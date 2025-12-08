package es.uab.TQS.mazmorra.model;

public class Llave implements Item { //Obre la porta de cada planta

  private Jugador poseedor;

  public Llave(Jugador j) {
    this.poseedor = j;
  }

  @Override
  public String getNom() {
    return "Clau";
  }

  @Override
  public String getDescripcio() {
    return "Obre la porta d'aquesta planta";
  }

  @Override
  public void usarItem() {
  }
}
