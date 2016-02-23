package by.jum.httpserver.utils.constants;

public enum Path {

    LOG4J_CONF_PATH("resource/config/log4j.properties");

    private String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
