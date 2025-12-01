package es.uab.TQS.mazmorra.controlador;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import es.uab.TQS.mazmorra.model.Item;
import es.uab.TQS.mazmorra.model.Joc;
import es.uab.TQS.mazmorra.vista.Interface;

import java.util.Objects;

public class LoopJoc {

    private final Joc joc;
    private final Interface hud;

    public LoopJoc(Joc joc, Interface hud) {
        this.joc = joc;
        this.hud = hud;
    }

    public void startGame() {
        joc.startGame();
        boolean jugando = true;

        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setForceAWTOverSwing(false)
                    .setTerminalEmulatorTitle("Mazmorra")
                    .setPreferTerminalEmulator(true);

            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            TextGraphics tg = screen.newTextGraphics();
            int idx = 0;

            while (jugando) {
                screen.clear();

                Joc.GameState currentState = joc.getCurrentState();

                switch (currentState) {
                    case EXPLORING -> hud.dibuixarMazmorra(screen, tg, joc.getPlayer(), joc.getPlantaActual());
                    case INVENTORY -> {
                        hud.dibuixarMazmorra(screen, tg, joc.getPlayer(), joc.getPlantaActual());
                        hud.dibuixarInventari(screen, tg, joc.getPlayer(), idx);
                    }
                    case GAME_OVER -> hud.gameOver(screen);
                    case INFO -> {
                        hud.dibuixarMazmorra(screen, tg, joc.getPlayer(), joc.getPlantaActual());
                        if (joc.getItemActual() == null) {
                            hud.ajudaPorta(screen, tg);
                        } else {
                            hud.mostrarItemTrobat(screen, tg, joc.getItemActual().getNom());
                        }
                    }
                }

                screen.refresh();

                joc.checkInfoMessageDuration();

                KeyStroke key = screen.pollInput();
                if (key != null) {
                    switch (key.getKeyType()) {
                        case ArrowUp -> {
                            if (currentState == Joc.GameState.EXPLORING)
                                joc.getPlayer().moveUp(joc.getPlantaActual(), joc);
                            else if (currentState == Joc.GameState.INVENTORY
                                    && !joc.getPlayer().getInventari().isEmpty()) {
                                idx = Math.max(0, idx - 1);
                            }
                        }
                        case ArrowDown -> {
                            if (currentState == Joc.GameState.EXPLORING)
                                joc.getPlayer().moveDown(joc.getPlantaActual(), joc);
                            else if (currentState == Joc.GameState.INVENTORY
                                    && !joc.getPlayer().getInventari().isEmpty()) {
                                idx = Math.min(joc.getPlayer().getInventari().size() - 1, idx + 1);
                            }
                        }
                        case ArrowLeft -> {
                            if (currentState == Joc.GameState.EXPLORING)
                                joc.getPlayer().moveLeft(joc.getPlantaActual(), joc);
                        }
                        case ArrowRight -> {
                            if (currentState == Joc.GameState.EXPLORING)
                                joc.getPlayer().moveRight(joc.getPlantaActual(), joc);
                        }
                        case Enter -> {
                            if (currentState == Joc.GameState.INVENTORY
                                    && !joc.getPlayer().getInventari().isEmpty()) {
                                Item item = joc.getPlayer().getInventari().get(idx);
                                if (Objects.equals(item.getNom(), "Clau")) {
                                    if (joc.getPlantaActual().isDoorPosition(joc.getPlayer().getPos_x(),
                                            joc.getPlayer().getPos_y())) {
                                        item.usarItem();
                                        joc.getPlayer().getInventari().remove(idx);
                                        joc.passarPlanta();
                                    } else {
                                        joc.missatgeTemporal(2000);
                                    }
                                } else {
                                    item.usarItem();
                                    joc.getPlayer().getInventari().remove(idx);
                                }
                                if (idx >= joc.getPlayer().getInventari().size())
                                    idx = 0;
                            }

                            if (currentState == Joc.GameState.GAME_OVER) {
                                this.joc.startGame();
                            }
                        }
                        case Escape, EOF -> jugando = false;

                        default -> {
                            if (key.getCharacter() != null) {
                                char c = Character.toUpperCase(key.getCharacter());
                                if (c == 'I') {
                                    idx = 0;
                                    joc.toggleInventory();
                                }
                            }
                        }
                    }
                }
                Thread.sleep(5);
            }
            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
