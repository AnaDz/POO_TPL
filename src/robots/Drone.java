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


  /************************/
 /* CLASSE FILLE : DRONE */
/************************/

public class Drone extends Robot {

    
    @Override
    protected void setVitesseDefaut(double v){
        if (v >= 0 && v <= 150) {
            this.vitesseDefaut = v;
        } else {
            this.vitesseDefaut = 100;
        }    
    }
        
    
    @Override
    public double getVitesse(NatureTerrain nature){
        return this.vitesseDefaut;
    }
    /* Constructeurs */
    
    public Drone(Case pos) {
        super(pos); 
        this.capaciteMax = 10000;
    }

    public Drone(double vitesse, Case pos) {
        super(vitesse, pos);
        this.capaciteMax = 10000;
    }
    
    @Override
    public void remplirReservoir(){
        if (this.position.getNatureTerrain() == NatureTerrain.EAU) {
            this.volumeRestant=this.capaciteMax;
        } else {
            /* LEVER UNE EXCEPTION */
            throw new IllegalArgumentException("le drone essaye de se remplir en dehors de l'eau");
        }            
    }
    
}

