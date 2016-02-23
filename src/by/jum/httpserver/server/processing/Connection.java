package by.jum.httpserver.server.processing;

import by.jum.httpserver.utils.constants.Constants;
import org.apache.log4j.Logger;

import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.FileReader;
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
            while (true) {
                String request = inputReader.readLine();
                if (request == null || request.trim().length() == 0) {
                    break;
                } else {
                    allRequest.append(request + "\n");
                }
            }
            LOGGER.info(requestHandler.getUrl(allRequest.toString()));
            logArea.append(Constants.SEPARATOR.getName() + allRequest);
            LOGGER.info(getMethod(allRequest.toString()));

            getContext("gf");


        } catch (IOException e) {
            logArea.append("\n Client disconnected");
        } finally {
            try {
                if (socket != null) {
                    logArea.append(Constants.SEPARATOR.getName() + "Client " + socket.getInetAddress().getCanonicalHostName() + " Disconnected\n");
                    socket.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    private String getMethod(String request) {
        String method = "";
        char symbol;
        for (int i = 0; i < request.length(); i++) {
            symbol = request.charAt(i);
            if (symbol == ' ') {
                return method;
            } else {
                method += symbol;
            }
        }
        return method;
    }

    private String getContext(String url) throws IOException {
        StringBuilder context = new StringBuilder();

        FileReader reader = new FileReader("resource/htmls/page1.html");

        while (reader.ready()){
//            context.append(reader.read());
            outputStream.write(reader.read());
        }
        return context.toString();
    }
}
