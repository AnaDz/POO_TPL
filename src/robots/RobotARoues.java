/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;

import carte.*;
import exceptions.ErreurPosition;


  /********************************/
 /* CLASSE FILLE : ROBOT A ROUES */
/********************************/

public class RobotARoues extends Robot {

    @Override 
    protected void setVitesseDefaut(double v) {
        if (v >= 0){
            this.vitesseDefaut = v;
        } else {
            this.vitesseDefaut = 80;
        }
    }
    
    
    @Override
    public double getVitesse(NatureTerrain nature) {
        switch(nature.toString()){
            case "TERRAIN_LIBRE" :
            case "HABITAT" :
                return this.vitesseDefaut;
      
            default :
                return 0;
        }
    }
    
    /*Constructeurs */
    
    public RobotARoues(Case pos) throws ErreurPosition {
        super(pos);
        this.capaciteMax = 5000;
        this.tempsRemplissageComp = 10;
        this.interventionUnitaire = 20;
        this.volumeRestant = this.capaciteMax;
    }

    public RobotARoues(double vitesse, Case pos) throws ErreurPosition {
        super(vitesse, pos);
        this.capaciteMax = 5000;
        this.tempsRemplissageComp = 10;
        this.interventionUnitaire = 20;
        this.volumeRestant = this.capaciteMax;
    }
    
    @Override
    public void remplirReservoir(){
        if(peutRemplirReservoir())
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
    	Carte carte = donnees.getCarte();
    	if(this.volumeRestant < this.capaciteMax) {
	        for (Direction dir : Direction.values()){
	            if (carte.voisinExiste(this.position, dir) && carte.getVoisin(this.position, dir).getNatureTerrain()==NatureTerrain.EAU)
	                return true;
	        }
    	}
        return false;
    }
    
    public String getFileOfRobot(){
    	return "images/robots/roues/";
    }
    
}