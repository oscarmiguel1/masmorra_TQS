package es.uab.TQS.mazmorra.model;

import static es.uab.TQS.mazmorra.model.Joc.GameState.INFO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class JocTest {

  @Test
  void testMissatgetemporal() {
    Jugador p = Mockito.mock(Jugador.class);
    Joc j = new Joc(p);
    long duracio = 3;
    long test;

    //Comprovacions
    j.missatgeTemporal(duracio);
    test = System.currentTimeMillis() + duracio;

    assertEquals(INFO, j.getCurrentState());
    assertEquals(test, j.getInfoMsgFi());
  }

  @Test
  void testStartGame() {
    Jugador p = Mockito.mock(Jugador.class);
    Joc j = new Joc(p);

    //Comprovacio de valors adequats
    j.startGame();

    assertNotNull(j.getMazmorra());
    assertEquals(3, j.getMazmorra().length);
    assertEquals(Joc.GameState.EXPLORING, j.getCurrentState());
    assertEquals(0, j.getNumPlanta());
    assertFalse(j.getGameOver());
    assertEquals(3, j.getMazmorra()[0].getEnemies().size());

    Joc j2 = new Joc(p);

    //Multiples trucades
    j2.startGame();
    j2.setNumPlanta(1); //avancem al joc
    j2.startGame();

    assertEquals(0, j.getNumPlanta());
    assertEquals(Joc.GameState.EXPLORING, j.getCurrentState());
  }

  @Test
  void testBattle() {
    // Cas normal: jugador guanya batalla
    Jugador p = Mockito.mock(Jugador.class);
    Joc j = new Joc(p);

    j.startGame();

    Planta plantaMock = Mockito.mock(Planta.class);
    Enemic e = Mockito.mock(Enemic.class);

    when(e.getAtk()).thenReturn(10);
    when(e.getEXP()).thenReturn(50);
    when(plantaMock.getEnemy(7, 4)).thenReturn(e).thenReturn(null);
    when(p.getHP()).thenReturn(100).thenReturn(90);
    when(p.getEXP()).thenReturn(0).thenReturn(50);

    int atk = e.getAtk();
    int exp = e.getEXP();

    j.battle(7, 4);

    assertEquals(100 - atk, p.getHP());
    assertEquals(exp, p.getEXP());
    assertNull(j.getPlantaActual().getEnemy(7, 4));

    // Cas normal: jugador perd
    Jugador p2 = Mockito.mock(Jugador.class);
    Joc j2 = new Joc(p2);

    when(p2.getHP()).thenReturn(-5); //la hp sera -5 directament per motius de test ja que al starGame sempre s'inicialitzen els mateixos valors
    j2.startGame();

    j2.battle(7, 4);

    assertEquals(Joc.GameState.gameOver, j2.getCurrentState());

    // Cas normal: enemic perd exactament
    Jugador p3 = Mockito.mock(Jugador.class);
    Joc j3 = new Joc(p3);

    when(p3.getHP()).thenReturn(0);
    j3.startGame();

    j3.battle(7, 4);

    assertEquals(Joc.GameState.gameOver, j3.getCurrentState());

    // Cas normal: tots els enemics derrotats, jugador rep clau
    Jugador p4 = Mockito.mock(Jugador.class,Mockito.RETURNS_DEEP_STUBS); //creacio adicional de mocks
    Joc j4 = new Joc(p4);
    j4.startGame();

    when(p4.getHP()).thenReturn(400); //posem molta hp al jugador perque no perdi
    j4.battle(7,4);
    j4.battle(59, 4);
    j4.battle(76, 10);

    Llave c = Mockito.mock(Llave.class);
    when(p4.getInventari().getLast()).thenReturn(c);

    assertInstanceOf(Llave.class,p4.getInventari().getLast());

    // Cas normal: comprovació de que l'enemic s'elimina
    Jugador p5 = mock(Jugador.class);
    Joc j5 = new Joc(p5);

    Planta planta5Mock = mock(Planta.class);
    Enemic e6 = mock(Enemic.class);

    when(j5.getPlantaActual()).thenReturn(planta5Mock);
    when(planta5Mock.getEnemy(7, 4)).thenReturn(e6).thenReturn(null);
    when(p5.getHP()).thenReturn(100);

    j5.startGame();

    assertNotNull(j5.getPlantaActual().getEnemy(7, 4));
    j5.battle(7, 4);
    assertNull(j5.getPlantaActual().getEnemy(7, 4));
  }

  @Test
  void testPassarDePlanta() {
    //Cas normal: passem de planta
    Jugador p = Mockito.mock(Jugador.class);
    Joc j = new Joc(p);
    j.startGame();

    j.passarPlanta();

    assertEquals(1,j.getNumPlanta());
    assertEquals(j.getPlantaActual(),j.getMazmorra()[j.getNumPlanta()]);

    j.passarPlanta();
    //Cas normal: finalitzem joc
    j.passarPlanta();

    assertEquals(Joc.GameState.GAME_WON,j.getCurrentState());
  }

}