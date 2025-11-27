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

    private int pos_x;
    private int pos_y;

    private int given_exp;

    public int getPos_x() {
        return this.pos_x;
    }

    public int getPos_y() {
        return this.pos_y;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getEXP() {
        return this.given_exp;
    }

    public Enemic(int a, int exp, int x, int y) {
        this.pos_x = x;
        this.pos_y = y;
        this.atk = a;
        this.given_exp = exp;
    }

}
