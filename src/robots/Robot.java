/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;
import carte.*;
import DonneesSimulation.DonneesSimulation;

/**
 *
 * @author skip
 */

    /* ce qui est a traiter :  Temps remplir et vider */


  /***********************/
 /* CLASSE MERE : ROBOT */
/***********************/

public abstract class Robot {
    
    protected double vitesseDefaut=0;
    
    protected Case position; 
    
    protected int capaciteMax = 0;
    
    protected int volumeRestant = 0;
    
    static protected DonneesSimulation donnees ;

    protected boolean occupe; //indique si le robot est occupé à se deplacer, eteindre un incendie, ou remplir son reservoir.
    
    protected Direction dir; //indique le sens de déplacement, null si ne se déplace pas
    
    /* Cette fonction est à appeller dans lecteur donnée */
    static public void setDS(DonneesSimulation ds) {
        donnees = ds;
    }
    
    abstract protected void setVitesseDefaut(double v);
    /* de base, dès qu'on modifie la position on modifie la vitesse en conséquence*/
       

    abstract public double getVitesse(NatureTerrain nature);


    /*constructeur qui ne modifie pas la vitesse par défaut*/
    public Robot(Case pos) {
        this.setVitesseDefaut(-1);
        this.setPosition(pos);
        this.occupe = false;
    }
    
    /*constructeur avec une nouvelle vitesse définie*/
    public Robot(double vitesse, Case pos) {
        this.setVitesseDefaut(vitesse);
        this.setPosition(pos);
        this.occupe = false;
    }
    
    public void setPosition(Case p){
        /* TODO vérifier que case dans carte */
        if (this.position == null) {
            this.position = p;
        } else {
            NatureTerrain nat = p.getNatureTerrain();
            double vitesse = this.getVitesse(nat);
            Direction dir = this.getDirection(p);
            if(vitesse != 0 && dir != null && donnees.getCarte().voisinExiste(p, dir)) {
                this.position = p;
            } else {
                /*exception*/
            }
        }
    }
    
    public Case getPosition(){
        return position;
    }

   
    
    public int getVolumeRestant() {
        return this.volumeRestant;
    }
    
    
    abstract public void remplirReservoir();
    
    
    public void deverserEau(int vol){
        Incendie incendievise = donnees.getIncendie(this.position);
        if (vol>=this.volumeRestant && incendievise != null) {
            this.volumeRestant -= vol;
            incendievise.eteindre(vol);
        } else {
            throw new IllegalArgumentException("Volume supérieur au volume restant ou robot n'est pas sur un incendie");
        }
    }
    
    
    protected Direction getDirection(Case p) {
        int dif_ligne = p.getLigne()- this.getPosition().getLigne();
        int dif_colonne = p.getColonne() - this.getPosition().getColonne();
 
        return Direction.getDir(dif_ligne, dif_colonne);
    }

    public boolean getOccupe(){
    	return occupe;
    }
    
    public void switchOccupe(){
    	this.occupe = !this.occupe;
    }
    
    public Direction getDirection(){
    	return dir;
    }
    
    public void setDirection(Direction dir){
    	this.dir = dir;
    }
    
    @Override
    public String toString() {
        return new String("le robot a une vitesse de " + this.getVitesse(this.getPosition().getNatureTerrain()) + "km/h et une capacité de " + this.capaciteMax + "L et une vitesse par défaut de "+this.vitesseDefaut + "km/h");
    }
    
    public abstract String getFileOfRobot();
}


