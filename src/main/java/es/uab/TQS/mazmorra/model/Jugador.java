package es.uab.TQS.mazmorra.model;


public class Jugador {

    private int pos_x;
    private int pos_y;

    private int lim_x;
    private int lim_y;
    private int hp_actual;
    private int max_hp;

    private int exp;
    private int exp_necesaria;
    private int lv;

    //private ArrayList inventari; esto sera un array de Items

    public Jugador(){
        this.pos_x = 0;
        this.pos_y = 0;
        this.lim_x = 30;
        this.lim_y = 30;
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

    public void moveUp() {
        if(this.pos_y-1 < this.lim_y){
            this.pos_y--;
        }
    }
    public void moveDown() {
        if(this.pos_y+1 > this.lim_y){
            this.pos_y++;
        }
    }
    public void moveRight(){
        if(this.pos_x+1 < this.lim_x){
            this.pos_x++;
        }
    }
    public void moveleft(){
          if(this.pos_x+1 > this.lim_x){
            this.pos_x--;
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
