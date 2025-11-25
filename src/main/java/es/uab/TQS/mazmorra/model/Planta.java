//Planta
package es.uab.TQS.mazmorra.model;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;

public class Planta {

    public static char WALL = '#';
    public static String FLOOR = ".";
    public static String PLAYER = "X";
    public static String ENEMY = "E";
    public static String DOOR = "M";

    private final int door_posX;
    private final int door_posY;

    private boolean doorOpen;


    private final String[] floorLayout;
    private final ArrayList<Enemic> enemics;
    private int enemiesLeft;

  public Planta(int e, String[] f, ArrayList<Enemic> en){
    this.door_posX = 20;
    this.door_posY = 2;
    this.doorOpen = false;
    this.enemiesLeft = e;
    this.floorLayout = f;
    this.enemics = en;
  }

  public Enemic getEnemy(int x, int y) {
        for (Enemic e : enemics) {
            if (e.getPos_x() == x && e.getPos_y() == y) {
                return e;
            }
        }
        return null; // No hay enemigo en esa casilla
    }

  public boolean isValidPosition(int x, int y){
      int lim_x = 79;
      int lim_y = 19;
      return x >= 0 && x <= lim_x &&
              y >= 0 && y <= lim_y &&
              this.floorLayout[y].charAt(x) != '#';
  }

  public String[] getFloorLayout(){
      return this.floorLayout;
  }

 public boolean isEnemyPosition(int x, int y) {
    for (Enemic e : enemics) {
        if (e.getPos_x() == x && e.getPos_y() == y) {
            return true;
        }
    }
    return false;
 }

  public char getPlayerTile(int x, int y){
      return this.floorLayout[y].charAt(x);
  }

  public void openDoor() {
      this.doorOpen = true;
  }
  public int getEnemiesLeft(){ return this.enemiesLeft;}

  public boolean getDoorState() { return this.doorOpen; }

  public void enemyDefeated() {
    if(this.enemiesLeft-1 >= 0) {
      this.enemiesLeft--;
      this.enemics.removeFirst();
    }else{
        this.enemiesLeft = 0;
        this.enemics.clear();
    }
  }


    public void dibuixarMazmorra(Screen screen, TextGraphics tg, Jugador j) {
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(1, 22, "Inventari: [I]");
        tg.putString(60, 22, "Enemics restants: " + this.enemiesLeft);
        tg.putString(40, 22, "LV: " + j.getLvl());
        tg.putString(28, 22, "HP: " + j.getHP()  + "/" + j.getMax_hp());

        String[] mazmorra =  this.floorLayout;

        tg.setForegroundColor(TextColor.ANSI.RED);
        for (int x = 0; x < 80; x++) tg.putString(x, 20, "_");
        for (int y = 0; y < mazmorra.length; y++) {
            String fila = mazmorra[y];
            for (int x = 0; x < fila.length(); x++) {
                tg.putString(x, y, String.valueOf(fila.charAt(x)));
            }
        }

        //Dibujamos puerta
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(door_posX,door_posY,DOOR);

        //Dibujamos enemigos
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        for (Enemic enemic : this.enemics) {
            tg.putString(enemic.getPos_x(), enemic.getPos_y(), ENEMY);
        }

        //Dibujamos Jugador
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
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



}
