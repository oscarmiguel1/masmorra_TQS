//Planta
package es.uab.TQS.mazmorra.model;

public class Planta {

  private boolean doorOpen;


  private String[] floorLayout;

  private int enemiesLeft;

  public Planta(int e, String[] f){
    this.doorOpen = false;
    this.enemiesLeft = e;
    this.floorLayout = f;
  }

  public void openDoor() {this.doorOpen = true;}
  public int getEnemiesLeft(){ return this.enemiesLeft;}

  public boolean getDoorState() { return this.doorOpen; }
  public void enemyDefeated() {
    if(this.enemiesLeft-1 >= 0) {
      this.enemiesLeft--;
    }
  }


}
