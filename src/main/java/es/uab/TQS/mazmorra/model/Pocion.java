package es.uab.TQS.mazmorra.model;

public class Pocion implements Item {
  private int cura = 10;

  private Jugador poseedor;

  public Pocion(Jugador j) {
    this.poseedor = j;
  }

  @Override
  public String getNom() {
    return "Poció de vida";
  }

  @Override
  public String getDescripcio() {
    return "Cura " + cura + " HP";
  }

  @Override
  public void usarItem() {
    this.poseedor.setHP(cura);
  }
}
