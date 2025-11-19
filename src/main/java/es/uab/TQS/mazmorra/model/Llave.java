package es.uab.TQS.mazmorra.model;

public class Llave implements Item {

  @Override
  public String getNom(){
    return "Llave";
  }

  @Override
  public String getDescripcio(){
    return "Abre la puerta de esta planta";
  }

  @Override
  public void usarItem(){
  }
}
