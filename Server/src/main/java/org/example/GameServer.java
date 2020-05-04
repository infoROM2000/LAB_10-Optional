package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
    // Define the port on which the server is listening
    public static final int PORT = 8100;
    public static int currentID=0; //fiecare joc va avea un ID unic
    public static ArrayList<Game> gamesList =new ArrayList<>();
    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT); //creaza un nou ServerSocket.
            while (true) { //server-ul va fi deschis pana cand se va apela exit(0) din clientThread
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept(); //asteapta conectarea unui client;
                // Execute the client's request in a new thread
                new ClientThread(socket).start(); //pornim un nou thread pentru fiecare client, pe care il si pornim
            }
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }
}

