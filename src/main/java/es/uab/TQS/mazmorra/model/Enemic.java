package es.uab.TQS.mazmorra.model;

public class Enemic {

  private int atk;

  private int posX;
  private int posY;

  private int givenExp;

  public int getPos_x() {
    return this.posX;
  }

  public int getPos_y() {
    return this.posY;
  }

  public int getAtk() {
    return this.atk;
  }

  public int getEXP() {
    return this.givenExp;
  }

  public Enemic(int a, int exp, int x, int y) {
    this.posX = x;
    this.posY = y;
    this.atk = a;
    this.givenExp = exp;
  }

}
