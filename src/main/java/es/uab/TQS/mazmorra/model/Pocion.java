package es.uab.TQS.mazmorra.model;

public class Pocion implements Item { //Cura segons la planta on es troba el jugador
  private int cura;

  private Jugador poseedor;

  public Pocion(Jugador j,int c) {
    this.cura = c;
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
    this.poseedor.setHP(this.poseedor.getHP() + cura);
  }
}
