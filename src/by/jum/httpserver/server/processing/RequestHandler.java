package by.jum.httpserver.server.processing;

public class RequestHandler implements IRequestHandler {

    @Override
    public String getMethod(String request) {
        if (request == null || request.equals("")) {
            return "";
        }
        return request.substring(0, request.indexOf(" "));
    }

    @Override
    public String getUrl(String request) {
        if (request == null || request.equals("")) {
            return "";
        }
        String method = getMethod(request);
        int length = method.length();
        String url = request.substring(length + 2, request.indexOf(" ", length + 2));
        return url;
    }
}
