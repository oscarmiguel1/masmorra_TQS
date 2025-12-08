package es.uab.TQS.mazmorra.model;

import java.util.ArrayList;

public class Planta {

  private final int doorPosX;
  private final int doorPosY;
  private boolean doorOpen;
  private final String[] floorLayout;
  private final ArrayList<Enemic> enemics;
  private int enemiesLeft;

  public Planta(int e, String[] f, ArrayList<Enemic> en, int dx, int dy) {
    this.doorPosX = dx;
    this.doorPosY = dy;
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
    return null; // no hi ha enemic a la casella
  }

  public void setEnemiesLeft(int e) {
    this.enemiesLeft = e;
  }

  public String[] getFloorLayout() {
    return this.floorLayout;
  }

  public char getPlayerTile(int x, int y) {
    return this.floorLayout[y].charAt(x);
  }

  public ArrayList<Enemic> getEnemies() {
    return this.enemics;
  }

  public int getDoorposX() {
    return this.doorPosX;
  }

  public int getDoorposY() {
    return this.doorPosY;
  }

  public int getEnemiesLeft() {
    return this.enemiesLeft;
  }

  public boolean isValidPosition(int x, int y) { //comprovem que la posicio es valida per al jugador: es troba dins dels limits i no vol anar a un mur
    int limX = 79;
    int limY = 19;
    return x >= 0 && x <= limX
            && y >= 0
            && y <= limY
            && this.floorLayout[y].charAt(x) != '#';
  }

  public boolean isEnemyPosition(int x, int y) {
    for (Enemic e : enemics) {
      if (e.getPos_x() == x && e.getPos_y() == y) {
        return true;
      }
    }
    return false;
  }

  public boolean isDoorPosition(int x, int y) {
    if (doorPosX == x && doorPosY == y) {
      return true;
    }
    return false;
  }

  public void openDoor() {
    this.doorOpen = true;
  }

  public void enemyDefeated(int x, int y) {
    if (this.enemiesLeft - 1 >= 0) {
      this.enemiesLeft--;
      for (int i = 0; i < this.enemics.size(); i++) {
        if (this.enemics.get(i).getPos_x() == x && this.enemics.get(i).getPos_y() == y) {
          this.enemics.remove(i);
        }
      }
    } else {
      this.enemiesLeft = 0;
      this.enemics.clear();
    }
  }

}
