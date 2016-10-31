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

  /*********************************/
 /* CLASSE FILLE : ROBOT A PATTES */
/*********************************/

public class RobotAPattes extends Robot {

    @Override
    protected void setVitesseDefaut(double v){
       this.vitesseDefaut = 30;
    }
    
    @Override
    public double getVitesse(NatureTerrain nature) {
        switch(nature.toString()){
            case "ROCHE":
                return 10;
            case "EAU" :
                return 0;
            default :
                return this.vitesseDefaut;
        }
    }
    
    /* constructeur */
    
    public RobotAPattes(Case pos) throws ErreurPosition {
        super(pos);
        this.capaciteMax = (int) Double.POSITIVE_INFINITY;
        this.tempsRemplissageComp = 0;
        this.interventionUnitaire = 10;
        this.volumeRestant = this.capaciteMax;
    }
    
    @Override
    public void remplirReservoir(){
        /*LEVER UNE EXCEPTION "vous essayez de remplir d'eau un robot à pattes*/             
    }
    
    public void remplirReservoir(int qte){
        /*LEVER UNE EXCEPTION "vous essayez de remplir d'eau un robot à pattes*/             
    }
    
    @Override
    public boolean peutRemplirReservoir(){
    	return false;
    }
    public String getFileOfRobot(){
    	return "images/robots/pattes/";
    }
}

