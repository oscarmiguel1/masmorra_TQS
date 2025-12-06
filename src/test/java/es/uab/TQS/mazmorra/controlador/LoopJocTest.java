package es.uab.TQS.mazmorra.controlador;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import es.uab.TQS.mazmorra.model.Item;
import es.uab.TQS.mazmorra.model.Joc;
import es.uab.TQS.mazmorra.model.Planta;
import es.uab.TQS.mazmorra.model.Jugador;
import es.uab.TQS.mazmorra.vista.Interface;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class LoopJocTest {
    @Test
    void testStartGame() {
        Joc joc = mock(Joc.class);
        Interface hud = mock(Interface.class);
        Jugador player = mock(Jugador.class);
        Planta planta = mock(Planta.class);
        Item clau = mock(Item.class);
        Item pocion = mock(Item.class);

        List<Item> inventario = new ArrayList<>();
        inventario.add(clau);
        inventario.add(pocion);

        when(joc.getPlayer()).thenReturn(player);
        when(joc.getPlantaActual()).thenReturn(planta);
        when(player.getInventari()).thenReturn((ArrayList<Item>) inventario);
        when(clau.getNom()).thenReturn("Clau");
        when(pocion.getNom()).thenReturn("Pocion");
        when(player.getPos_x()).thenReturn(5);
        when(player.getPos_y()).thenReturn(5);

        LoopJoc loopJoc = new LoopJoc(joc, hud);

        // indexs de l'inventari
        int[] idx = {0};

        //Estat EXPLORING ------------------------------------------
        when(joc.getCurrentState()).thenReturn(Joc.GameState.EXPLORING);

        //Cas normal: moviment
        // Simulem moviment amunt
        KeyStroke keyUp = crearPulsacions(KeyType.ArrowUp);
        processarTecles(keyUp, joc, idx);
        verify(player, times(1)).moveUp(planta, joc);

        // Simulem moviment avall
        KeyStroke keyDown = crearPulsacions(KeyType.ArrowDown);
        processarTecles(keyDown, joc, idx);
        verify(player, times(1)).moveDown(planta, joc);

        // Simulem moviment esquerra
        KeyStroke keyLeft = crearPulsacions(KeyType.ArrowLeft);
        processarTecles(keyLeft, joc, idx);
        verify(player, times(1)).moveLeft(planta, joc);

        // Simulem moviment dreta
        KeyStroke keyRight = crearPulsacions(KeyType.ArrowRight);
        processarTecles(keyRight, joc, idx);
        verify(player, times(1)).moveRight(planta, joc);

        // Estat INVENTORY
        KeyStroke keyI = crearCarac('i');
        processarTecles(keyI, joc, idx);
        verify(joc, times(1)).toggleInventory();
        assertEquals(0, idx[0]); // El índice se resetea a 0

        //Cas normal: Navegació per l'inventari
        when(joc.getCurrentState()).thenReturn(Joc.GameState.INVENTORY);

        // Navegar cap avall
        processarTecles(keyDown, joc, idx);
        assertEquals(1, idx[0]);

        // Navegar cap avall sense passar limit
        processarTecles(keyDown, joc, idx);
        assertEquals(1, idx[0]); // Se queda en 1 (último elemento)

        // Navegar cap amunt
        processarTecles(keyUp, joc, idx);
        assertEquals(0, idx[0]);

        // Navegar cap amunt sense que quedi negatiu
        processarTecles(keyUp, joc, idx);
        assertEquals(0, idx[0]);

        // Cas normal: utilitzar clau sense serhi a la porta
        when(planta.isDoorPosition(anyInt(), anyInt())).thenReturn(false);

        KeyStroke keyEnter = crearPulsacions(KeyType.Enter);
        processarTecles(keyEnter, joc, idx);

        verify(joc, times(1)).missatgeTemporal(2000);
        verify(joc, never()).passarPlanta(); // NO debe pasar de planta
        assertEquals(2, inventario.size()); // La clau NO se elimina

        // Cass normal: utilitzar clau a la porta
        when(planta.isDoorPosition(anyInt(), anyInt())).thenReturn(true);

        processarTecles(keyEnter, joc, idx);

        verify(clau, times(1)).usarItem();
        verify(joc, times(1)).passarPlanta();
        assertEquals(1, inventario.size()); // La clau se elimina
        assertEquals(0, idx[0]); // El índice se ajusta

        //Cas normal: utilitzar un item
        processarTecles(keyEnter, joc, idx);

        verify(pocion, times(1)).usarItem();
        assertEquals(0, inventario.size());

        //Estat GAME_OVER --------------------------------------
        when(joc.getCurrentState()).thenReturn(Joc.GameState.gameOver);
        processarTecles(keyEnter, joc, idx);
        verify(joc, times(1)).startGame();
    }

    //Funcions auxiliars ---------------------------------------
    private void processarTecles(KeyStroke key, Joc joc, int[] idx) {
        if (key == null) return;

        Joc.GameState currentState = joc.getCurrentState();
        Jugador player = joc.getPlayer();
        Planta planta = joc.getPlantaActual();

        switch (key.getKeyType()) {
            case ArrowUp -> {
                if (currentState == Joc.GameState.EXPLORING) {
                    player.moveUp(planta, joc);
                } else if (currentState == Joc.GameState.INVENTORY
                        && !player.getInventari().isEmpty()) {
                    idx[0] = Math.max(0, idx[0] - 1);
                }
            }
            case ArrowDown -> {
                if (currentState == Joc.GameState.EXPLORING) {
                    player.moveDown(planta, joc);
                } else if (currentState == Joc.GameState.INVENTORY
                        && !player.getInventari().isEmpty()) {
                    idx[0] = Math.min(player.getInventari().size() - 1, idx[0] + 1);
                }
            }
            case ArrowLeft -> {
                if (currentState == Joc.GameState.EXPLORING) {
                    player.moveLeft(planta, joc);
                }
            }
            case ArrowRight -> {
                if (currentState == Joc.GameState.EXPLORING) {
                    player.moveRight(planta, joc);
                }
            }
            case Enter -> {
                if (currentState == Joc.GameState.INVENTORY
                        && !player.getInventari().isEmpty()) {
                    Item item = player.getInventari().get(idx[0]);
                    if (item.getNom().equals("Clau")) {
                        if (planta.isDoorPosition(player.getPos_x(),
                                player.getPos_y())) {
                            item.usarItem();
                            player.getInventari().remove(idx[0]);
                            joc.passarPlanta();
                        } else {
                            joc.missatgeTemporal(2000);
                        }
                    } else {
                        item.usarItem();
                        player.getInventari().remove(idx[0]);
                    }
                    if (idx[0] >= player.getInventari().size()) {
                        idx[0] = 0;
                    }
                }

                if (currentState == Joc.GameState.gameOver) {
                    joc.startGame();
                }
            }
            default -> {
                if (key.getCharacter() != null) {
                    char c = Character.toUpperCase(key.getCharacter());
                    if (c == 'I') {
                        idx[0] = 0;
                        joc.toggleInventory();
                    }
                }
            }
        }
    }
    // funcions per crear pulsacions de tecles
    private KeyStroke crearPulsacions(KeyType keyType) {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(keyType);
        when(keyStroke.getCharacter()).thenReturn(null);
        return keyStroke;
    }
    private KeyStroke crearCarac(char c) {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke.getCharacter()).thenReturn(c);
        return keyStroke;
    }
}