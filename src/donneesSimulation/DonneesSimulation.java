package donneesSimulation;

import carte.*;

import java.util.List;
import robots.*;

public class DonneesSimulation {

    private Carte carte;
    private List<Incendie> listeIncendies;
    private List<Robot> listeRobots;

    /*
     * *****************************
     * CONSTRUCTEURS 
     * *****************************
     */
    /**
     * Construit une instance de la classe DonneesSimulation
     *
     * @param carte de la simulation
     * @param listeIncendies présents dans la simulation
     * @param listeRobots présents dans la simulation
     */
    public DonneesSimulation(Carte carte, List<Incendie> listeIncendies, List<Robot> listeRobots) {
        this.carte = carte;
        this.listeIncendies = listeIncendies;
        this.listeRobots = listeRobots;
        //On n'oublie pas de donner les données à nos robots.
        Robot.setDS(this);
    }

    /*
     * ***************************
     * ACCESSEURS 
     * ***************************
     */
    /**
     * Retourne la carte de la simulation
     *
     * @return carte
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * Retourne la liste des incendies présents dans la simulation
     *
     * @return liste incendies
     */
    public List<Incendie> getListeIncendies() {
        return listeIncendies;
    }

    /**
     * Retourne la liste des robots présents dans la simulation
     *
     * @return liste robots
     */
    public List<Robot> getListeRobots() {
        return listeRobots;
    }

    /**
     * Retourne l'incendie située dans la case en argument
     *
     * @param position case où l'on souhaite récupérer l'incendie
     * @return l'incendie présent sur la case ou null s'il n'y a pas d'incendie
     */
    public Incendie getIncendie(Case position) {
        for (int i = 0; i < this.getListeIncendies().size(); i++) {
            Incendie incendie = this.listeIncendies.get(i);
            if (incendie.getCaseIncendie() == position) {
                return incendie;
            }
        }
        return null;
    }

}
