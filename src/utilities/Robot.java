/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author skip
 */

/* ce qui est a traiter :  Temps remplir et vider */


  /***********************/
 /* CLASSE MERE : ROBOT */
/***********************/

class Robot {
    
    protected double vitesse;
    
    protected double vitesseDefaut=0;
    
    protected Case position; 
    
    protected int capaciteMax = 0;
    
    protected int volumeRestant = 0;
    

    
    protected void setVitesseDefaut(double v){
        if (v >= 0) {
            this.vitesseDefaut = v;
        } else {
            throw new IllegalArgumentException("on essaye d'instaurer une vitesse par défaut négative");
        }    
    }
  
    /* de base, dès qu'on modifie la position on modifie la vitesse en conséquence*/
        
    public void setPosition(Case p){
        this.position=p;
        this.vitesse = this.vitesseDefaut;
    }
     
    /*constructeur qui ne modifie pas la vitesse par défaut*/
    public Robot(Case pos) {
        this.setVitesseDefaut(-1);
        this.setPosition(pos);
    }
    
    /*constructeur avec une nouvelle vitesse définie*/
    public Robot(double vitesse, Case pos) {
        this.setVitesseDefaut(vitesse);
        this.setPosition(pos);
    }
    
    
    
    public Case getPosition(){
        return position;
    }

    
    public double getVitesse(NatureTerrain nature){
        return vitesse;
    }
    
    public int getVolumeRestant() {
        return this.volumeRestant;
    }
    
    
    public void remplirReservoir(){
        this.volumeRestant=this.capaciteMax;        
    }
    
    public void deverserEau(int vol){
        if (vol>=this.volumeRestant) {
            this.volumeRestant -= vol;
        } else {
            throw new IllegalArgumentException("Volume supérieur au volume restant");
        }
        
        /* à ajouter quand besoin de répercuter sur le feu*/
    }

    @Override
    public String toString() {
        return new String("le robot a une vitesse de " + this.vitesse + "km/h et une capacité de " + this.capaciteMax + "L et une vitesse par défaut de "+this.vitesseDefaut + "km/h");
    }
}


  /************************/
 /* CLASSE FILLE : DRONE */
/************************/

class Drone extends Robot {

    
    @Override
    protected void setVitesseDefaut(double v){
        if (v >= 0 && v <= 150) {
            this.vitesseDefaut = v;
        } else {
            this.vitesseDefaut = 100;
        }    
    }
    
    /*Pas besoin de modifier setPosition car le drone peut voler partout */
    
    /* Constructeurs */
    
    public Drone(Case pos) {
        super(pos); 
        this.capaciteMax = 10000;
    }

    public Drone(double vitesse, Case pos) {
        super(vitesse, pos);
        this.capaciteMax = 10000;
    }
    
    @Override
    public void remplirReservoir(){
        if (this.position.getNatureTerrain() == NatureTerrain.EAU) {
            this.volumeRestant=this.capaciteMax;
        } else {
            /* LEVER UNE EXCEPTION */
            throw new IllegalArgumentException("le drone essaye de se remplir en dehors de l'eau");
        }            
    }
    
}


  /************************************/
 /* CLASSE FILLE : ROBOT A CHENILLES */
/************************************/

class RobotAChenilles extends Robot {
    

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
        if (p.getNatureTerrain() != NatureTerrain.EAU && p.getNatureTerrain() != NatureTerrain.ROCHE ) {      
            this.position=p;
        } else {
            throw new IllegalArgumentException("Un robot à chenilles essaye de sortir de son terrain");
        }
        
        if (p.getNatureTerrain() == NatureTerrain.FORET) {
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
    
    @Override
    public void remplirReservoir(){
        
             
    }
    
}


  /*********************************/
 /* CLASSE FILLE : ROBOT A PATTES */
/*********************************/

class RobotAPattes extends Robot {

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
    
}


  /********************************/
 /* CLASSE FILLE : ROBOT A ROUES */
/********************************/

class RobotARoues extends Robot {

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
        if (p.getNatureTerrain() == NatureTerrain.TERRAIN_LIBRE || p.getNatureTerrain() == NatureTerrain.HABITAT ) {      
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

