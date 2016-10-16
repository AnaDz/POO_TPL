/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;


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
        if (p.nature == NatureTerrain.TERRAIN_LIBRE || p.nature == NatureTerrain.HABITAT ) {      
            this.position=p;
            this.vitesse = this.vitesseDefaut;
        } else {
            throw new IllegalArgumentException("Un robot à roues essaye de sortir de son terrain");
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
    
}
