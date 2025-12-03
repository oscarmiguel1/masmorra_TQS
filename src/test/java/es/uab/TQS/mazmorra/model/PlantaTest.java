//PlantaTest
package es.uab.TQS.mazmorra.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlantaTest {

    @Test
    void testGetEnemy() {

        String[] LAYOUT = {
                "################################################################################",
                "#             #                        #              #                        #",
                "#    #####    #    ################    #    ######    #    ################    #",
                "#    #   #    #    #              #    #    #    #    #    #              #    #",
                "#    #   #         #              #         #    #         #              #    #",
                "#    #   ###########              ###########    ###########              #    #",
                "#    #                                                                    #    #",
                "#    #              #######                #######                #####   #    #",
                "######              #     #                #     #                #   #   ######",
                "#                   #     #                #     #                #   #        #",
                "#    ################     ##################     ##################   #####    #",
                "#    #                                                                         #",
                "#    #              #######                #######                #########    #",
                "######              #     #                #     #                #       #    #",
                "#        ############     ##################     ##################            #",
                "#        #                #                      #                             #",
                "#        #                #                      #                        #    #",
                "#   ######   ##############   ####################   ######################    #",
                "#   #        #                #                      #                    #    #",
                "#            #                #                      #                    #    #",
                "################################################################################"
        };

        Enemic enemic1 = Mockito.mock(Enemic.class);
        Enemic enemic2 = Mockito.mock(Enemic.class);

        Mockito.when(enemic1.getPos_x()).thenReturn(5);
        Mockito.when(enemic1.getPos_y()).thenReturn(7);

        Mockito.when(enemic2.getPos_x()).thenReturn(10);
        Mockito.when(enemic2.getPos_y()).thenReturn(3);

        List<Enemic> enemicsMock = Arrays.asList(enemic1, enemic2);

        Planta planta = new Planta(2, null, new ArrayList<>(enemicsMock), 0, 0);

        //Enemic trobat
        Enemic result = planta.getEnemy(5, 7);


        assertEquals(enemic1, result);

        //Enemic no trobat
        result = planta.getEnemy(6,6);
        assertNotEquals(enemic1,result);
    }

    @Test
    void testisValidPosition(){
        String[] LAYOUT = {
                "################################################################################",
                "#             #                        #              #                        #",
                "#    #####    #    ################    #    ######    #    ################    #",
                "#    #   #    #    #              #    #    #    #    #    #              #    #",
                "#    #   #         #              #         #    #         #              #    #",
                "#    #   ###########              ###########    ###########              #    #",
                "#    #                                                                    #    #",
                "#    #              #######                #######                #####   #    #",
                "######              #     #                #     #                #   #   ######",
                "#                   #     #                #     #                #   #        #",
                "#    ################     ##################     ##################   #####    #",
                "#    #                                                                         #",
                "#    #              #######                #######                #########    #",
                "######              #     #                #     #                #       #    #",
                "#        ############     ##################     ##################            #",
                "#        #                #                      #                             #",
                "#        #                #                      #                        #    #",
                "#   ######   ##############   ####################   ######################    #",
                "#   #        #                #                      #                    #    #",
                "#            #                #                      #                    #    #",
                "################################################################################"
        };
        //Comprovacions layout: files
        assertEquals(21,LAYOUT.length);

        //Comprovacions layout: columnes
        for (int i = 0; i < LAYOUT.length; i++) {
            assertEquals(80, LAYOUT[i].length());
        }

        Planta planta = new Planta(2,LAYOUT,null,20,20);

        //Cas normal: no hi ha mur (#)
        assertTrue(planta.isValidPosition(1,1));

        //Cas normal: mur no deixa pasar (#)
        assertFalse(planta.isValidPosition(5,2));

        //Cas inusual: valors fora de rang
        assertFalse(planta.isValidPosition(-50,-50));
        assertFalse(planta.isValidPosition(200,200));

        //Segons l'elaboració de cada planta, cap cantonada hauria de ser accessible
        //Limits: Cantonada (0,0)
        assertFalse(planta.isValidPosition(0,0));

        //Limits: Cantonada (79,19)
        assertFalse(planta.isValidPosition(79,19));

        //Limits: Cantonada (79,0)
        assertFalse(planta.isValidPosition(79,0));

        //Limits: Cantonada (0,19)
        assertFalse(planta.isValidPosition(0,19));

        //Limits exteriors: (-1,0)
        assertFalse(planta.isValidPosition(-1,0));

        //Limits exteriors: (81,0)
        assertFalse(planta.isValidPosition(81,0));

        //Limits exteriors: (0,-1)
        assertFalse(planta.isValidPosition(0,-1));

        //Limits exteriors: (0,22)
        assertFalse(planta.isValidPosition(0,22));

    }

    @Test
    void testisEnemyPosition() {

        Enemic enemic1 = Mockito.mock(Enemic.class);
        Enemic enemic2 = Mockito.mock(Enemic.class);
        Enemic enemic3 = Mockito.mock(Enemic.class);

        Mockito.when(enemic1.getPos_x()).thenReturn(5);
        Mockito.when(enemic1.getPos_y()).thenReturn(7);

        Mockito.when(enemic2.getPos_x()).thenReturn(-5);
        Mockito.when(enemic2.getPos_y()).thenReturn(-5);

        Mockito.when(enemic3.getPos_x()).thenReturn(500);
        Mockito.when(enemic3.getPos_y()).thenReturn(500);

        ArrayList<Enemic> enemicsMock = new ArrayList<>();
        Planta p = new Planta(2,null,enemicsMock,20,20);

        //Hauria de donar false amb llista buida
        assertFalse(p.isEnemyPosition(0, 0));
        assertFalse(p.isEnemyPosition(5, 7));

        //Afegim enemic i comprovem
        p.getEnemies().add(enemic1);
        assertTrue(p.isEnemyPosition(5,7));

        //Posicio sense enemic
        assertFalse(p.isEnemyPosition(3,3));

        //Posicions negatives o molt grans: encara que no tingui sentit, ho hauria de trobar
        p.getEnemies().add(enemic2);
        assertTrue(p.isEnemyPosition(-5,-5));
        assertFalse(p.isEnemyPosition(-6,-5));

        p.getEnemies().add(enemic3);
        assertTrue(p.isEnemyPosition(500,500));

    }

    @Test
    void testisDoorPosition(){

        Planta p = new Planta(2,null,null,20,20);

        //Cas normal: true
        assertTrue(p.isDoorPosition(20,20));

        //Cas normal: false
        assertFalse(p.isDoorPosition(18,15));
        assertFalse(p.isDoorPosition(19,19));
        assertFalse(p.isDoorPosition(18,19));
        assertFalse(p.isDoorPosition(0,0));
        assertFalse(p.isDoorPosition(-20,0));

        Planta p2 = new Planta(2,null,null,-50,-50);
        assertTrue(p2.isDoorPosition(-50,-50));
        assertFalse(p2.isDoorPosition(-20,-20));
        assertFalse(p2.isDoorPosition(5,15));


    }

    @Test
    void testenemyDefeated(){
        Enemic enemic1 = Mockito.mock(Enemic.class);
        Enemic enemic2 = Mockito.mock(Enemic.class);
        Enemic enemic3 = Mockito.mock(Enemic.class);

        Mockito.when(enemic1.getPos_x()).thenReturn(2);
        Mockito.when(enemic1.getPos_y()).thenReturn(1);

        Mockito.when(enemic2.getPos_x()).thenReturn(5);
        Mockito.when(enemic2.getPos_y()).thenReturn(5);

        Mockito.when(enemic3.getPos_x()).thenReturn(3);
        Mockito.when(enemic3.getPos_y()).thenReturn(7);

        ArrayList<Enemic> enemicsMock = new ArrayList<>();
        enemicsMock.add(enemic1);
        enemicsMock.add(enemic2);
        enemicsMock.add(enemic3);
        Planta p = new Planta(3,null,enemicsMock,20,20);

        //Cas normal: eliminar enemic.
        // Donem les coordenades per conegudes ja que aquesta
        //funcio es crida la funcio battle
        p.enemyDefeated(2,1);
        boolean trobat = false;
        for (Enemic e : p.getEnemies()) {
            if (e.getPos_x() == 2 && e.getPos_y() == 1) {
                trobat = true;
                break;
            }
        }
        assertFalse(trobat); //L'enemic ja no es troba a l'array d'enemics
        assertEquals(2,p.getEnemiesLeft());
        assertEquals(2,p.getEnemies().size());
        assertEquals(p.getEnemies().size(),p.getEnemiesLeft());

        //Cas normal: eliminar enemic a posicio intermitja a l'array
        p.getEnemies().add(enemic1);
        p.setEnemiesLeft(3);

        p.enemyDefeated(5,5); //enemic2

        trobat = false;
        for (Enemic e : p.getEnemies()) {
            if (e.getPos_x() == 5 && e.getPos_y() == 5) {
                trobat = true;
                break;
            }
        }
        assertFalse(trobat); //L'enemic ja no es troba a l'array d'enemics
        assertEquals(2,p.getEnemiesLeft());
        assertEquals(2,p.getEnemies().size());
        assertEquals(p.getEnemies().size(),p.getEnemiesLeft());

        //Cas normal: eliminar ultim enemic
        p.enemyDefeated(2,1); //ara ja queda un enemic

        p.enemyDefeated(3,7);

        trobat = false;
        for (Enemic e : p.getEnemies()) {
            if (e.getPos_x() == 3 && e.getPos_y() == 7) {
                trobat = true;
                break;
            }
        }
        assertFalse(trobat); //L'enemic ja no es troba a l'array d'enemics
        assertEquals(0,p.getEnemiesLeft());
        assertEquals(0,p.getEnemies().size());
        assertEquals(p.getEnemies().size(),p.getEnemiesLeft());


        //Cas: elminiacio seguida

        Planta p2 =new Planta(3,null,enemicsMock,20,20);

        p.enemyDefeated(2,1);
        p.enemyDefeated(5,5);
        p.enemyDefeated(3,7);

        assertEquals(p.getEnemiesLeft(),p.getEnemies().size());
    }


}




