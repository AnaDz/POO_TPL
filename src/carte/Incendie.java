package carte;

import evenements.Evenement;

public class Incendie implements Cloneable{

    private Case caseIncendie;
    private int nbLitres;

    /*
     * *****************************
     * CONSTRUCTEURS 
     * *****************************
     */
    /**
     * Construit une instance de la classe Incendie
     *
     * @param c Case où es situé l'incendie
     * @param n nombre de litre qu'il faut pour l'éteindre
     */
    public Incendie(Case c, int n) {
        caseIncendie = c;
        nbLitres = n;
    }

    /*
     * ***************************
     * ACCESSEURS 
     * ***************************
     */
    /**
     * Retourne la case où est situé l'incendie
     *
     * @return case
     */
    public Case getCaseIncendie() {
        return caseIncendie;
    }

    /**
     * Retourne le nombre de litres qu'il faut pour éteindre l'incendie
     *
     * @return nombre de litres
     */
    public int getNbLitres() {
        return nbLitres;
    }

    /*
     * *******************************
     * MUTATEURS 
     * *******************************
     */
    /**
     * Modifie le nombre de litres necessaire pour éteindre l'incendie
     *
     * @param nbLitres (entier)
     */
    public void setNbLitres(int nbLitres) {
        this.nbLitres = nbLitres;
    }

    /**
     * Action éteindre l'incendie : diminue le nombre de litres necessaire pour
     * l'extinction
     *
     * @param vol volume déversé sur l'incendie
     */
    public void eteindre(int vol) {
        this.nbLitres -= vol;
    }

    @Override
    public String toString() {
        return "Incendie en position " + caseIncendie.toString() + " d'intensité " + nbLitres;
    }
    
    public Incendie clone() {
    	Incendie copie = null;
		try {
    		copie = (Incendie)super.clone();
    	} catch(CloneNotSupportedException cnse) {
    		cnse.printStackTrace(System.err);
    	}
    	return copie;
	}
}
