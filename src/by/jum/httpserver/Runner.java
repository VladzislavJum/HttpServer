package by.jum.httpserver;

import by.jum.httpserver.server.HttpServerWindow;
import by.jum.httpserver.utils.constants.Path;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.SwingUtilities;

public class Runner {
    public static void main(String[] args) {
        PropertyConfigurator.configure(Path.LOG4J_CONF_PATH.getPath());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HttpServerWindow httpServerWindow = new HttpServerWindow();
                httpServerWindow.createLogWindow();
            }
        });
    }
}
