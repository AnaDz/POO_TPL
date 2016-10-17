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
        if (p.getNatureTerrain() != NatureTerrain.EAU) {
            this.position=p;
        } else {
            throw new IllegalArgumentException("Un robot à pattes essaye de marcher sur l'eau");
        }
        if (p.getNatureTerrain() == NatureTerrain.ROCHE) {
            this.vitesse = 10;
        } else {
            this.vitesse = this.vitesseDefaut;
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

