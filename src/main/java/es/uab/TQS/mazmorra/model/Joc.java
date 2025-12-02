package es.uab.TQS.mazmorra.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Joc {

    public enum GameState {
        EXPLORING,
        INVENTORY,
        GAME_OVER,
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

    long infoMessageEndTime = 0;

    private final Jugador player;
    private Planta[] mazmorra;
    private Planta planta_actual;
    int num_planta;
    boolean game_over;
    GameState currentState;
    Item itemActual;

    public Joc(Jugador j) {
        this.player = j;
        this.currentState = GameState.EXPLORING;
        this.game_over = false;
        this.itemActual = null;
    }

    public void missatgeTemporal(long duracionMs) {
        currentState = GameState.INFO;
        infoMessageEndTime = System.currentTimeMillis() + duracionMs;
    }

    public void startGame() {
        this.mazmorra = new Planta[3];
        this.player.setStatsInicials();
        this.num_planta = 0;
        this.currentState = GameState.EXPLORING;
        this.game_over = false;

        ArrayList<Enemic> enemics1 = new ArrayList<>();
        Enemic e1 = new Enemic(1, 50, 7, 4);
        enemics1.add(e1);
        Enemic e2 = new Enemic(1, 50, 59, 4);
        enemics1.add(e2);
        Enemic e3 = new Enemic(50, 50, 76, 10);
        enemics1.add(e3);

        ArrayList<Enemic> enemics2 = new ArrayList<>();
        Enemic e4 = new Enemic(1, 20, 70, 13);
        enemics2.add(e4);
        Enemic e5 = new Enemic(1, 20, 23, 13);
        enemics2.add(e5);
        Enemic e6 = new Enemic(1, 20, 21, 3);
        enemics2.add(e6);

        ArrayList<Enemic> enemics3 = new ArrayList<>();
        Enemic e7 = new Enemic(1, 40, 25, 7);
        enemics3.add(e7);
        Enemic e8 = new Enemic(1, 20, 72, 14);
        enemics3.add(e8);
        Enemic e9 = new Enemic(1, 20, 3, 19);
        enemics3.add(e9);

        this.mazmorra[0] = new Planta(3, FLOOR1, enemics1, 20, 2);
        this.mazmorra[1] = new Planta(3, FLOOR2, enemics2, 2, 12);
        this.mazmorra[2] = new Planta(3, FLOOR3, enemics3, 35, 1);

        this.planta_actual = this.mazmorra[0];
        this.player.setInitialPos(38, 19);
    }

    public Jugador getPlayer() {
        return player;
    }

    public Planta getPlantaActual() {
        return planta_actual;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public Item getItemActual() {
        return itemActual;
    }

    public void checkInfoMessageDuration() {
        if (currentState == GameState.INFO && System.currentTimeMillis() > infoMessageEndTime) {
            currentState = GameState.EXPLORING;
            this.itemActual = null;
        }
    }

    public void toggleInventory() {
        currentState = (currentState == GameState.EXPLORING)
                ? GameState.INVENTORY
                : GameState.EXPLORING;
    }

    public void battle(int x, int y) {
        Enemic e = this.planta_actual.getEnemy(x, y);

        int p_hp = this.player.getHP();
        int p_exp = this.player.getEXP();

        p_hp = p_hp - e.getAtk();
        p_exp = p_exp + e.getEXP();

        if (p_hp <= 0) {
            currentState = GameState.GAME_OVER;
        } else {
            this.player.setHP(p_hp);
            this.player.setEXP(p_exp);
            this.planta_actual.enemyDefeated(x, y);

            if (Math.random() < 0.25) {
                this.itemActual = new Pocion(this.player);
                giveItem(itemActual);
                missatgeTemporal(1000);
            }

            if (this.planta_actual.getEnemiesLeft() == 0) {
                this.itemActual = new Llave(this.player);
                giveItem(this.itemActual);
                missatgeTemporal(1000);
            }
        }
    }

    public void giveItem(Item i) {
        this.player.addItem(i);
    }

    public void passarPlanta() {
        if (this.num_planta != 2) {
            this.num_planta++;
            this.planta_actual = mazmorra[num_planta];
            this.player.setInitialPos(38, 19);
        } else {
            currentState = GameState.GAME_WON;
        }
    }

}
