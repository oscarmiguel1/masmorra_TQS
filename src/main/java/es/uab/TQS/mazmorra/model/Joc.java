package es.uab.TQS.mazmorra.model;

import com.googlecode.lanterna.input.KeyStroke;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import es.uab.TQS.mazmorra.model.Enemic;
import es.uab.TQS.mazmorra.model.Jugador;
import es.uab.TQS.mazmorra.model.Planta;

import java.util.ArrayList;

public class Joc {

    enum GameState {
        EXPLORING,
        INVENTORY
    }

    public static String[] FLOOR1 = {
            "################################################################################",
            "#..............###########.....................#########..............##########",
            "#..............#.........#.....................#.......#..............#........#",
            "#..............#.........##########    #########.......#######  #######........#",
            "#..............#..................#    #.....................#  #..............#",
            "#..............###########........######...........###########  #..............#",
            "#........................#..............#.........#............ #..............#",
            "#........................#..............#.........#............ #..............#",
            "#######   ###################     #######.........#............ #######   ######",
            "#.....#   #.................#     #.....#.........#............#.....#   #.....#",
            "#.....#####.................#######.....###########............#.....#####.....#",
            "#.................................#......................#.....#...............#",
            "#.................................#......................#.....#...............#",
            "###########     ###################......................#######......##########",
            "#.........#     #.....................................................#........#",
            "#.........#######.....................................................##########",
            "#..............................................................................#",
            "#..............................................................................#",
            "#..............................................................................#",
            "#..............................................................................#",
            "################################################################################"
    };
    public static String[] FLOOR2 = {
            "################################################################################",
            "#.................##########........................#########.................#",
            "#.................#........#........................#.......#.................#",
            "#.................#........###########    ###########.......#######  ##########",
            "#.................#..................#    #.......................#  #........#",
            "#.................###########........######...........#############  #........#",
            "#............................#..............#.......#.............. #........#",
            "#............................#..............#.......#.............. #........#",
            "#######   ####################     #######.........#..............  #######   #",
            "#.....#   #..................#     #.....#.........#..............# #.....#   #",
            "#.....#####..................#######.....###########..............# #.....#####",
            "#.................................#......................#.........#..........#",
            "#.................................#......................#.........#..........#",
            "###########     ###################......................#########......#####.#",
            "#.........#     #...................................................#.........#",
            "#.........#######...................................................###########",
            "#................................................................................",
            "#................................................................................",
            "#................................................................................",
            "#................................................................................",
            "################################################################################"
    };
    public static String[] FLOOR3 = {
            "################################################################################",
            "#........#####.......................#########...................##############",
            "#........#...#.......................#.......#...................#............#",
            "#........#...###########    ##########.......#############  #######..........#",
            "#........#..............#    #............................#  #................#",
            "#........############...######...........#############....#  #................#",
            "#.......................#.........#.....#..............#   #..................#",
            "#.......................#.........#.....#..............#   #..................#",
            "######   ##################    #######..#..............#   #######   ##########",
            "#....#   #................#    #.....#..#..............#  #.....#   #.........#",
            "#....#####................#######.....###########......#  #.....#####.........#",
            "#.............................#....................#...#  #...................#",
            "#.............................#....................#...#  #...................#",
            "#########     #################....................#######......###############",
            "#.......#     #.....................................................#.........#",
            "#.......#######.....................................................###########",
            "#..............................................................................#",
            "#..............................................................................#",
            "#..............................................................................#",
            "#..............................................................................#",
            "################################################################################"
    };

    private Jugador player;

    private Planta[] mazmorra;

    private Planta planta_actual;

    GameState currentState;

    public Joc(Jugador j){
        this.player = j;
        this.currentState = GameState.EXPLORING;
    }


    public void startGame(){
        this.mazmorra = new Planta[3];

        ArrayList<Enemic> enemics1 = new ArrayList<>();
        Enemic e1 = new Enemic(5,10);
        enemics1.add(e1);
        Enemic e2 = new Enemic(5,10);
        enemics1.add(e2);
        Enemic e3 = new Enemic(5,10);
        enemics1.add(e3);

        ArrayList<Enemic> enemics2 = new ArrayList<>();
        Enemic e4 = new Enemic(10,20);
        enemics2.add(e4);
        Enemic e5 = new Enemic(10,20);
        enemics2.add(e4);
        Enemic e6 = new Enemic(10,20);
        enemics2.add(e6);

        ArrayList<Enemic> enemics3 = new ArrayList<>();
        Enemic e7 = new Enemic(20,40);
        enemics3.add(e7);
        Enemic e8 = new Enemic(10,20);
        enemics3.add(e8);
        Enemic e9 = new Enemic(10,20);
        enemics3.add(e9);

        this.mazmorra[0] = new Planta(3, FLOOR1, enemics1);
        this.mazmorra[1] = new Planta(4, FLOOR2, enemics2);
        this.mazmorra[2] = new Planta(5, FLOOR3, enemics3);

        this.planta_actual = this.mazmorra[0];

        this.player.setInitialPos(38,19);

        boolean seguir = true;



        while (seguir) {
            screen.clear();

            if (currentState == GameState.EXPLORING) {
                screen.clear();            // SOLO limpiar cuando explores
                drawDungeon(screen, tg, j);
            } else if (currentState == GameState.INVENTORY) {
                // NO limpiar pantalla → el mapa permanece
                drawDungeon(screen, tg, j); // redibujar el mapa
                drawInventory(screen, tg);  // panel superpuesto
            }

            screen.refresh();

            KeyStroke key = screen.pollInput();
            if (key != null) {
                switch (key.getKeyType()) {
                    case ArrowUp -> {
                        if (currentState == GameState.EXPLORING) player.moveUp(this.planta_actual,this);
                    }
                    case ArrowDown -> {
                        if (currentState == GameState.EXPLORING) player.moveDown(this.planta_actual,this);
                    }
                    case ArrowLeft -> {
                        if (currentState == GameState.EXPLORING) player.moveLeft(this.planta_actual,this);
                    }
                    case ArrowRight -> {
                        if (currentState == GameState.EXPLORING) player.moveRight(this.planta_actual,this);
                    }
                    case Escape, EOF -> seguir = false;
                    default -> {
                        if (key.getCharacter() != null) {
                            char c = Character.toUpperCase(key.getCharacter());
                            if (c == 'I') {
                                // alternar entre modos
                                currentState = (currentState == GameState.EXPLORING)
                                        ? GameState.INVENTORY
                                        : GameState.EXPLORING;
                            }
                        }
                    }
                }
            }

            Thread.sleep(50);
        }


    }

    public void battle(int x, int y){
        Enemic e = this.planta_actual.getEnemy(x,y);

        int p_hp = this.player.getHP();

        p_hp =  p_hp - e.getAtk();

        if(p_hp <= 0){
            //game_over
        }else{
            this.player.setHP(p_hp);
            this.planta_actual.enemyDefeated();
        }


    }

    public void gameOver(){

    }

    public void giveItem(Item i){
        this.player.addItem(i);
    }

    public void passarPlanta(){

    }


}
