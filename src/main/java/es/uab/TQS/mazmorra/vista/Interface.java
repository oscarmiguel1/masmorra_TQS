package es.uab.TQS.mazmorra.vista;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import es.uab.TQS.mazmorra.model.Enemic;
import es.uab.TQS.mazmorra.model.Jugador;
import es.uab.TQS.mazmorra.model.Planta;

import java.io.IOException;

public class Interface { //clase para dibujar la vista

    public static char WALL = '#';
    public static String FLOOR = ".";
    public static String PLAYER = "X";
    public static String ENEMY = "E";
    public static String DOOR = "M";


    public void dibuixarMazmorra(Screen screen, TextGraphics tg, Jugador j, Planta p) {

        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(1, 22, "Inventari: [I]");
        tg.putString(60, 22, "Enemics restants: " + p.getEnemiesLeft());
        tg.putString(40, 22, "LV: " + j.getLvl());
        tg.putString(28, 22, "HP: " + j.getHP()  + "/" + j.getMax_hp());

        String[] mazmorra =  p.getFloorLayout();


        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        for (int x = 0; x < 80; x++) tg.putString(x, 20, "_");
        for (int y = 0; y < mazmorra.length; y++) {
            String fila = mazmorra[y];
            for (int x = 0; x < fila.length(); x++) {
                tg.putString(x, y, String.valueOf(fila.charAt(x)));
            }
        }

        //Dibujamos puerta
        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        tg.putString(p.getDoorposX(),p.getDoorposY(),DOOR);

        //Dibujamos enemigos
        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        for (Enemic enemic : p.getEnemies()) {
            tg.putString(enemic.getPos_x(), enemic.getPos_y(), ENEMY);
        }

        //Dibujamos Jugador
        tg.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
        tg.putString(j.getPos_x(), j.getPos_y(), PLAYER);
    }


    public void dibuixarInventari(Screen screen, TextGraphics tg, Jugador j, int idx) {

        // Establecer color de fondo del panel
        tg.setBackgroundColor(TextColor.ANSI.BLACK);
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        tg.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(24, 15), ' ');

        // Panel lateral
        for (int y = 0; y < 15; y++) {
            tg.putString(0, y, "│");
            tg.putString(24, y, "│");
        }
        tg.putString(0, 0,  "┌───────────────────────┐");
        tg.putString(0, 14, "└───────────────────────┘");

        // Título
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(6, 1, " INVENTARI ");

        // ITEMS DEL INVENTARIO
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        if (j.getInventari().isEmpty()) {
            tg.putString(2, 3, "No tens cap item");  // No hay items
            tg.putString(4, 13, "[I] Tancar invent.");
            return;
        } else {
            for (int i = 0; i < j.getInventari().size(); i++) {
                // Si el cursor está en este item → resaltarlo
                if (i == idx) {
                    tg.setForegroundColor(TextColor.ANSI.GREEN);     // Color destacado
                    tg.putString(2, i + 3, "> " + (i + 1) + ". " + j.getInventari().get(i).getNom());
                } else {
                    tg.setForegroundColor(TextColor.ANSI.WHITE);
                    tg.putString(2, i + 3, "  " + (i + 1) + ". " + j.getInventari().get(i).getNom());
                }
            }
        }

        // Instrucciones
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        tg.putString(3,8,j.getInventari().get(idx).getDescripcio());
        tg.putString(2, 10, "Per utilitzar un item,");
        tg.putString(8, 11, "premi ENTER");

        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(4, 13, "[I] Tancar invent.");
    }

    public void gameOver(Screen screen) throws IOException {
        screen.clear();

        // Texto "GAME OVER"
        String msg = "== GAME OVER ==";
        String retryMsg = "Pulsa ENTER para reintentar";

        // Pintar texto en el centro aproximado
        screen.newTextGraphics().putString(10, 5, msg);
        screen.newTextGraphics().putString(10, 7, retryMsg);

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Esperar a que se pulse ENTER
        KeyStroke key;
        do {
            key = screen.readInput();
        } while (key == null || key.getKeyType() != com.googlecode.lanterna.input.KeyType.Enter);

        // Limpiar y continuar el juego
        screen.clear();
        screen.refresh();
    }

    public void ajudaPorta(Screen screen, TextGraphics tg) {
        int startX = 59;
        int startY = 16;

        tg.setBackgroundColor(TextColor.ANSI.BLACK);
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.fillRectangle(new TerminalPosition(startX, startY), new TerminalSize(30, 4), ' ');

        tg.putString(startX, startY,     "┌────────────────────────────┐");
        tg.putString(startX, startY + 3,"└────────────────────────────┘");
        tg.putString(startX, startY + 1,"│ No estàs a una porta!     │");
        tg.putString(startX, startY + 2,"│ Intenta apropar-te.       │");
    }

    public void mostrarItemTrobat(Screen screen, TextGraphics tg,String nom) {
        int startX = 59;
        int startY = 16;

        tg.setBackgroundColor(TextColor.ANSI.BLACK);
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.fillRectangle(new TerminalPosition(startX, startY), new TerminalSize(30, 4), ' ');

        tg.putString(startX, startY,     "┌────────────────────────────┐");
        tg.putString(startX, startY + 3,"└────────────────────────────┘");
        tg.putString(startX, startY + 1,"│    Has trobat una         │");
        tg.putString(startX, startY + 2,"│     " + nom + "!          │");
    }






}
