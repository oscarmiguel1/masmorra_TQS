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
    public static void main(String[] args) {

        Jugador j = new Jugador();
        try {

            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setForceAWTOverSwing(false)  // asegura compatibilidad
                    .setTerminalEmulatorTitle("Mazmorra")
                    .setPreferTerminalEmulator(true); // fuerza modo ventana


            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null); // oculta el cursor

            TextGraphics tg = screen.newTextGraphics();
            j.setInitialPos(38,19);
            boolean seguir = true;

            while (seguir) {
                screen.clear();

                //Pantalla: 24(filas)x80(columnas)
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(1,22,"Inventari: [I]");
                tg.putString(60,22,"Enemics restants:");
                tg.putString(40,22,"LV: 1");
                tg.putString(30,22,"HP: 0/0 ");


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
                "###########     ###################......................#######      ##########",
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
                        char c = fila.charAt(x);
                        tg.putString(x, y, String.valueOf(c));
                    }
                }
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(j.getPos_x(), j.getPos_y(), "X");

                screen.refresh();

                KeyStroke key = screen.pollInput();
                if (key != null) {
                    switch (key.getKeyType()) {
                        case ArrowUp -> j.moveUp();
                        case ArrowDown -> j.moveDown();
                        case ArrowLeft -> j.moveleft();
                        case ArrowRight -> j.moveRight();
                        case Escape, EOF -> seguir = false;
                    }
                }

                Thread.sleep(50);
            }

            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}