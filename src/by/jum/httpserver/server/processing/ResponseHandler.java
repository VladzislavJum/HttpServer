package by.jum.httpserver.server.processing;

import by.jum.httpserver.utils.constants.Constants;
import by.jum.httpserver.utils.constants.Path;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseHandler {

    private static final Logger LOGGER = Logger.getLogger(ResponseHandler.class);
    private OutputStream outputStream;
    private StringBuffer response;


    public ResponseHandler(OutputStream outputStream) {
        this.outputStream = outputStream;
        response = new StringBuffer();
    }

    public String getResponse(String filePath) {
        String status = Constants.STATUS_200.getName();

        try {
            readFile(filePath);
        } catch (FileNotFoundException e) {
            LOGGER.warn("File " + filePath + " not found");
            status = Constants.STATUS_404.getName();
            filePath = Path.ERROR_404_PATH.getPath();
            try {
                readFile(filePath);
            } catch (IOException e1) {
                LOGGER.error("File not Found");
            }
        } catch (IOException e) {
            LOGGER.warn("Read File " + filePath + " Error");
            status = Constants.STATUS_500.getName();
        }

        String responseHeader = "HTTP/1.1 " + status + "\r\n" +
                "Server: Http Server\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + response.length() + "\r\n" +
                "Connection: keep-alive\r\n\r\n";

        return responseHeader + response;
    }

    private void readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while (reader.ready()) {
            response.append(reader.readLine());
        }
    }

}
