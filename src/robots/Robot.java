/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;

import java.util.List;

import carte.*;
import donneesSimulation.DonneesSimulation;

/**
 * Classe mère : Robot
 */

public abstract class Robot {


    static protected DonneesSimulation donnees;
    
    protected Case position;
    protected Action action = Action.INNOCUPE;
    
    protected double vitesseDefaut = 0;
    protected Direction dir;

    protected int capaciteMax = 0;
    protected int volumeRestant = 0;
    protected int tempsRemplissageComp = 0;
    protected int interventionUnitaire = 0;
    
    
    /*******************************
     	CONSTRUCTEURS
     *******************************/
    
    /**
     * Construit une instance de la classe Robot en conservant la vitesse par défaut
     * @param pos	la position de départ du robot
     * @throws ErreurPosition
     */
    public Robot(Case pos) {
        setVitesseDefaut(-1);
        position = pos;
    }

    /**
     * Construit une instance de la classe Robot en modifiant la vitesse par défaut.
     * @param vitesse	la nouvelle vitesse
     * @param pos		la position de départ du Robot
     * @throws ErreurPosition
     */
    public Robot(double vitesse, Case pos) {
        vitesseDefaut = vitesse;
        position = pos;
    }
    
    /********************************
     		ACCESSEURS
     *******************************/
    
    /**
     * Retourne la case sur laquelle est le robot.
     */
    public Case getPosition() {
        return position;
    }
    
    /**
     * Retourne l'action qu'est en train d'effectuer le robot.
     * @return INNOCUPE ou DEPLACE ou REMPLIR ou VERSER
     */
    public Action getAction() {
        return action;
    }
    
    /**
     * Retourne la vitesse du robot en fonction d'une nature de terrain
     * @param nature	la nature du terrain
     * @return			la vitesse du robot sur une case de type nature.
     */
    public abstract double getVitesse(NatureTerrain nature);
    
    /**
     * Retourne la direction vers laquelle se déplace le robot. Null si ne se déplace pas.
     * @return		NORD ou SUD ou EST ou OUEST ou NULL
     */
    public Direction getDirection() {
        return dir;
    }
    
    /**
     * Retourne la capacité maximale du reservoir du robot
     */
    public int getCapaciteMax() {
        return capaciteMax;
    }
    
    /**
     * Retourne le volume contenu dans le réservoir du robot
     */
    public int getVolumeRestant() {
        return this.volumeRestant;
    }
    
    /**
     * Retourne le temps nécessaire au robot pour remplir son réservoir de 0 à sa capacité maximale
     */
    public int getTempsRemplissageComp() {
        return tempsRemplissageComp;
    }

    /**
     * Retourne le nombre de litres par seconde que peut verser le robot
     */
    public int getInterventionUnitaire() {
        return interventionUnitaire;
    }

    /**
     * Retourne la direction de la case passée en paramètre par rapport à la case courante.
     * @param p 	la case voisine à la case courante
     * @return		NORD ou SUD ou OUEST ou EST
     */
    //Que se passe-t-il si la case n'est pas voisine ?
    public Direction getDirection(Case p) {
        int dif_ligne = p.getLigne() - this.getPosition().getLigne();
        int dif_colonne = p.getColonne() - this.getPosition().getColonne();

        return Direction.getDir(dif_ligne, dif_colonne);
    }

    /**
     * Retourne le nombre d'itérations nécessaires au robot pour se déplace à la case c
     * @param h		le temps (en minutes) alloué au robot entre chaque itération
     * @param c		la case voisine à la position du robot
     */
    public int getDureeDeplacement(double h, Case c) {
        NatureTerrain nature = c.getNatureTerrain();
        int DistanceTotale = donnees.getCarte().getTailleCases();
        int DistanceParcourue = (int) (this.getVitesse(nature) * h * 1000 / 60);
        double duree = DistanceTotale/DistanceParcourue;
        return (int) duree+1;
    }
    /**
     * Retourne le chemin du dossier dans lequel les images propres au type du robot sont stockées.
     */
    public abstract String getFileOfRobot();
    
    /***********************************
     			MUTATEURS
     **********************************/
    
    /**
     * Modifie l'attribut DonneesSimulation, commun à tous les robots.
     * @param ds	les données associées à la simulation
     */
    static public void setDS(DonneesSimulation ds) {
        donnees = ds;
    }

    /**
     * Modifie la vitesse du robot
     * @param v		la nouvelle vitesse
     */
    protected abstract void setVitesseDefaut(double v);
    
    /**
     * Déplace le robot
     * @param p		la nouvelle position du robot
     */
    public void setPosition(Case p) {
        try {
        	NatureTerrain nat = p.getNatureTerrain();
            double vitesse = this.getVitesse(nat);
            if(vitesse != 0) {
            	this.position = p;
            } else {
            	System.out.println("Le robot en position "+position.toString()+" essaye d'aller en "+p.toString()+" alors qu'il ne peut pas.");
            	System.exit(0);
            }
        } catch (ArrayIndexOutOfBoundsException exc) {
        	System.out.println("Le robot en position "+position.toString()+ "essaye de se déplacer en "+p.toString());
        	System.exit(0);
        }
    }

    /**
     * Augmente la quantité d'eau présente dans le reservoir, sans vérifier que le robot est sur une case appropriée
     * @param vol	le volume ajoutée au réservoir
     */
    public void setVolumeRestant(int vol) {
    	this.volumeRestant = vol;
    }
    
    /**
     * Retourne true peut remplir son réservoir, false sinon
     */
    public abstract boolean peutRemplirReservoir();
    
    /**
     * Augmente la quantité d'eau présente dans le reservoir, en vérifiant que le robot est sur une case appropriée
     * @param vol	le volume ajoutée au réservoir
     */
    public abstract void remplirReservoir(int qte);
    
    

    
    /**
     * Retourne true si le robot possède un volume suffisant pour pouvoir le verser et qu'il ait sur un incendie, false sinon
     * @param vol	le volume que robot veut verser
     */
    public boolean peutDeverserEau(int vol) {
        Incendie incendievise = donnees.getIncendie(this.position);
        if (incendievise != null && vol <= this.volumeRestant) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Deverse vol litres d'eau
     */
    public void deverserEau(int vol) {
        Incendie incendievise = donnees.getIncendie(this.position);
        if(peutDeverserEau(vol)) {
        	this.volumeRestant -= vol;
        	incendievise.eteindre(vol);
        } else {
        	throw new IllegalArgumentException("Volume supérieur au volume restant ou le robot n'est pas sur un incendie");
        }
    }

    /**
     * Modifie l'action courante du robot
     * @param action	INNOCUPE ou DEPLACE ou REMPLIR ou VERSER
     */
    public void switchAction(Action action) {
        this.action = action;
    }

    /**
     * Modifie la direction vers laquelle se déplace le robot
     * @param dir	NORD ou SUD ou OUEST ou EST
     */
    public void setDirection(Direction dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return new String("Le robot réalise l'action :" + this.getAction());
    }

}
