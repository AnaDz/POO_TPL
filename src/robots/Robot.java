/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robots;

import carte.*;
import donneesSimulation.DonneesSimulation;
import exceptions.*;
import evenements.*;
import interfacegraphique.*;

/**
 *
 * @author skip
 */

/* ce qui est a traiter :  Temps remplir et vider */
/**
 * ********************
 */
/* CLASSE MERE : ROBOT */
/**
 * ********************
 */
public abstract class Robot {

    protected double vitesseDefaut = 0;

    protected Case position;

    protected int capaciteMax = 0;

    protected int volumeRestant = 0;

    static protected DonneesSimulation donnees;

    protected Action action = Action.INNOCUPE; //indique si le robot est pas occupé (0), occupé à se deplacer (1), occupé à remplir son reservoir (2), occupé à eteindre un incendie (3); 

    protected Direction dir; //indique le sens de déplacement, null si ne se déplace pas

    protected int tempsRemplissageComp = 0; //en minutes;

    protected int interventionUnitaire = 0; //en litres par secondes;

    /* Cette fonction est à appeller dans lecteur donnée */
    static public void setDS(DonneesSimulation ds) {
        donnees = ds;
    }

    abstract protected void setVitesseDefaut(double v);

    /* de base, dès qu'on modifie la position on modifie la vitesse en conséquence*/

    abstract public double getVitesse(NatureTerrain nature);


    /*constructeur qui ne modifie pas la vitesse par défaut*/
    public Robot(Case pos) throws ErreurPosition {
        this.setVitesseDefaut(-1);
        this.setPosition(pos);
    }

    /*constructeur avec une nouvelle vitesse définie*/
    public Robot(double vitesse, Case pos) throws ErreurPosition {
        this.setVitesseDefaut(vitesse);
        this.setPosition(pos);
    }

    public int getTempsRemplissageComp() {
        return tempsRemplissageComp;
    }

    public int getInterventionUnitaire() {
        return interventionUnitaire;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setPosition(Case p) throws ErreurPosition {
        /* TODO vérifier que case dans carte */
        if (this.position == null) {
            this.position = p;
        } else {
            NatureTerrain nat = p.getNatureTerrain();
            double vitesse = this.getVitesse(nat);
            Direction dir = this.getDirection(p);
            if ((p.getColonne() < donnees.getCarte().getNbColonnes()) && (p.getLigne() < donnees.getCarte().getNbLignes())) {
                if (vitesse != 0) {
                    if (this.getDirection(p) != null && donnees.getCarte().voisinExiste(p, this.getDirection(p))) {
                        this.position = p;
                    } else {
                        throw new ErreurPosition("La case demandée n'est pas voisin du robot en position" + this.position.toString());
                    }

                } else {
                    throw new ErreurPosition("Le robot en position" + this.position.toString() + "essaye d'aller sur un terrain qui n'est pas le sien (" + p.getNatureTerrain() + ")");
                }

            } else {
                throw new ErreurPosition("On veut déplacer un robot dans une case qui n'existe pas sur la carte");
            }

        }
    }

    public Case getPosition() {
        return position;
    }

    public int getVolumeRestant() {
        return this.volumeRestant;
    }

    public abstract void remplirReservoir();

    public abstract void remplirReservoir(int qte);

    public abstract boolean peutRemplirReservoir();

    public void deverserEau(int vol) {
        Incendie incendievise = donnees.getIncendie(this.position);
        System.out.println("incendievise = " + incendievise.toString());
        System.out.println("Vol = " + vol + " et volumeRestant = " + this.volumeRestant);
        if (vol <= this.volumeRestant && incendievise != null) {
            this.volumeRestant -= vol;
            incendievise.eteindre(vol);
        } else {
            throw new IllegalArgumentException("Volume supérieur au volume restant ou robot n'est pas sur un incendie");
        }
    }

    public boolean peutDeverserEau(int vol) {
        Incendie incendievise = donnees.getIncendie(this.position);
        if (incendievise != null && vol <= this.volumeRestant) {
            return true;
        } else {
            return false;
        }
    }

    protected Direction getDirection(Case p) {
        int dif_ligne = p.getLigne() - this.getPosition().getLigne();
        int dif_colonne = p.getColonne() - this.getPosition().getColonne();

        return Direction.getDir(dif_ligne, dif_colonne);
    }

    public Action getAction() {
        return action;
    }

    public void switchAction(Action action) {
        this.action = action;
    }

    public Direction getDirection() {
        return dir;
    }

    public void setDirection(Direction dir) {
        this.dir = dir;
    }

    public int getDureeDeplacement(DonneesSimulation data, Robot rob, Simulateur sim, GestionnaireEvents gE) {
        NatureTerrain nature = rob.getPosition().getNatureTerrain();
        double DistanceParcourue = this.getVitesse(nature) * gE.getPasDeTemps() * (1000 / 60);
        DistanceParcourue = DistanceParcourue * sim.getTailleCase();
        DistanceParcourue = DistanceParcourue / data.getCarte().getTailleCases();
        double duree = sim.getTailleCase() / DistanceParcourue;
        /* durée de la traversée d'une case dans l'échelle réelle */
        if ((int) duree < duree) {
            return (int) duree + 2;
        } else {
            return (int) duree + 1;
        }

    }

    @Override
    public String toString() {
        return new String("le robot a une vitesse de " + this.getVitesse(this.getPosition().getNatureTerrain()) + "km/h et une capacité de " + this.capaciteMax + "L et une vitesse par défaut de " + this.vitesseDefaut + "km/h");
    }

    public abstract String getFileOfRobot();
}
