package es.uab.TQS.mazmorra.model;

public class Bomba implements Item{
  private Jugador poseedor;

  public Bomba(Jugador j) {
    this.poseedor = j;
  }

  @Override
  public String getNom() {
    return "Bomba";
  }

  @Override
  public String getDescripcio() {
    return "Elimina a tots els enemics";
  }

  @Override
  public void usarItem() {
  }
}
