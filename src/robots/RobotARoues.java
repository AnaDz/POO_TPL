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
    public void setPosition(Case p){
        if (this.getVitesse(p.getNatureTerrain())!=0 ) {      
            this.position=p;
        } else {
            throw new IllegalArgumentException("Un robot à roues essaye de sortir de son terrain");
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
    
    public RobotARoues(Case pos) {
        super(pos);
        this.capaciteMax = 5000;
    }

    public RobotARoues(double vitesse, Case pos) {
        super(vitesse, pos);
        this.capaciteMax = 5000;
    }
    
    @Override
    public void remplirReservoir(){
        
             
    }
}

