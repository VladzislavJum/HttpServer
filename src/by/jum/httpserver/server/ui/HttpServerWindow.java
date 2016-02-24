package by.jum.httpserver.server.ui;

import by.jum.httpserver.server.ServerRunner;
import by.jum.httpserver.utils.constants.Constants;
import org.apache.log4j.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HttpServerWindow {

    private static final Logger LOGGER = Logger.getLogger(HttpServerWindow.class);

    private JTextArea logArea;
    private ServerRunner serverRunner;
    private boolean isRuning;

    public void createLogWindow() {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton stopServer = new JButton(Constants.STOP.getName());
        JButton runServer = new JButton(Constants.START.getName());
        JButton clearLOG = new JButton(Constants.CLEAR.getName());

        JPanel serverPanel = new JPanel();
        serverPanel.setLayout(new FlowLayout());
        serverPanel.add(runServer);
        serverPanel.add(stopServer);
        serverPanel.add(clearLOG);

        stopServer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (serverRunner != null && isRuning){
                        serverRunner.stopServer();
                        isRuning = false;
                    }
                } catch (IOException e1) {
                    LOGGER.info("Closing error");
                }
            }
        });

        runServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRuning){
                    serverRunner = new ServerRunner(logArea);
                    serverRunner.start();
                    isRuning = true;
                }
            }
        });


        logArea = new JTextArea();
        logArea.setEditable(false);


        clearLOG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logArea.setText("");
            }
        });

        frame.add(new JScrollPane(logArea));
        frame.add(serverPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }
}
