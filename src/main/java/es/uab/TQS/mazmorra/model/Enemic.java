/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package es.uab.TQS.mazmorra.model;

/**
 *
 * @author oscar
 */
public class Enemic {

    private int atk;

    private String enemyIcon = "&";

    private int pos_x;
    private int pos_y;

    private int given_exp;

    public int getPos_x() {return this.pos_x;}

    public int getPos_y() {return this.pos_y;}


    public Enemic(int a, int exp){
        this.pos_x = 0;
        this.pos_y = 0;
        this.atk = a;
        this.given_exp = exp;
    }

    public int getAtk(){return this.atk;}

    public int getEXP() {return this.given_exp;}

    public void die(){

    }

}
