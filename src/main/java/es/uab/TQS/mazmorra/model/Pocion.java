package es.uab.TQS.mazmorra.model;

public class Pocion implements Item{
  private int cura = 50;

  @Override
  public String getNom(){
    return "Pocion de vida";
  }

  @Override
  public String getDescripcio(){
    return "Cura " + cura + "puntos de salud.";
  }

  @Override
  public void usarItem(Jugador j){
    j.setHP(cura);
  }
}
