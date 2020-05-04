package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Integer.parseInt;
import static org.example.GameServer.currentID;
import static org.example.GameServer.gamesList;

class ClientThread extends Thread {
    private Socket socket = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //folosit ca sa citim comanda trimisa de client
            while (true) {
                String request = in.readLine(); //citim din socket intr-un string cu ajutorul BufferedReader-ului
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                if (request == null) //primit daca clientul scrie null (clientul scrie exit, care nici nu e trimis in socket). In cazul acesta e necesar sa se inchida socketul clientului respectiv din server
                    socket.close();
                else if (request.equals("stop")) //daca primim stop, atunci trimitem Server stopped si inchidem server-ului/tot programul. O solutie usor brutala
                {
                    out.println("Server stopped!");
                    out.flush();
                    System.exit(0);
                } else { //altfel, pentru comenzi cream un PrintWriter pentru a intoarce in socket raspunsul de la server -> client
                    // Send the response to the output stream: server → client
                    String raspuns = "Server received the request " + request + ".";
                    System.out.println(raspuns);
                    System.out.println(currentID);
                    String[] separat = request.split(" ");
                    switch (separat[0]) {//primul cuvant din request va fi mereu tipul de comanda
                        case "create":
                            new Game(currentID++);
                            out.println(currentID - 1);
                            break;
                        case "see":
                            String mesaj = "";
                            for (Game game : gamesList) {
                                if (game.inAsteptare) //trimitem clientului id-urile tuturor jocurilor la care se poate conecta
                                    mesaj += game.getID() + " ";
                            }
                            System.out.println(mesaj);
                            out.println(mesaj);
                            break;
                        case "move":
                            int i = parseInt(separat[1]);
                            int j = parseInt(separat[2]);
                            int id = parseInt(separat[3]);
                            String negru = separat[4];
                            for (Game game : gamesList) {
                                if (game.getID() == id) {
                                    if (game.getMutaNegru().equals(negru)) {//cel care solicita sa mute este la tura
                                        if (negru.equals("1")) {
                                            game.board[i][j] = 1;
                                            if (game.verifica(negru, i, j).equals("A castigat negrul")) {
                                                System.out.print("A castigat negrul");
                                            } else
                                                game.setMutaNegru("0"); //acum va trebui sa mute albul
                                        } else {
                                            game.board[i][j] = -1;
                                            if (game.verifica(negru, i, j).equals("A castigat albul")) {
                                                System.out.print("A castigat albul");
                                            } else
                                                game.setMutaNegru("1"); //acum va trebui sa mute negrul
                                        }
                                        out.println("Succes!");
                                    } else {
                                        out.println("Asteptati pe celalalt!");
                                    }
                                }
                                break;
                            }
                            break;
                        case "join":
                            int ID = parseInt(separat[1]);
                            for (Game game : gamesList) {
                                if (game.getID() == ID) {
                                    if (game.inAsteptare) {
                                        game.inAsteptare = false;
                                        out.println("Conectat la " + ID);
                                    } else {
                                        out.println("Deja inceput!");
                                    }
                                }
                            }
                    }
                    out.flush(); //flush ca sa "curatam" out-ul
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        }
    }
}