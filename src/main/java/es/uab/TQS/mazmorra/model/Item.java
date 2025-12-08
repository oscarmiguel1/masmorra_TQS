package es.uab.TQS.mazmorra.model;

public interface Item { //interface de la qual surgeixen les classes Pocio, Llave i Bomba, els diferents items

  public String getNom();

  public String getDescripcio();

  public void usarItem();

}
