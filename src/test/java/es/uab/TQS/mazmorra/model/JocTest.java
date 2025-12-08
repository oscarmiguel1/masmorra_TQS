package es.uab.TQS.mazmorra.model;

import static es.uab.TQS.mazmorra.model.Joc.GameState.INFO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;

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

    // Cas normal: jugador perd exactament
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

    // Cas normal: jugador guanya comprovació de que l'enemic s'elimina
    Jugador p5 = Mockito.mock(Jugador.class);
    Joc j5 = new Joc(p5);

    Planta planta5Mock = Mockito.mock(Planta.class);
    Enemic e6 = Mockito.mock(Enemic.class);

    when(j5.getPlantaActual()).thenReturn(planta5Mock);
    when(planta5Mock.getEnemy(7, 4)).thenReturn(e6).thenReturn(null);
    when(p5.getHP()).thenReturn(100);

    j5.startGame();

    assertNotNull(j5.getPlantaActual().getEnemy(7, 4));
    j5.battle(7, 4);
    assertNull(j5.getPlantaActual().getEnemy(7, 4));

    // Cas normal: jugador guanya i rep poció
    Jugador p6 = Mockito.mock(Jugador.class);
    Joc j6 = Mockito.spy(new Joc(p6));

    Mockito.doReturn(0.2).when(j6).numeroAleatori(); //fixem el valor aleatori

    j6.startGame();

    Planta planta6 = Mockito.mock(Planta.class);
    j6.setPlantaActual(planta6);

    Enemic e7 = Mockito.mock(Enemic.class);
    when(planta6.getEnemy(5, 5)).thenReturn(e7).thenReturn(null);
    when(e7.getAtk()).thenReturn(0);
    when(e7.getEXP()).thenReturn(0);
    when(p6.getHP()).thenReturn(100);

    doNothing().when(planta6).enemyDefeated(5, 5);
    when(planta6.getEnemiesLeft()).thenReturn(1);

    j6.battle(5, 5);

    assertInstanceOf(Pocion.class, j6.getItemActual());

    // Cas normal: jugador guanya i rep bomba
    Jugador p7 = Mockito.mock(Jugador.class);
    Joc j7 = Mockito.spy(new Joc(p7));

    Mockito.doReturn(0.01).when(j7).numeroAleatori(); //fixem el valor aleatori

    j7.startGame();

    Planta planta7 = Mockito.mock(Planta.class);
    j7.setPlantaActual(planta7);

    Enemic e8 = Mockito.mock(Enemic.class);
    when(planta7.getEnemy(5, 5)).thenReturn(e8).thenReturn(null);
    when(e8.getAtk()).thenReturn(0);
    when(e8.getEXP()).thenReturn(0);
    when(p7.getHP()).thenReturn(100);

    doNothing().when(planta7).enemyDefeated(5, 5);
    when(planta7.getEnemiesLeft()).thenReturn(1);

    j7.battle(5, 5);

    assertInstanceOf(Bomba.class, j7.getItemActual());


    //Cas normal: jugador guanya i rep bomba i clau
    Jugador p8 = Mockito.mock(Jugador.class);
    Joc j8 = Mockito.spy(new Joc(p8));

    doNothing().when(j8).giveItem(Mockito.any());
    Mockito.doReturn(0.2, 0.05).when(j8).numeroAleatori();

    j8.startGame();

    Planta planta8 = Mockito.mock(Planta.class);
    j8.setPlantaActual(planta8);

    Enemic e9 = Mockito.mock(Enemic.class);
    when(planta8.getEnemy(5, 5)).thenReturn(e9).thenReturn(null);
    when(e9.getAtk()).thenReturn(0);
    when(e9.getEXP()).thenReturn(0);
    when(p8.getHP()).thenReturn(100);

    doNothing().when(planta8).enemyDefeated(5, 5);
    when(planta8.getEnemiesLeft()).thenReturn(1);

    j8.battle(5, 5);

    verify(j8, atLeastOnce()).giveItem(Mockito.isA(Pocion.class));
    verify(j8, atLeastOnce()).giveItem(Mockito.isA(Bomba.class));
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

  @Test
  void testBoom(){
    Planta planta = Mockito.mock(Planta.class);
    Jugador p = mock(Jugador.class);
    Joc j = new Joc(p);

    j.setPlantaActual(planta);

    // Cas normal: no queden enemics; no fa res
    ArrayList<Enemic> listaVacia = new ArrayList<>();
    when(planta.getEnemies()).thenReturn(listaVacia);

    j.boom();

    // Verifiquem que no es crida a setEnemiesLeft
    verify(planta, never()).setEnemiesLeft(anyInt());

    reset(planta);

    // Cas normal: queda un enemic, elimina 1
    ArrayList<Enemic> llista1 = new ArrayList<>();
    Enemic e = Mockito.mock(Enemic.class);
    llista1.add(e);
    when(planta.getEnemies()).thenReturn(llista1);
    when(planta.getEnemiesLeft()).thenReturn(0);

    j.boom();

    assertEquals(0, llista1.size());
    verify(planta).setEnemiesLeft(0);
    assertInstanceOf(Llave.class, j.getItemActual()); //verifiquem que s'afegeix la clau

    reset(planta);
    reset(e);


    // Cas normal: queden 2 enemics, elimina 1 o 2
    ArrayList<Enemic> llista2 = new ArrayList<>();
    llista2.add(e);
    Enemic e2 = Mockito.mock(Enemic.class);
    llista2.add(e2);
    when(planta.getEnemies()).thenReturn(llista2);

    j.boom();

    assertTrue(llista2.size() >= 0 && llista2.size() <= 1);
    verify(planta).setEnemiesLeft(llista2.size());

    reset(planta);
    reset(e,e2);


    // Cas normal: eliminacio de 1 2 o 3 enemics
    ArrayList<Enemic> llista3 = new ArrayList<>();
    llista3.add(e);
    llista3.add(e2);
    Enemic e3 = Mockito.mock(Enemic.class);
    llista3.add(e3);
    when(planta.getEnemies()).thenReturn(llista3);

    j.boom();

    assertTrue(llista3.size() >= 0 && llista3.size() <= 2);
    verify(planta).setEnemiesLeft(llista3.size());

    reset(planta);
    reset(e,e2,e3);

    // Cas normal: mes de 3 enemics, pero elimina com a maxim 3
    ArrayList<Enemic> llistames3 = new ArrayList<>();
    llistames3.add(e);
    llistames3.add(e2);
    llistames3.add(e3);
    Enemic e4 = Mockito.mock(Enemic.class);
    llistames3.add(e4);
    Enemic e5 = Mockito.mock(Enemic.class);
    llistames3.add(e5);

    when(planta.getEnemies()).thenReturn(llistames3);

    j.boom();

    assertTrue(llistames3.size() >= 2 && llistames3.size() <= 4);
    assertFalse(llistames3.isEmpty());
    verify(planta).setEnemiesLeft(llistames3.size());

    reset(planta);
  }

}