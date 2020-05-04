package org.example;

import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.example.App.socket;
public class MainMenu extends JFrame {

    JButton createBtn = new JButton("Create game");
    JButton joinBtn = new JButton("Join game");

    public MainMenu() {
        super("Gomoku");
        this.setPreferredSize(new Dimension(800,600));
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        add(createBtn);
        add(joinBtn);
        createBtn.addActionListener(this::create);
        joinBtn.addActionListener(this::join);
        pack();
        //create the components
    }

    private void create(ActionEvent e) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("create");
            System.out.println("Am trimis: create");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //folosit ca sa citim comanda id-ul trimis de server
            String id=in.readLine();
            System.out.println("Am primit id-ul noii camere: " + id);
            String eNegru="1";
            new GameFrame(id,eNegru).setVisible(true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void join(ActionEvent e) {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("see");
            System.out.println("Am trimis: see");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            System.out.println("Am primit id-urile camerelor disponibile:" + response);
            String[] separat=response.split(" "); //separam id-urile in string-uri individuale
            new SelectMenu(separat).setVisible(true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
