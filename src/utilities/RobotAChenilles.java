/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;


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
    public void setPosition(Case p){
        if (p.nature != NatureTerrain.EAU && p.nature != NatureTerrain.ROCHE ) {      
            this.position=p;
        } else {
            throw new IllegalArgumentException("Un robot à chenilles essaye de sortir de son terrain");
        }
        
        if (p.nature == NatureTerrain.FORET) {
            this.vitesse = this.vitesseDefaut/2;
        } else {
            this.vitesse = this.vitesseDefaut;
        }
    }
    
    /* constructeurs */

    public RobotAChenilles(Case pos) {
        super(pos);
        this.capaciteMax = 2000;
    }

    public RobotAChenilles(double vitesse, Case pos) {
        super(vitesse, pos);
        this.capaciteMax = 2000;
    }
    
}
