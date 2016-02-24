package by.jum.httpserver.utils.constants;

public enum Constants {

    STOP("Stop"),
    START("Start"),
    CLEAR("Clear LOG"),
    SEPARATOR("!-------------------------------------------------------!\n"),
    STATUS_200("200 OK"),
    STATUS_404("404 Not Found"),
    STATUS_500("500 Internal Server Error");


    private String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

