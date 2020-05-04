package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import static org.example.App.socket;

public class SelectMenu extends JFrame {
    SelectMenu(String... gamesIDs) {
        super("Selectie");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(400, gamesIDs.length * 50));
        setLayout(new GridLayout(gamesIDs.length, 1));
        for (int i = 0; i < gamesIDs.length; i++) {
            String id = gamesIDs[i];
            JButton button = new JButton("Joc cu ID-ul: " + id);
            button.addActionListener(listener);
            add(button);
        }
        pack();
    }

    ActionListener listener = e -> {
        try {
            String text = ((JButton) e.getSource()).getText(); // aici luam i-ul si j-ul butonului
            String[] separat = text.split(" ");
            String id = separat[3];
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Am trimis: " + "join " + id);
            out.println("join " + id);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //folosit ca sa citim raspunsul transmis de server
            String raspuns = in.readLine();
            System.out.println("Am primit: " + raspuns);
            String[] separat1 = raspuns.split(" ");
            if (separat1[0].equals("Conectat")) {
                new GameFrame(separat1[2], "0");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    };
}

