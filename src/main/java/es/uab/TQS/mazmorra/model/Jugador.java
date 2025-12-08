package es.uab.TQS.mazmorra.model;

import java.util.ArrayList;

public class Jugador {

  private int posX;
  private int posY;

  private final ArrayList<Item> inventari;

  private int hpActual;
  private int maxHp;

  private int exp;
  private int expNecesaria;
  private int lv;

  public Jugador() {
    this.posX = 0;
    this.posY = 0;
    this.setStatsInicials();
    this.inventari = new ArrayList<>();
  }

  public void setStatsInicials() {
    this.maxHp = 20;
    this.hpActual = this.maxHp;
    this.exp = 0;
    this.expNecesaria = 100;
    this.lv = 1;
  }

  public int getLvl() {
    return this.lv;
  }

  public int getPos_x() {
    return this.posX;
  }

  public int getPos_y() {
    return this.posY;
  }

  public int getHP() {
    return this.hpActual;
  }

  public int getMax_hp() {
    return this.maxHp;
  }

  public int getExp_necesaria() {
    return this.expNecesaria;
  }

  public int getEXP() {
    return this.exp;
  }

  public int getLv() {
    return this.lv;
  }

  public ArrayList<Item> getInventari() {
    return this.inventari;
  }

  public void addItem(Item i) {
    this.inventari.add(i);
  }

  public void moveRight(Planta p, Joc j) {
    int newX = posX + 1; // movimiento provisional
    int newY = posY;

    if (p.isValidPosition(newX, newY)) {
      if (p.isEnemyPosition(newX, newY)) {
        posX = newX;
        j.battle(posX, posY);
      } else {
        posX = newX;
      } // solo movemos si es válido
    }
  }

  public void moveLeft(Planta p, Joc j) {
    int newX = posX - 1;
    int newY = posY;

    if (p.isValidPosition(newX, newY)) {
      if (p.isEnemyPosition(newX, newY)) {
        posX = newX;
        j.battle(posX, posY);
      } else {
        posX = newX;
      } // solo movemos si es válido
    }
  }

  public void moveUp(Planta p, Joc j) {
    int newX = posX;
    int newY = posY - 1;

    if (p.isValidPosition(newX, newY)) {
      if (p.isEnemyPosition(newX, newY)) {
        posY = newY;
        j.battle(posX, posY);
      } else {
        posY = newY;
      } // solo movemos si es válido
    }
  }

  public void moveDown(Planta p, Joc j) {
    int newX = posX;
    int newY = posY + 1;

    if (p.isValidPosition(newX, newY)) {
      if (p.isEnemyPosition(newX, newY)) {
        posY = newY;
        j.battle(posX, posY);
      } else {
        posY = newY;
      } // solo movemos si es válido
    }
  }

  public void setInitialPos(int x, int y) {
    this.posX = x;
    this.posY = y;
  }

  public void setMax_hp(int m) {
    this.maxHp = m;
  }

  public void setHP(int h) {
    if (h >= this.maxHp) {
      this.hpActual = this.maxHp;
    } else {
      if (h < 0) {
        this.hpActual = 0;
      } else {
        this.hpActual = h;
      }
    }

  }

  public void openDoor(Planta p) {
    if (p.getPlayerTile(this.posX, this.posY) == 'M') {
      p.openDoor();
    }
  }

  public void setEXP(int e) {
    if (e >= this.expNecesaria) {
      this.exp = 0;
      lvUP();
    } else {
      if (e < 0) {
        this.exp = 0;
      } else {
        this.exp = e;
      }
    }
  }

  public boolean isPlayerAtDoor(Planta p) {
    if (p.getDoorposX() == this.posX && p.getDoorposY() == this.posY) {
      return true;
    } else {
      return false;
    }
  }

  public void lvUP() {
    this.lv++;
    this.maxHp = this.maxHp + (int) Math.round(this.maxHp * 0.4);
    this.hpActual = this.hpActual + (int) Math.round(this.maxHp * 0.2);
    this.expNecesaria = this.expNecesaria + (int) Math.round(this.expNecesaria * 0.4);
  }

}
