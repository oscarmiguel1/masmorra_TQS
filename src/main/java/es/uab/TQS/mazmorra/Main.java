package es.uab.TQS.mazmorra;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import es.uab.TQS.mazmorra.model.Jugador;

public class Main {

    enum GameState {
        EXPLORING,
        INVENTORY
    }

    public static void main(String[] args) {

        Jugador j = new Jugador();

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
            j.setInitialPos(38, 19);

            boolean seguir = true;
            GameState currentState = GameState.EXPLORING;

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
                            if (currentState == GameState.EXPLORING) j.moveUp();
                        }
                        case ArrowDown -> {
                            if (currentState == GameState.EXPLORING) j.moveDown();
                        }
                        case ArrowLeft -> {
                            if (currentState == GameState.EXPLORING) j.moveleft();
                        }
                        case ArrowRight -> {
                            if (currentState == GameState.EXPLORING) j.moveRight();
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

            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void drawDungeon(Screen screen, TextGraphics tg, Jugador j) {
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(1, 22, "Inventari: [I]");
        tg.putString(60, 22, "Enemics restants:");
        tg.putString(40, 22, "LV: 1");
        tg.putString(30, 22, "HP: 0/0 ");

        String[] mazmorra = {
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

        tg.setForegroundColor(TextColor.ANSI.RED);
        for (int x = 0; x < 80; x++) tg.putString(x, 20, "_");
        for (int y = 0; y < mazmorra.length; y++) {
            String fila = mazmorra[y];
            for (int x = 0; x < fila.length(); x++) {
                tg.putString(x, y, String.valueOf(fila.charAt(x)));
            }
        }
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(j.getPos_x(), j.getPos_y(), "X");
    }

    private static void drawInventory(Screen screen, TextGraphics tg) {
        // Panel lateral
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        for (int y = 0; y < 15; y++) {
            tg.putString(0, y, "│");
            tg.putString(19, y, "│");
        }
        tg.putString(0, 0, "┌──────────────────┐");
        tg.putString(0, 14, "└──────────────────┘");

        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(2, 1, " INVENTARIO ");

        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(2, 3, "1. Espada oxidada");
        tg.putString(2, 4, "2. Escudo de madera");
        tg.putString(2, 5, "3. Poción de curación");

        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(2, 7, "[I] Cerrar invent.");
    }


}
