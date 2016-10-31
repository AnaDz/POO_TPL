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


  /************************************/
 /* CLASSE FILLE : ROBOT A CHENILLES */
/************************************/

public class RobotAChenilles extends Robot {
    

    @Override 
    protected void setVitesseDefaut(double v) {
        if (v >= 0){
            this.vitesseDefaut = v;
        } else {
            this.vitesseDefaut = 60;
        }
    }
    
    
    @Override
    public double getVitesse(NatureTerrain nature){
        switch(nature.toString()){
            case "FORET":
                return this.vitesseDefaut/2;
            case "EAU" :
            case "ROCHE" :
                return 0;
            default :
                return this.vitesseDefaut;
        }
    }
    
    /* constructeurs */

    public RobotAChenilles(Case pos) throws ErreurPosition {
        super(pos);
        this.capaciteMax = 2000;
        this.tempsRemplissageComp = 5;
        this.interventionUnitaire = (int) (100/8) + 1;
        this.volumeRestant = this.capaciteMax;
    }

    public RobotAChenilles(double vitesse, Case pos) throws ErreurPosition {
        super(vitesse, pos);
        this.capaciteMax = 2000;
        this.tempsRemplissageComp = 5;
        this.interventionUnitaire = (int) (100/8) + 1;
        this.volumeRestant = this.capaciteMax;
    }
    
    @Override
    public void remplirReservoir(){
    	if (peutRemplirReservoir())
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
         for (Direction dir : Direction.values()){
             if (carte.voisinExiste(this.position, dir) && carte.getVoisin(this.position, dir).getNatureTerrain()==NatureTerrain.EAU)
                 return true;
         }
         return false;
    }
    
    public String getFileOfRobot(){
    	return "images/robots/chenille/";
    }
    
}

