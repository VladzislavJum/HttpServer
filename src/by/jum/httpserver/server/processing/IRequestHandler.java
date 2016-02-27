package by.jum.httpserver.server.processing;

import java.util.HashMap;

public interface IRequestHandler {
    String getMethod(String request);
    String getUrl(String request);
    HashMap<String, String> getParams(String request);
    int getContentLength(String request);
}
