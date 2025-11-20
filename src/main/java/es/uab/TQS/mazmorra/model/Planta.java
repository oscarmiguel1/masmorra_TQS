//Planta
package es.uab.TQS.mazmorra.model;

import java.util.ArrayList;

public class Planta {

    public static char WALL = '#';
    public static String FLOOR = ".";
    public static String PLAYER = "X";
    public static String ENEMY = "O";
    public static char DOOR = 'M';
    private static int lim_x = 79;
    private static int lim_y = 19;


    private boolean doorOpen;


    private String[] floorLayout;
    private ArrayList<Enemic> enemics;
    private int enemiesLeft;

  public Planta(int e, String[] f, ArrayList<Enemic> en){
    this.doorOpen = false;
    this.enemiesLeft = e;
    this.floorLayout = f;
    this.enemics = en;
  }

  public boolean isValidPosition(int x, int y){
      return x >= 0 && x <= lim_x &&
              y >= 0 && y <= lim_y &&
              this.floorLayout[y].charAt(x) != '#';
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
    }
  }


}
