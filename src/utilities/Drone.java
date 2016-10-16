/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;


public class Drone extends Robot {

    
    @Override
    protected void setVitesseDefaut(double v){
        if (v >= 0 && v <= 150) {
            this.vitesseDefaut = v;
        } else {
            this.vitesseDefaut = 100;
        }    
    }
    
    /*Pas besoin de modifier setPosition car le drone peut voler partout */
    
    /* Constructeurs */
    
    public Drone(Case pos) {
        super(pos); 
        this.capaciteMax = 10000;
    }

    public Drone(double vitesse, Case pos) {
        super(vitesse, pos);
        this.capaciteMax = 10000;
    }
    
}
