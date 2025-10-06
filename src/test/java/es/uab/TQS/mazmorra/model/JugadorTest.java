package es.uab.TQS.mazmorra.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class JugadorTest {
    

    @Test
    void testsetHP() {
       Jugador j = new Jugador();

       j.setHP(-3);
       assertTrue(j.getHP()==0);

       j.setHP(12);
       assertTrue(j.getHP() == 12);

       j.setHP(44);
       assertTrue(j.getHP() == 20);
    }
}
