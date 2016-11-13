package evenements;

import java.util.*;
import strategie.*;

public class GestionnaireEvents {

    // Le chef en charge de l'opération
    private ChefRobotElementaire chefElem = null;
    private ChefRobotEvolue chefEvolue = null;

    //Date courante de la simulation
    private int dateSimulation;

    //Pas de temps en minutes
    //Ce pas de temps correspond au temps (en minutes) alloué au robot entre chaque evenements.
    //Exemple :
    //Evenement i = "Déplacer Robot A vers le nord, Deplacer Robot B vers l'est, et remplir Robot C"
    //A l'execution : Le Robot A se deplace h min vers le nord, idem pour le B, et le robot C se remplit pendant h minutes. 
    // Ainsi, par exemple pour remplir totalement un robot il mettre X pas (X*h = temps total de remplissage en minute)
    private double h = 0.5;

    //File à priorité contenant des évènements;
    private PriorityQueue<Evenement> listeEvenements;

    private List<Evenement> poubelle;

    /**
     * Crée un gestionnaire d'évenements en gardant le pas par défaut (0.5)
     */
    public GestionnaireEvents() {
        dateSimulation = -1;
        listeEvenements = new PriorityQueue<Evenement>();
        poubelle = new ArrayList<Evenement>();
    }

    /**
     * Crée un gestionnaire d'événements en modifiant le pas
     *
     * @param pasDeTps le pas
     */
    public GestionnaireEvents(int pasDeTps) {
        this();
        h = pasDeTps;
    }

    /*
     * ***************************
     * ACCESSEURS 
     * ***************************
     */
    /**
     * Retourne le pas de la simulation
     *
     * @return le pas h
     */
    public double getPasDeTemps() {
        //System.out.println(listeEvenements.comparator());
        return h;
    }

    /**
     * Obtiention de la liste d'événements
     *
     * @return liste d'événements
     */
    public PriorityQueue<Evenement> getListeEvenement() {
        return listeEvenements;
    }

    /**
     * Retourne la date durant la simulation
     *
     * @return date
     */
    public long getDateSimulation() {
        return dateSimulation;
    }

    /**
     * Retourne le chef pour la stratégie élémentaire
     *
     * @return chef élémentaire
     */
    public ChefRobotElementaire getChefElementaire() {
        return chefElem;
    }

    /**
     * Retourne le chef pour la stratégie évoluée
     *
     * @return Chef évolué
     */
    public ChefRobotEvolue getChefEvolue() {
        return chefEvolue;
    }

    /*
     * *******************************
     * MUTATEURS 
     * *******************************
     */
    /**
     * Déclare un chef à stratégie élémentaire pour la gestion d'événement
     *
     * @param chef le chef qui gérera la simulation
     */
    public void setChef(ChefRobotElementaire chef) {
        chefElem = chef;
    }

    /**
     * Déclare un chef à stratégie évoluée pour la gestion d'événement
     *
     * @param chef le chef qui gérera la simulation
     */
    public void setChef(ChefRobotEvolue chef) {
        chefEvolue = chef;
    }

    /**
     * Ajout d'un événement dans la liste d'évenements
     *
     * @param e évenement à ajouter
     */
    public void ajouteEvenement(Evenement e) {
        listeEvenements.add(e);
    }

    /**
     * Incrémente la date de la simulation (en terme d'itérations)
     */
    public void incrementeDate() {
        dateSimulation += 1;
        if ((chefElem != null && dateSimulation % chefElem.getPasDeTps() == 0)) {
            System.out.println("Le chef élémentaire donne des directives, date = " + dateSimulation + " :");
            chefElem.donneDirectives(dateSimulation);
        } else if (chefEvolue != null) {
            chefEvolue.donneDirectives(dateSimulation);
        }

        Iterator<Evenement> it = listeEvenements.iterator();
        while (it.hasNext()) {
            Evenement e = it.next();
            if (e.getDate() <= dateSimulation) {
                e.execute();
                poubelle.add(e);
                it.remove(); //on retire l'événement qu'on vient d'executer.
            } else {
                break;
            }
        }
    }

    /**
     * Fin forcée de la simulation
     */
    public void restartGestionnaireEvents() {

        this.dateSimulation = -1;
        if (chefElem == null && chefEvolue == null) {
            this.listeEvenements.addAll(poubelle);
            poubelle.clear();
        } else {
            this.listeEvenements.clear();
            Evenement e = new ProgrammeFin(Integer.MAX_VALUE);
            ajouteEvenement(e);
			poubelle.clear();
			if(chefEvolue != null) {
				chefEvolue.initialise();
			} else {
				chefElem.initialise();
			}
		}
		
	}
	
	public boolean simulationTerminee(){
		return listeEvenements.isEmpty();
	}
	
	public void supprimeDernierElement(){
		PriorityQueue<Evenement> listeEvenementsNew = new PriorityQueue<Evenement>(); 
		while(listeEvenements.size() > 1) {
		        listeEvenementsNew.add(listeEvenements.poll());
		}
		listeEvenements.clear();
		listeEvenements = listeEvenementsNew;
	}
	
	public PriorityQueue<Evenement> getListeEvenement(){
		return listeEvenements;
	}
	
	@Override
	public String toString(){
		Iterator<Evenement> it = listeEvenements.iterator();
		String res = "";
		while(it.hasNext()){
			res += it.next().toString();
		}
		return res;
	}
	
}
