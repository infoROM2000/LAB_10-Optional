package org.example;

import static org.example.GameServer.gamesList;

public class Game {
    private int ID;
    boolean inAsteptare;
    private String mutaNegru;
    int[][] board;

    Game(int id) {
        ID = id;
        inAsteptare = true;
        mutaNegru = "1";
        board = new int[17][17]; //dimensiuni standard Gomoko; 0 gol, 1 negru, -1 alb
        gamesList.add(this);
    }

    String verifica(String negru, int i, int j) {//functie pentru a verifica daca s-a realizat 5 in linie dupa o mutare. E necesar doar verificarea in jurul noii mutari
        int culoareaDorita;
        int counter;
        int ci = i;
        int cj = j;
        if (negru == "1")//tocmai a pus o piesa negru, vom verifica piesele negre
            culoareaDorita = 1;
        else
            culoareaDorita = -1;

        while (i > 0 && j > 0 && board[i][j] == culoareaDorita) //mergem in diagonala spre stanga-sus pana cand dam de o piesa alba/gol sau iesim din tabla
        {
            i--;
            j--;
        }
        i++;
        j++; //cand while-ul nu va mai fi adevarat, vom fi mers cu un pas mai mult decat trebuia
        counter = 1;
        while (true) {//verificam diagonala coborand spre dreapta jos
            i++;
            j++;
            if (i < 17 && j < 17 && board[i][j] == culoareaDorita) {
                counter++;
            } else
                break;
            if (counter == 5)
                if (culoareaDorita == 1)
                    return "A castigat negrul";
                else
                    return "A castigat albul";
        }

        i = ci;
        j = cj;
        while (i > -1 && j < 17 && board[i][j] == culoareaDorita) //mergem in diagonala spre dreapta-sus pana cand dam de o piesa alba/gol sau iesim din tabla
        {
            i--;
            j++;
        }
        i++;
        j--; //cand while-ul nu va mai fi adevarat, vom fi mers cu un pas mai mult decat trebuia
        counter = 1;
        while (true) {//verificam diagonala coborand spre stanga jos
            i++;
            j--;
            if (i < 17 && j > -1 && board[i][j] == culoareaDorita) {
                counter++;
            } else
                break;
            if (counter == 5)
                if (culoareaDorita == 1)
                    return "A castigat negrul";
                else
                    return "A castigat albul";
        }

        i = ci;
        j = cj;
        while (i > -1 && board[i][j] == culoareaDorita) //mergem in sus
            i--;
        i++; //cand while-ul nu va mai fi adevarat, vom fi mers cu un pas mai mult decat trebuia
        counter = 1;
        while (true) {//verificam coloana mergand in jos
            i++;
            if (i < 17 && board[i][j] == culoareaDorita) {
                counter++;
            } else
                break;
            if (counter == 5)
                if (culoareaDorita == 1)
                    return "A castigat negrul";
                else
                    return "A castigat albul";
        }

        i = ci;
        j = cj;
        while (j > -1 && board[i][j] == culoareaDorita) //mergem in stanga
            j--;
        j++; //cand while-ul nu va mai fi adevarat, vom fi mers cu un pas mai mult decat trebuia
        counter = 1;
        while (true) {//verificam linia mergand in dreapta
            j++;
            if (j < 17 && board[i][j] == culoareaDorita) {
                counter++;
            } else
                break;
            if (counter == 5)
                if (culoareaDorita == 1)
                    return "A castigat negrul";
                else
                    return "A castigat albul";
        }

        return " "; //continuam
    }

    String getMutaNegru() {
        return mutaNegru;
    }

    void setMutaNegru(String s) {
        mutaNegru = s;
    }

    int getID() {
        return ID;
    }
}
