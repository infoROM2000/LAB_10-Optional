package org.example;

import java.io.IOException;
import java.net.Socket;

public class App {
    static Socket socket=null;
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try {
            socket = new Socket(serverAddress, PORT);
            new MainMenu().setVisible(true);
        } catch (IOException e) {
            System.err.println("No server listening... " + e);
        }
    }
}
