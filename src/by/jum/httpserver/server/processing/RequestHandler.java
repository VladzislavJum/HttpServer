package by.jum.httpserver.server.processing;

import java.util.HashMap;

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


    @Override
    public int getContentLength(String request) {
        if (request == null || request.equals("")) {
            return 0;
        }
        final String contentHeader = "Content-Length: ";
        int startIndex = request.indexOf(contentHeader) + contentHeader.length();
        int endIndex = request.substring(startIndex).indexOf("\n") + startIndex;
        int contentLength = Integer.parseInt(request.substring(startIndex, endIndex).trim());
        return contentLength;
    }

    @Override
    public HashMap<String, String> getParams(String request) {
        HashMap<String, String> params = new HashMap<>();
        if (request == null || request.equals("")) {
            return params;
        }
        String[] allSubstring = request.split("\n");
        String lastString = allSubstring[allSubstring.length - 1];

        String[] allParams = lastString.split("&");
        String[] keyValue;
        for (String param : allParams) {
            keyValue = param.split("=");
            if (keyValue.length == 1) {
                params.put(keyValue[0], "");
            } else {
                params.put(keyValue[0], keyValue[1]);
            }
        }
        return params;
    }
}
