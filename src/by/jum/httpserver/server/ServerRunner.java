package by.jum.httpserver.server;

import by.jum.httpserver.server.processing.Connection;
import by.jum.httpserver.utils.constants.Constants;
import org.apache.log4j.Logger;

import javax.swing.JTextArea;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ServerRunner.class);
    private final int PORT = 5225;
    private ServerSocket serverSocket;
    private Socket socket;
    private JTextArea logArea;

    public ServerRunner(JTextArea logArea) {
        super();
        this.logArea = logArea;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            logArea.append("Server Running\n");

            while (!serverSocket.isClosed()) {
                socket = serverSocket.accept();
                logArea.append(Constants.SEPARATOR.getConstant() + "Client " + socket.getInetAddress().getCanonicalHostName() + " connected\n");
                Connection connection = new Connection(socket, logArea);
                connection.start();
            }
        } catch (IOException e) {
            LOGGER.info("Connection Error!");
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    LOGGER.info("Server Socket closed");
                }
            } catch (IOException e) {
                LOGGER.info("Connection Error!");
            }
        }
    }

    public void stopServer() throws IOException {
        if (socket != null) {
            socket.close();
        }

        if (serverSocket != null) {
            serverSocket.close();
        }
        logArea.append("Server Stopped\n");
    }

}
