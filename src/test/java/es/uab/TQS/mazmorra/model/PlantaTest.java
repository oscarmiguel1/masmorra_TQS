//PlantaTest
package es.uab.TQS.mazmorra.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlantaTest {
  @Test
  void openDoor(){
    String[] planta = {
  "+-------------------------+------------+",
  "|                         |            |",
  "|  W                                   |",
  "|   SSS                   |           W|",
  "|                         |            |",
  "|       TT                |            |",
  "|                         |            |",
  "|                         |            |",
  "|----------------- --------------------|",
  "|                         |            |",
  "|   B                         t        |",
  "|                         |            |",
  "+-------------------------+------------+"
  };

    Planta p = new Planta(3,planta);

    p.openDoor();
    assertTrue(p.getDoorState());
  }

  void enemiesLeft(){
    String[] planta = {
  "+-------------------------+------------+",
  "|                         |            |",
  "|  W                                   |",
  "|   SSS                   |           W|",
  "|                         |            |",
  "|       TT                |            |",
  "|                         |            |",
  "|                         |            |",
  "|----------------- --------------------|",
  "|                         |            |",
  "|   B                         t        |",
  "|                         |            |",
  "+-------------------------+------------+"
  };

    Planta p = new Planta(3,planta);

    p.enemyDefeated();

    assertTrue(p.getEnemiesLeft() == 2);

    p.enemyDefeated();
    p.enemyDefeated();
    p.enemyDefeated();
    assertTrue(p.getEnemiesLeft() == 0);

  }
}




