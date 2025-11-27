package es.uab.TQS.mazmorra;

import es.uab.TQS.mazmorra.model.*;

public class Main {

    public static void main(String[] args) {

        Jugador player = new Jugador();
        Joc j = new Joc(player);

        j.startGame();



    }

}
