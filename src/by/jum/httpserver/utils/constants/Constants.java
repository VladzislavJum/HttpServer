package by.jum.httpserver.utils.constants;

public enum Constants {

    STOP("Stop"),
    START("Start"),
    CLEAR("Clear LOG"),
    SEPARATOR("!-------------------------------------------------------!\n");

    private String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

