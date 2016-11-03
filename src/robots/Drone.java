/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;
import carte.*;
import exceptions.ErreurPosition;

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
    
    public Drone(Case pos) throws ErreurPosition {
        super(pos); 
        this.capaciteMax = 10000;
        this.tempsRemplissageComp = 30;
        this.interventionUnitaire = (int) (10000/30) + 1;
        this.volumeRestant = this.capaciteMax;
    }

    public Drone(double vitesse, Case pos) throws ErreurPosition {
        super(vitesse, pos);
        this.capaciteMax = 10000;
        this.tempsRemplissageComp = 30;
        this.interventionUnitaire = (int) (10000/30) + 1;
        this.volumeRestant = this.capaciteMax;
    }
    
    public void remplirReservoir(int qte){
    	if(peutRemplirReservoir()) {
        	if(this.volumeRestant + qte <= this.capaciteMax) {
        		this.volumeRestant +=  qte;
        	} else {
        		this.volumeRestant = this.capaciteMax;
        	}
        } 
    }
    @Override
    public boolean peutRemplirReservoir(){
	    if (this.position.getNatureTerrain() == NatureTerrain.EAU && this.volumeRestant < this.capaciteMax) {
	    		return true;
	    } else {
	    	System.out.println("Le robot ne peut pas remplir son réservoir.");
	    	return false;
	    }
    }
    
    public String getFileOfRobot(){
    	return "images/robots/drone/";
    }
}

