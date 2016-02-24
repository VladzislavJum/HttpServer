package by.jum.httpserver.utils.constants;

public enum Path {

    LOG4J_CONF_PATH("resource/config/log4j.properties"),
    PAGES_PATH("resource/htmls/"),
    ERROR_404_PATH("resource/htmls/404error.html");


    private String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
