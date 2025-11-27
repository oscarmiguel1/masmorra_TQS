package es.uab.TQS.mazmorra.model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import es.uab.TQS.mazmorra.vista.Interface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Joc {

    enum GameState {
        EXPLORING,
        INVENTORY,
        GAME_OVER,
        INFO
    }

    public static String[] FLOOR1 = {
            "################################################################################",
            "#..............###########.....................#########..............##########",
            "#..............#.........#.....................#.......#..............#........#",
            "#..............#........##########....#########.......#######..#######.........#",
            "#..............#..................#....#.....................#..#..............#",
            "#..............###########........######...........###########..#..............#",
            "#........................#.............#..........#.............#..............#",
            "#........................#.............#........................#..............#",
            "#######...####...############.....#....#........................#######..#######",
            "#.....#...#.................#.....#....#..........#.............#.....#..#.....#",
            "#.....#####.................#######....############.............#.....####.....#",
            "#.................................#......................#......#..............#",
            "#.................................#......................#......#..............#",
            "###########.....#########....######......................########.....##########",
            "#.........#.....#.....................................................#........#",
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

    long infoMessageEndTime = 0;

    private final Jugador player;
    private Planta[] mazmorra;
    private Planta planta_actual;
    int num_planta;
    boolean game_over;
    GameState currentState;
    Interface hud;
    Item itemActual;

    public Joc(Jugador j) {
        this.player = j;
        this.currentState = GameState.EXPLORING;
        this.hud = new Interface();
        this.game_over = false;
        this.itemActual = null;
    }

    public void missatgeTemporal(long duracionMs) {
        currentState = GameState.INFO;
        infoMessageEndTime = System.currentTimeMillis() + duracionMs;
    }

    public void startGame() {
        this.mazmorra = new Planta[3];
        this.num_planta = 0;

        ArrayList<Enemic> enemics1 = new ArrayList<>();
        Enemic e1 = new Enemic(5, 50, 7, 4);
        enemics1.add(e1);
        Enemic e2 = new Enemic(5, 50, 59, 4);
        enemics1.add(e2);
        Enemic e3 = new Enemic(5, 50, 76, 10);
        enemics1.add(e3);

        ArrayList<Enemic> enemics2 = new ArrayList<>();
        Enemic e4 = new Enemic(10, 20, 4, 7);
        enemics2.add(e4);
        Enemic e5 = new Enemic(10, 20, 4, 7);
        enemics2.add(e5);
        Enemic e6 = new Enemic(10, 20, 4, 7);
        enemics2.add(e6);

        ArrayList<Enemic> enemics3 = new ArrayList<>();
        Enemic e7 = new Enemic(20, 40, 4, 7);
        enemics3.add(e7);
        Enemic e8 = new Enemic(10, 20, 4, 7);
        enemics3.add(e8);
        Enemic e9 = new Enemic(10, 20, 4, 7);
        enemics3.add(e9);

        this.mazmorra[0] = new Planta(3, FLOOR1, enemics1);
        this.mazmorra[1] = new Planta(4, FLOOR2, enemics2);
        this.mazmorra[2] = new Planta(5, FLOOR3, enemics3);

        this.planta_actual = this.mazmorra[0];
        this.player.setInitialPos(38, 19);


        boolean jugando = true;

        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setForceAWTOverSwing(false)
                    .setTerminalEmulatorTitle("Mazmorra")
                    .setPreferTerminalEmulator(true);

            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            TextGraphics tg = screen.newTextGraphics();
            int idx = 0;

            while (jugando) {
                screen.clear();

                switch (currentState) {
                    case EXPLORING -> hud.dibuixarMazmorra(screen, tg, this.player, this.planta_actual);
                    case INVENTORY -> {
                        hud.dibuixarMazmorra(screen, tg, this.player, this.planta_actual);
                        hud.dibuixarInventari(screen, tg, this.player, idx);
                    }
                    case GAME_OVER -> hud.gameOver(screen);
                    case INFO -> {
                        hud.dibuixarMazmorra(screen, tg, this.player, this.planta_actual);

                        if(itemActual == null){
                            hud.ajudaPorta(screen, tg);
                        }else{
                            hud.mostrarItemTrobat(screen, tg, itemActual.getNom());
                        }
                    }
                }

                screen.refresh();

                if (currentState == GameState.INFO && System.currentTimeMillis() > infoMessageEndTime) {
                    currentState = GameState.EXPLORING;
                    this.itemActual = null;
                }

                KeyStroke key = screen.pollInput();
                if (key != null) {
                    switch (key.getKeyType()) {
                        case ArrowUp -> {
                            if (currentState == GameState.EXPLORING)
                                this.player.moveUp(this.planta_actual, this);
                            else if (currentState == GameState.INVENTORY && !player.getInventari().isEmpty()) {
                                idx = Math.max(0, idx - 1);
                            }
                        }
                        case ArrowDown -> {
                            if (currentState == GameState.EXPLORING)
                                player.moveDown(this.planta_actual, this);
                            else if (currentState == GameState.INVENTORY && !player.getInventari().isEmpty()) {
                                idx = Math.min(this.player.getInventari().size() - 1, idx + 1);
                            }
                        }
                        case ArrowLeft -> {
                            if (currentState == GameState.EXPLORING)
                                player.moveLeft(this.planta_actual, this);
                        }
                        case ArrowRight -> {
                            if (currentState == GameState.EXPLORING)
                                player.moveRight(this.planta_actual, this);
                        }
                        case Enter -> {
                            if (currentState == GameState.INVENTORY && !this.player.getInventari().isEmpty()) {
                                Item item = this.player.getInventari().get(idx);
                                if (Objects.equals(item.getNom(), "Clau")) {
                                    if (this.planta_actual.isDoorPosition(this.player.getPos_x(), this.player.getPos_y())) {
                                        item.usarItem();
                                        this.player.getInventari().remove(idx);
                                        passarPlanta();
                                    } else {
                                        missatgeTemporal(2000);
                                    }
                                } else {
                                    item.usarItem();
                                    this.player.getInventari().remove(idx);
                                }
                                if (idx >= this.player.getInventari().size()) idx = 0;
                            }
                        }
                        case Escape, EOF -> jugando = false;

                        default -> {
                            if (key.getCharacter() != null) {
                                char c = Character.toUpperCase(key.getCharacter());
                                if (c == 'I') {
                                    idx = 0;
                                    currentState = (currentState == GameState.EXPLORING)
                                            ? GameState.INVENTORY
                                            : GameState.EXPLORING;
                                }
                            }
                        }
                    }
                }
                Thread.sleep(5);
            }
            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.num_planta++;
        this.planta_actual = mazmorra[num_planta];
        this.player.setInitialPos(38, 19);
    }


}
