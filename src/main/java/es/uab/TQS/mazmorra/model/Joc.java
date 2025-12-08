package es.uab.TQS.mazmorra.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Joc { //Classe principal on s'inicialitza tot el joc i es gestionen les interaccions entre les classes

  public enum GameState {
    EXPLORING,
    INVENTORY,
    gameOver,
    GAME_WON,
    INFO
  }

  public static String[] FLOOR1 = {
      "################################################################################",
      "#              ###########                     #########              ##########",
      "#              #         #                     #       #              #        #",
      "#              #        ##########    #########       #######  #######         #",
      "#              #                  #    #                     #  #              #",
      "#              ###########        ######           ###########  #              #",
      "#                        #             #          #             #              #",
      "#                        #             #                        #              #",
      "#######   ####   ############     #    #                        #######  #######",
      "#     #   #                 #     #    #          #             #     #  #     #",
      "#     #####                 #######    ############             #     ####     #",
      "#                                 #                      #      #              #",
      "#                                 #                      #      #              #",
      "###########     #########    ######                      ########     ##########",
      "#         #     #                                                     #        #",
      "#         #######                                                     ##########",
      "#                                                                              #",
      "#                                                                              #",
      "#                                                                              #",
      "#                                                                              #",
      "################################################################################"
  };
  public static String[] FLOOR2 = {
      "################################################################################",
      "#             #                        #              #                        #",
      "#    #####    #    ################    #    ######    #    ################    #",
      "#    #   #    #    #              #    #    #    #    #    #              #    #",
      "#    #   #         #              #         #    #         #              #    #",
      "#    #   ###########              ###########    ###########              #    #",
      "#    #                                                                    #    #",
      "#    #              #######                #######                #####   #    #",
      "######              #     #                #     #                #   #   ######",
      "#                   #     #                #     #                #   #        #",
      "#    ################     ##################     ##################   #####    #",
      "#    #                                                                         #",
      "#    #              #######                #######                #########    #",
      "######              #     #                #     #                #       #    #",
      "#        ############     ##################     ##################            #",
      "#        #                #                      #                             #",
      "#        #                #                      #                        #    #",
      "#   ######   ##############   ####################   ######################    #",
      "#   #        #                #                      #                    #    #",
      "#            #                #                      #                    #    #",
      "################################################################################"
  };
  public static String[] FLOOR3 = {
      "################################################################################",
      "#                                                                              #",
      "#      ############          ############          ############      #######   #",
      "#      #          #          #          #          #          #      #     #   #",
      "#      #          #          #          #          #          #      #     #   #",
      "#      #          #          #          #          #          #      #######   #",
      "#      ############          ############          ############                #",
      "#                                                                              #",
      "#                                                                              #",
      "#           ########            ########            ########                   #",
      "#           ########            ########            ########                   #",
      "#           ########            ########            ########                   #",
      "#           ########            ########            ########                   #",
      "#                                                                              #",
      "#                                                                              #",
      "#      ########        ####                ####        ########                #",
      "#      ########        ####                ####        ########      #######   #",
      "#      ########        ####                ####        ########      #     #   #",
      "#      ########        ####                ####        ########      #     #   #",
      "#      ########        ####                ####        ########      #######   #",
      "################################################################################"
  };

  private long infoMsgFi = 0;
  private final Jugador player;
  private Planta[] mazmorra;
  private Planta plantaActual;
  int numPlanta;
  boolean gameOver;
  GameState currentState;
  Item itemActual;

  public Joc(Jugador j) {
    this.player = j;
    this.currentState = GameState.EXPLORING;
    this.gameOver = false;
    this.itemActual = null;
  }

  public void missatgeTemporal(long duracionMs) { //estableix la duracio dels missatges d'INFO
    currentState = GameState.INFO;
    infoMsgFi = System.currentTimeMillis() + duracionMs;
  }

  public void startGame() {

    this.mazmorra = new Planta[3];
    this.player.setStatsInicials();
    this.numPlanta = 0;
    this.currentState = GameState.EXPLORING;
    this.gameOver = false;

    //establiment dels enmics i plantes de la masmorra
    ArrayList<Enemic> enemics1 = new ArrayList<>();
    Enemic e1 = new Enemic(5, 50, 7, 4);
    enemics1.add(e1);
    Enemic e2 = new Enemic(5, 50, 59, 4);
    enemics1.add(e2);
    Enemic e3 = new Enemic(3, 50, 76, 10);
    enemics1.add(e3);
    Enemic e4 = new Enemic(3, 50, 13, 13);
    enemics1.add(e4);
    Enemic e5 = new Enemic(8, 50, 46, 11);
    enemics1.add(e5);

    ArrayList<Enemic> enemics2 = new ArrayList<>();
    Enemic e6 = new Enemic(8, 50, 70, 13);
    enemics2.add(e6);
    Enemic e7 = new Enemic(8, 50, 23, 13);
    enemics2.add(e7);
    Enemic e8 = new Enemic(10, 50, 21, 3);
    enemics2.add(e8);
    Enemic e9 = new Enemic(10, 50, 46, 3);
    enemics2.add(e9);

    ArrayList<Enemic> enemics3 = new ArrayList<>();
    Enemic e10 = new Enemic(10, 50, 25, 7);
    enemics3.add(e10);
    Enemic e11 = new Enemic(5, 50, 72, 14);
    enemics3.add(e11);
    Enemic e12 = new Enemic(8, 50, 3, 19);
    enemics3.add(e12);
    Enemic e13 = new Enemic(8, 50, 55, 1);
    enemics3.add(e13);

    this.mazmorra[0] = new Planta(5, FLOOR1, enemics1, 20, 2);
    this.mazmorra[1] = new Planta(4, FLOOR2, enemics2, 2, 12);
    this.mazmorra[2] = new Planta(4, FLOOR3, enemics3, 35, 1);

    this.plantaActual = this.mazmorra[0];
    this.player.setInitialPos(38, 19);
    giveItem(new Pocion(player,10));
  }

  public Jugador getPlayer() {
    return player;
  }

  public Planta getPlantaActual() {
    return plantaActual;
  }

  public GameState getCurrentState() {
    return currentState;
  }

  public Item getItemActual() {
    return itemActual;
  }

  public long getInfoMsgFi() {
    return infoMsgFi;
  }

  public Planta[] getMazmorra() {
    return this.mazmorra;
  }

  public int getNumPlanta() {
    return this.numPlanta;
  }

  public boolean getGameOver() {
    return this.gameOver;
  }

  public void setNumPlanta(int x) {
    this.numPlanta = x;
  }

  public void setPlantaActual(Planta planta) {
    this.plantaActual = planta;
  }

  public double numeroAleatori(){
    return Math.random();
  }

  public void checkInfo() {
    if (currentState == GameState.INFO && System.currentTimeMillis() > infoMsgFi) {
      currentState = GameState.EXPLORING;
      this.itemActual = null;
    }
  }

  public void toggleInventory() {
    currentState = (currentState == GameState.EXPLORING)
            ? GameState.INVENTORY
            : GameState.EXPLORING;
  }

  public void boom(){ //elimina els enemics corresponents de manera aleatoria i comprova si queden restants per donar la clau
    ArrayList<Enemic> enemics = this.plantaActual.getEnemies();

    if(enemics.isEmpty()) return; //si no queda cap enemic, la bomba no fa res

    int enemicsAeliminar = Math.min(1 + (int)(Math.random() * 3), enemics.size());
    for (int i = 0; i < enemicsAeliminar; i++) {
      int idx = (int)(Math.random() * enemics.size());
      enemics.remove(idx);
    }
    this.plantaActual.setEnemiesLeft(enemics.size());
    if(this.plantaActual.getEnemiesLeft() == 0){ //si eliminem els ultims, rebem clau
      this.itemActual = new Llave(this.player);
      giveItem(itemActual);
      missatgeTemporal(1000);
    }
  }

  public void battle(int x, int y) { //resta al jugador l'atk de l'enemic i comprova si perd la batalla o si guanya. al guanyar, rep diferents items
    Enemic e = this.plantaActual.getEnemy(x, y);

    int php = this.player.getHP();
    int pexp = this.player.getEXP();

    php = php - e.getAtk();
    pexp = pexp + e.getEXP();

    if (php <= 0) {
      currentState = GameState.gameOver;
    } else {
      this.player.setHP(php);
      this.player.setEXP(pexp);
      this.plantaActual.enemyDefeated(x, y);

      //Els drops depenen d'un valor aleatori, a excepcio de la clau
      //Drop de pocio
      if (numeroAleatori() < 0.50) {
        if(this.numPlanta > 0){
          this.itemActual = new Pocion(this.player,15);
        }else{
          this.itemActual = new Pocion(this.player,10);
        }
        giveItem(itemActual);
        missatgeTemporal(1000);
      }

      //Drop de bomba
      if(numeroAleatori() < 0.10 ){
        this.itemActual = new Bomba(this.player);
        giveItem(itemActual);
        missatgeTemporal(1000);
      }

      if (this.plantaActual.getEnemiesLeft() == 0) {
        this.itemActual = new Llave(this.player);
        giveItem(this.itemActual);
        missatgeTemporal(1000);
      }
    }
  }

  public void giveItem(Item i) {
    this.player.addItem(i);
  }

  public void passarPlanta() { //avancem de planta, si hi som a l'ultima guanyem
    if (this.numPlanta != 2) {
      this.numPlanta++;
      this.plantaActual = mazmorra[numPlanta];
      this.player.setInitialPos(38, 19);
    } else {
      currentState = GameState.GAME_WON;
    }
  }

}
