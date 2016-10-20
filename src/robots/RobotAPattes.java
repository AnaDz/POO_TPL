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

  /*********************************/
 /* CLASSE FILLE : ROBOT A PATTES */
/*********************************/

public class RobotAPattes extends Robot {

    @Override
    protected void setVitesseDefaut(double v){
       this.vitesseDefaut = 30;
    }
    
    @Override
    public void setPosition(Case p) {
        if (this.getVitesse(p.getNatureTerrain()) != 0) {
            this.position=p;
        } else {
            throw new IllegalArgumentException("Un robot à pattes essaye de marcher sur l'eau");
        }
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
    
    public RobotAPattes(Case pos) {
        super(pos);
        /* capacité max infinie comment faire ? */
    }
    
        @Override
    public void remplirReservoir(){
        /*LEVER UNE EXCEPTION "vous essayez de remplir d'eau un robot à pattes*/             
    }
}

