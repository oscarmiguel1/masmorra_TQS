package es.uab.TQS.mazmorra.model;

public class Llave implements Item {

    private Jugador poseedor;

  public Llave(Jugador j){
      this.poseedor = j;
  }

  @Override
  public String getNom(){
    return "Clau";
  }

  @Override
  public String getDescripcio(){
    return "Obre la porta d'aquesta planta";
  }

  @Override
  public void usarItem(){
  }
}
