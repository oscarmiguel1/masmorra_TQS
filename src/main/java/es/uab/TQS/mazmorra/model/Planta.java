//Planta
package es.uab.TQS.mazmorra.model;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;

public class Planta {


    private final int door_posX;
    private final int door_posY;

    private boolean doorOpen;


    private final String[] floorLayout;
    private final ArrayList<Enemic> enemics;
    private int enemiesLeft;

    public Planta(int e, String[] f, ArrayList<Enemic> en) {
        this.door_posX = 20;
        this.door_posY = 2;
        this.doorOpen = false;
        this.enemiesLeft = e;
        this.floorLayout = f;
        this.enemics = en;
    }


    public Enemic getEnemy(int x, int y) {
        for (Enemic e : enemics) {
            if (e.getPos_x() == x && e.getPos_y() == y) {
                return e;
            }
        }
        return null; // No hay enemigo en esa casilla
    }

    public String[] getFloorLayout() {
        return this.floorLayout;
    }

    public char getPlayerTile(int x, int y) {
        return this.floorLayout[y].charAt(x);
    }

    public ArrayList<Enemic> getEnemies() {
        return this.enemics;
    }

    public int getDoorposX() {
        return this.door_posX;
    }

    public int getDoorposY() {
        return this.door_posY;
    }

    public int getEnemiesLeft() {
        return this.enemiesLeft;
    }

    public boolean getDoorState() {
        return this.doorOpen;
    }


    public boolean isValidPosition(int x, int y) {
        int lim_x = 79;
        int lim_y = 19;
        return x >= 0 && x <= lim_x &&
                y >= 0 && y <= lim_y &&
                this.floorLayout[y].charAt(x) != '#';
    }


    public boolean isEnemyPosition(int x, int y) {
        for (Enemic e : enemics) {
            if (e.getPos_x() == x && e.getPos_y() == y) {
                return true;
            }
        }
        return false;
    }

    public boolean isDoorPosition(int x, int y){
        if(door_posX == x && door_posY == y){
            return true;
        }
        return false;
    }

    public void openDoor() {
        this.doorOpen = true;
    }


    public void enemyDefeated(int x, int y) {
        if (this.enemiesLeft - 1 >= 0) {
            this.enemiesLeft--;
            for (int i = 0; i < this.enemics.size(); i++) {
                if (this.enemics.get(i).getPos_x() == x && this.enemics.get(i).getPos_y() == y) {
                    this.enemics.remove(i);
                }
            }
            //this.enemics.removeFirst();
        } else {
            this.enemiesLeft = 0;
            this.enemics.clear();
        }
    }


}
