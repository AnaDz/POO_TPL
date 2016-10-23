/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;

import carte.*;


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
        Carte carte = donnees.getCarte();
        for (Direction dir : Direction.values()){
            if (carte.voisinExiste(this.position, dir) && carte.getVoisin(this.position, dir).getNatureTerrain()==NatureTerrain.EAU){
                this.volumeRestant = this.capaciteMax ;
            } else {
                /*exception*/
            }
        }        
             
    }
}