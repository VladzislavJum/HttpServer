package by.jum.httpserver.server.processing;

import by.jum.httpserver.utils.constants.Constants;
import by.jum.httpserver.utils.constants.Path;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

public class ResponseHandler {

    private static final Logger LOGGER = Logger.getLogger(ResponseHandler.class);
    private OutputStream outputStream;
    private StringBuffer response;
    private String status;
    private RequestHandler requestHandler;


    public ResponseHandler(OutputStream outputStream, RequestHandler requestHandler) {
        this.outputStream = outputStream;
        this.requestHandler = requestHandler;
        response = new StringBuffer();
    }

    public String getResponse(String filePath, String request) {
        addBody(filePath);
        String method = requestHandler.getMethod(request);
        if (method.equals(Constants.METHOD_POST.getConstant())) {
            Collection<String> valuesList = requestHandler.getParams(request).values();
            LOGGER.info("Params: " + requestHandler.getParams(request));
            StringBuilder values = new StringBuilder();
            valuesList.forEach(s -> values.append(s + "<br/>"));
            response.insert(response.indexOf("<body>") + 6, values);
        } else if (method.equals(Constants.METHOD_HEAD.getConstant())) {
            return getHeader();
        }

        return getHeader() + response;
    }

    private void readFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while (reader.ready()) {
            response.append(reader.readLine());
        }
    }

    private void addBody(String filePath) {
        status = Constants.STATUS_200.getConstant();
        try {
            readFile(filePath);
        } catch (FileNotFoundException e) {
            LOGGER.warn("File " + filePath + " not found");
            status = Constants.STATUS_404.getConstant();
            filePath = Path.ERROR_404_PATH.getPath();
            try {
                readFile(filePath);
            } catch (IOException e1) {
                LOGGER.error("File not Found");
            }
        } catch (IOException e) {
            LOGGER.warn("Read File " + filePath + " Error");
            status = Constants.STATUS_500.getConstant();
        }
        LOGGER.info("Status " + status);
    }


    private String getHeader(){
        String responseHeader = "HTTP/1.1 " + status + "\r\n" +
                "Server: Http Server\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + response.length() + "\r\n" +
                "Connection: keep-alive\r\n\r\n";

        return responseHeader;
    }

}
