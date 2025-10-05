package es.uab.TQS.mazmorra.model;


public class Jugador {

    private int hp_actual;
    private int max_hp;

    private int exp;
    private int exp_necesaria;
    private int lv;

    //private ArrayList inventari; esto sera un array de Items

    public Jugador(){
        this.max_hp = 20;
        this.hp_actual = this.max_hp;
        this.exp = 0;
        this.exp_necesaria = 100;
        this.lv = 1;
    }

    public int getLvl(){
        return this.lv;
    }
    
    public int getHP(){
        return this.hp_actual;
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
