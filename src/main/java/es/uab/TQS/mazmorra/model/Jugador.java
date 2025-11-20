//Jugador
package es.uab.TQS.mazmorra.model;


import java.util.ArrayList;

public class Jugador {

    private int pos_x;
    private int pos_y;

    private ArrayList<Item> inventari;

    private int hp_actual;
    private int max_hp;

    private int exp;
    private int exp_necesaria;
    private int lv;


    public Jugador(){
        this.pos_x = 0;
        this.pos_y = 0;
        this.max_hp = 20;
        this.hp_actual = this.max_hp;
        this.exp = 0;
        this.exp_necesaria = 100;
        this.lv = 1;
    }

    public int getLvl(){
        return this.lv;
    }

    public int getPos_x() {return this.pos_x;}

    public int getPos_y() {return this.pos_y;}

    public Item getInventari(int n) {return this.inventari.get(n);}
    public void addItem(Item i) {this.inventari.add(i);}
    public void moveRight(Planta p) {
        int newX = pos_x + 1; // movimiento provisional
        int newY = pos_y;

        if (p.isValidPosition(newX, newY)) {
            pos_x = newX; // solo movemos si es válido
        }
    }

    public void moveLeft(Planta p) {
        int newX = pos_x - 1;
        int newY = pos_y;

        if (p.isValidPosition(newX, newY)) {
            pos_x = newX;
        }
    }

    public void moveUp(Planta p) {
        int newX = pos_x;
        int newY = pos_y - 1;

        if (p.isValidPosition(newX, newY)) {
            pos_y = newY;
        }
    }

    public void moveDown(Planta p) {
        int newX = pos_x;
        int newY = pos_y + 1;

        if (p.isValidPosition(newX, newY)) {
            pos_y = newY;
        }
    }

    public int getHP(){
        return this.hp_actual;
    }

    public void setInitialPos(int x,int y) {
        this.pos_x = x;
        this.pos_y = y;
    }

    public int getEXP(){
        return this.exp;
    }

    public void setHP(int h){
        if(h >= this.max_hp){
            this.hp_actual = this.max_hp;
        }else{
            if(h < 0){
                this.hp_actual = 0;
            }else{
                this.hp_actual = h;
            }
        }
    
    }

    public void openDoor(Planta p){
        if(p.getPlayerTile(this.pos_x,this.pos_y) == 'M'){
            p.openDoor();
        }
    }

    public void setEXP(int e){
        this.exp = e;
        if(e >= this.exp_necesaria){
            this.exp = this.exp_necesaria;
        }else{
            if(e < 0){
                this.exp = 0;
            }else{
                this.exp = e;
            }
        }
    }

    public void lvUP(){
        
    }

}
