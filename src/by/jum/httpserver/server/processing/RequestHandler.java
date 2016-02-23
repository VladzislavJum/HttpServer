package by.jum.httpserver.server.processing;

public class RequestHandler implements IRequestHandler{

    @Override
    public String getMethod(String request) {
        String method = request.substring(0, request.indexOf(" "));
        return method;
    }

    @Override
    public String getUrl(String request) {
        String method = getMethod(request);
        int length = method.length();
        String url = request.substring(length + 2, request.indexOf(" ", length + 2));
        return url;
    }
}
