package es.uab.TQS.mazmorra.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PocionTest {

  @Test
  void testUsarItem(){
    //Cas normal: verifiquem (mitjancant un mock de Jugador) que la salut augmenta
    Jugador p = Mockito.mock(Jugador.class);
    Item pocion = new Pocion(p);
    when(p.getHP()).thenReturn(50);

    pocion.usarItem();

    verify(p).setHP(60); //verifiquem que es fa una crida mock de pujar la HP

    //Cas normal: multiples usos
    Jugador p2 = Mockito.mock(Jugador.class);
    Item pocion2 = new Pocion(p2);
    when(p2.getHP()).thenReturn(50, 60, 70);

    pocion2.usarItem();
    pocion2.usarItem();
    pocion2.usarItem();

    verify(p2).setHP(60);
    verify(p2).setHP(70);
    verify(p2).setHP(80);
  }
}
