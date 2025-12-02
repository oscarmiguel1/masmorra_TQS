package es.uab.TQS.mazmorra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class JugadorTest {

    @Test
    void testMoveRight() {
        Jugador j = new Jugador();
        Planta plantaMock = Mockito.mock(Planta.class);
        Joc jocMock = Mockito.mock(Joc.class);

        j.setInitialPos(0, 0);
        // No hi ha mur (#) ni enemic (E)
        Mockito.when(plantaMock.isValidPosition(1, 0)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(1, 0)).thenReturn(false);


        j.moveRight(plantaMock, jocMock);

        assertEquals(1, j.getPos_x());
        assertEquals(0, j.getPos_y());

        // ------------------------------------------------------------------

        j.setInitialPos(0, 0);
        // Hi ha mur (#) no enemic (E)
        Mockito.when(plantaMock.isValidPosition(1, 0)).thenReturn(false);
        Mockito.when(plantaMock.isEnemyPosition(1, 0)).thenReturn(false);

        j.moveRight(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(0, j.getPos_y());

        // ------------------------------------------------------------------


        j.setInitialPos(0, 0);
        // No mur (#) si enemic (E)
        Mockito.when(plantaMock.isValidPosition(1, 0)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(1, 0)).thenReturn(true);

        j.moveRight(plantaMock, jocMock);

        assertEquals(1, j.getPos_x());
        assertEquals(0, j.getPos_y());
    }

    @Test
    void testMoveLeft() {
        Jugador j = new Jugador();
        Planta plantaMock = Mockito.mock(Planta.class);
        Joc jocMock = Mockito.mock(Joc.class);

        j.setInitialPos(1, 0);
        // No hi ha mur (#) ni enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 0)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(0, 0)).thenReturn(false);


        j.moveLeft(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(0, j.getPos_y());

        // ------------------------------------------------------------------

        j.setInitialPos(1, 0);
        // Hi ha mur (#) no enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 0)).thenReturn(false);
        Mockito.when(plantaMock.isEnemyPosition(0, 0)).thenReturn(false);

        j.moveLeft(plantaMock, jocMock);

        assertEquals(1, j.getPos_x());
        assertEquals(0, j.getPos_y());

        // ------------------------------------------------------------------


        j.setInitialPos(1, 0);
        // No mur (#) si enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 0)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(0, 0)).thenReturn(true);

        j.moveLeft(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(0, j.getPos_y());
    }

    @Test
    void testMoveUp() {
        Jugador j = new Jugador();
        Planta plantaMock = Mockito.mock(Planta.class);
        Joc jocMock = Mockito.mock(Joc.class);

        j.setInitialPos(0, 1);
        // No hi ha mur (#) ni enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 0)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(0, 0)).thenReturn(false);


        j.moveUp(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(0, j.getPos_y());

        // ------------------------------------------------------------------

        j.setInitialPos(0, 1);
        // Hi ha mur (#) no enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 0)).thenReturn(false);
        Mockito.when(plantaMock.isEnemyPosition(0, 0)).thenReturn(false);

        j.moveUp(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(1, j.getPos_y());

        // ------------------------------------------------------------------


        j.setInitialPos(0, 1);
        // No mur (#) si enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 0)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(0, 0)).thenReturn(true);

        j.moveUp(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(0, j.getPos_y());
    }

    @Test
    void testMoveDown() {
        Jugador j = new Jugador();
        Planta plantaMock = Mockito.mock(Planta.class);
        Joc jocMock = Mockito.mock(Joc.class);

        j.setInitialPos(0, 0);
        // No hi ha mur (#) ni enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 1)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(0, 1)).thenReturn(false);


        j.moveDown(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(1, j.getPos_y());

        // ------------------------------------------------------------------

        j.setInitialPos(0, 0);
        // Hi ha mur (#) no enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 1)).thenReturn(false);
        Mockito.when(plantaMock.isEnemyPosition(0, 1)).thenReturn(false);

        j.moveDown(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(0, j.getPos_y());

        // ------------------------------------------------------------------


        j.setInitialPos(0, 0);
        // No mur (#) si enemic (E)
        Mockito.when(plantaMock.isValidPosition(0, 1)).thenReturn(true);
        Mockito.when(plantaMock.isEnemyPosition(0, 1)).thenReturn(true);

        j.moveDown(plantaMock, jocMock);

        assertEquals(0, j.getPos_x());
        assertEquals(1, j.getPos_y());
    }

    @Test
    void testSetHP() {
        Jugador j = new Jugador();

        j.setHP(-3);
        assertEquals(0, j.getHP());

        j.setHP(12);
        assertEquals(12, j.getHP());

        j.setHP(44);
        assertEquals(20, j.getHP());
    }

    @Test
    void testSetEXP() {
        Jugador j = new Jugador();


        //no hauria d'acceptar valors negatius
        j.setEXP(-3);
        assertEquals(0, j.getEXP());


        //setjament habitual
        j.setEXP(12);
        assertEquals(12, j.getEXP());


        j = new Jugador();

        //al sobrepasar l'exp necessaria, hauria d'ajustar-se al maxim
        j.setEXP(44);
        assertEquals(44, j.getEXP());
    }

    @Test
    void testOpenDoor() {
        Jugador j = new Jugador();
        Planta plantaMock = Mockito.mock(Planta.class);

        j.setInitialPos(0, 0);
        Mockito.when(plantaMock.getPlayerTile(0, 0)).thenReturn('M');

        j.openDoor(plantaMock);

        assertEquals(0, j.getPos_x());
        assertEquals(0, j.getPos_y());
    }

    @Test
    void testIsPlayerAtDoor() {
        Jugador j = new Jugador();
        Planta plantaMock = Mockito.mock(Planta.class);

        j.setInitialPos(0, 0);
        Mockito.when(plantaMock.getDoorposX()).thenReturn(0);
        Mockito.when(plantaMock.getDoorposY()).thenReturn(0);

        assertTrue(j.isPlayerAtDoor(plantaMock));
    }

    @Test
    public void lvUP(){
        Jugador j = new Jugador();

        //Cas normal
        j.lvUP();

        assertEquals(2, j.getLv());
        assertEquals(28, j.getMax_hp());
        assertEquals(140, j.getExp_necesaria());

        //Caixa Blanca: redondeig funcio math.round
        j = new Jugador();

        j.setMax_hp(14);
        j.lvUP();

        assertEquals(20, j.getMax_hp());

        //Assegurem que les estadistiques mai baixen

        j = new Jugador();
        int HPinicial = j.getMax_hp();

        j.lvUP();

        assertTrue(j.getMax_hp() > HPinicial);


    }


}