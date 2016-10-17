/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;
import carte.*;

/**
 *
 * @author skip
 */

    /* ce qui est a traiter :  Temps remplir et vider */


  /***********************/
 /* CLASSE MERE : ROBOT */
/***********************/

abstract class Robot {
    
    protected double vitesse;
    
    protected double vitesseDefaut=0;
    
    protected Case position; 
    
    protected int capaciteMax = 0;
    
    protected int volumeRestant = 0;
    

    
    abstract protected void setVitesseDefaut(double v);
    /* de base, dès qu'on modifie la position on modifie la vitesse en conséquence*/
        
    abstract public void setPosition(Case p);
     
    /*constructeur qui ne modifie pas la vitesse par défaut*/
    public Robot(Case pos) {
        this.setVitesseDefaut(-1);
        this.setPosition(pos);
    }
    
    /*constructeur avec une nouvelle vitesse définie*/
    public Robot(double vitesse, Case pos) {
        this.setVitesseDefaut(vitesse);
        this.setPosition(pos);
    }
    
    
    
    public Case getPosition(){
        return position;
    }

    
    public double getVitesse(NatureTerrain nature){
        return vitesse;
    }
    
    public int getVolumeRestant() {
        return this.volumeRestant;
    }
    
    
    abstract public void remplirReservoir();
    
    public void deverserEau(int vol){
        if (vol>=this.volumeRestant) {
            this.volumeRestant -= vol;
        } else {
            throw new IllegalArgumentException("Volume supérieur au volume restant");
        }
        
        /* à ajouter quand besoin de répercuter sur le feu*/
    }

    @Override
    public String toString() {
        return new String("le robot a une vitesse de " + this.vitesse + "km/h et une capacité de " + this.capaciteMax + "L et une vitesse par défaut de "+this.vitesseDefaut + "km/h");
    }
}


