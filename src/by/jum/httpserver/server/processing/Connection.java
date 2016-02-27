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

    public Connection(Socket socket, JTextArea logArea) throws IOException {
        this.logArea = logArea;
        this.socket = socket;

        requestHandler = new RequestHandler();
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        outputStream.flush();
    }

    public void run() {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer allRequest = new StringBuffer();
            String request;
            while (true) {
                request = inputReader.readLine();
                if (request == null || request.trim().length() == 0) {
                    String method = requestHandler.getMethod(allRequest.toString());
                    if (method.equals(Constants.METHOD_POST.getConstant())) {
                        StringBuilder body = new StringBuilder();
                        for (int i = 0; i < requestHandler.getContentLength(allRequest.toString()); i++) {
                            body.append((char) inputReader.read());
                        }
                        allRequest.append(body + "\n");
                    }
                    break;
                } else {
                    allRequest.append(request + "\n");
                }
            }

            logArea.append(Constants.SEPARATOR.getConstant() + allRequest);
            writeResponse(allRequest.toString());
        } catch (IOException e) {
            logArea.append("\n Request or Response Error");
        } finally {
            try {
                if (socket != null) {
                    logArea.append(Constants.SEPARATOR.getConstant() + "Client " + socket.getInetAddress().getCanonicalHostName() + " Disconnected\n");
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

    private void writeResponse(String request) throws IOException {
        ResponseHandler responseHandler = new ResponseHandler(outputStream, requestHandler);
        String method = requestHandler.getMethod(request);
        LOGGER.info("Method " + method);

        String filePath = Path.PAGES_PATH.getPath() + requestHandler.getUrl(request) + ".html";
        String response = responseHandler.getResponse(filePath, request);
        outputStream.write(response.getBytes());
        logArea.append(Constants.SEPARATOR.getConstant() + response + "\n");
    }

}
