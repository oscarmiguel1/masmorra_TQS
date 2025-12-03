package es.uab.TQS.mazmorra;

import es.uab.TQS.mazmorra.controlador.LoopJoc;
import es.uab.TQS.mazmorra.model.*;
import es.uab.TQS.mazmorra.vista.Interface;

public class Main {

  public static void main(String[] args) {

    Jugador player = new Jugador();
    Joc j = new Joc(player);
    j.giveItem(new Pocion(player));
    Interface hud = new Interface();
    LoopJoc controller = new LoopJoc(j, hud);

    controller.startGame();

  }

}
