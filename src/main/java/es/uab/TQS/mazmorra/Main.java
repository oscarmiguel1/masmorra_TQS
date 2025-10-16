package es.uab.TQS.mazmorra;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {
    public static void main(String[] args) {
        try {

            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setForceAWTOverSwing(false)  // asegura compatibilidad
                    .setTerminalEmulatorTitle("Mazmorra")
                    .setPreferTerminalEmulator(true); // fuerza modo ventana


            Terminal terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null); // oculta el cursor

            TextGraphics tg = screen.newTextGraphics();
            int jugadorX = 10, jugadorY = 5;
            boolean seguir = true;

            while (seguir) {
                screen.clear();

                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.putString(jugadorX, jugadorY, "@");

                tg.setForegroundColor(TextColor.ANSI.RED);
                for (int x = 0; x < 50; x++) tg.putString(x, 10, "_");

                screen.refresh();

                KeyStroke key = screen.pollInput();
                if (key != null) {
                    switch (key.getKeyType()) {
                        case ArrowUp -> jugadorY--;
                        case ArrowDown -> jugadorY++;
                        case ArrowLeft -> jugadorX--;
                        case ArrowRight -> jugadorX++;
                        case Escape, EOF -> seguir = false;
                    }
                }

                Thread.sleep(50);
            }

            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}