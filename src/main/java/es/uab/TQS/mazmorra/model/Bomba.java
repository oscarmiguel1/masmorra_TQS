package es.uab.TQS.mazmorra.model;

public class Bomba implements Item { //Item que elimina entre 1 i 3 enemics d'una planta de manera aleatoria.
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
    return "Elimina entre 1 i 3 enemics";
  }

  @Override
  public void usarItem() {
  }
}
