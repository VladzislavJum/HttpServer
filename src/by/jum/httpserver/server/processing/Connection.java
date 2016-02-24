package by.jum.httpserver.server.processing;

import by.jum.httpserver.utils.constants.Constants;
import by.jum.httpserver.utils.constants.Path;
import org.apache.log4j.Logger;

import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Connection extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Connection.class);
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private RequestHandler requestHandler;
    private JTextArea logArea;
    private ResponseHandler responseHandler;

    public Connection(Socket socket, JTextArea logArea) throws IOException {
        this.logArea = logArea;
        this.socket = socket;

        requestHandler = new RequestHandler();
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        responseHandler = new ResponseHandler(outputStream);
        outputStream.flush();
    }

    public void run() {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer allRequest = new StringBuffer();
            while (true) {
                String request = inputReader.readLine();
                if (request == null || request.trim().length() == 0) {
                    break;
                } else {
                    allRequest.append(request + "\n");
                }
            }
            System.out.print(allRequest);

            LOGGER.info(requestHandler.getMethod(allRequest.toString()));
            logArea.append(Constants.SEPARATOR.getName() + allRequest);

            String response = responseHandler.getResponse(
                    Path.PAGES_PATH.getPath() + requestHandler.getUrl(allRequest.toString()) + ".html");

            outputStream.write(response.getBytes());
            logArea.append(Constants.SEPARATOR.getName() + response + "\n");
        } catch (IOException e) {
            logArea.append("\n Request or Response Error");
        } finally {
            try {
                if (socket != null) {
                    logArea.append(Constants.SEPARATOR.getName() + "Client " + socket.getInetAddress().getCanonicalHostName() + " Disconnected\n");
                    socket.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
