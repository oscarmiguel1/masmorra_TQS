package es.uab.TQS.mazmorra.model;

public class Pocion implements Item{
  private int cura = 50;

  private Jugador poseedor;

  public Pocion(Jugador j){
      this.poseedor = j;
  }

  @Override
  public String getNom(){
    return "Pocion de vida";
  }

  @Override
  public String getDescripcio(){
    return "Cura " + cura + "puntos de salud.";
  }

  @Override
  public void usarItem(){
    this.poseedor.setHP(cura);
  }
}
