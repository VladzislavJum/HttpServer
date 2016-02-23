package by.jum.httpserver.server.processing;

public interface IRequestHandler {
    String getMethod(String request);
    String getUrl(String request);
}
