package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import static java.lang.Integer.parseInt;
import static org.example.App.socket;

public class GameFrame extends JFrame {
    final int LUNGIME = 17;
    final int LATIME = 17;
    String negru; //true daca pune piese negre, false daca albe
    JButton[][] casute = new JButton[LUNGIME][LATIME];
    String ID; //ID-ul jocului din fereastra

    GameFrame(String id, String eNegru) {
        super("Joc");
        ID = id;
        negru = eNegru;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(850, 850));
        setLayout(new GridLayout(LUNGIME, LATIME));
        for (int i = 0; i < LUNGIME; i++)
            for (int j = 0; j < LATIME; j++) {
                casute[i][j] = new JButton();
                casute[i][j].setText(i + " " + j + " ");
                casute[i][j].addActionListener(listener);
                add(casute[i][j]);
            }
        pack();
    }

    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String text = ((JButton) e.getSource()).getText(); // aici luam i-ul si j-ul butonului

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("Am trimis: " + "move " + text + ID + " " + negru);
                out.println("move " + text + ID + " " + negru);
                String[] separat = text.split(" ");
                int i = parseInt(separat[0]);
                int j = parseInt(separat[1]);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //folosit ca sa citim raspunsul transmis de server
                String raspuns = in.readLine();
                System.out.println("Am primit: " + raspuns);
                if (raspuns.equals("Succes!")) {
                    if (negru.equals("1")) {
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(getClass().getResource("/black.png"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Icon icon = new ImageIcon(image);
                        casute[i][j].setIcon(icon);
                    } else {
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(getClass().getResource("/black.png"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Icon icon = new ImageIcon(image);
                        casute[i][j].setIcon(icon);
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    };
}
