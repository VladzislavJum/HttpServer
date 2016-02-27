package by.jum.httpserver.utils.constants;

public enum Constants {

    STOP("Stop"),
    START("Start"),
    CLEAR("Clear LOG"),
    SEPARATOR("!-------------------------------------------------------!\n"),
    STATUS_200("200 OK"),
    STATUS_404("404 Not Found"),
    STATUS_500("500 Internal Server Error"),
    METHOD_GET("GET"),
    METHOD_POST("POST"),
    METHOD_HEAD("HEAD");


    private String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getConstant(){
        return constant;
    }
}

