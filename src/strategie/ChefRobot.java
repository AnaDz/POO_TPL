package strategie;
import donneesSimulation.DonneesSimulation;
import evenements.GestionnaireEvents;
import carte.*;
import evenements.*;
import java.util.List;
import robots.*;

public class ChefRobot {

	DonneesSimulation data;
	GestionnaireEvents gE;
	int [] nbIterationsRobots;
	Case [] caseCouranteRobots;
	
	public ChefRobot(DonneesSimulation data, GestionnaireEvents gE){
		this.data = data;
		this.gE = gE;
		initialise();
	}
	
	private void initialise(){
		int taille = data.getListeRobots().size();
		nbIterationsRobots = new int[taille];
		caseCouranteRobots = new Case[taille];
		for(int i = 0; i < taille; i++) {
			nbIterationsRobots[i] = 0;
			caseCouranteRobots[i] = data.getListeRobots().get(i).getPosition();
		}
	}
    
	/**
	 * Programme la liste des déplacements élémentaires du robot pour se rendre à une position et les ajoute au Gestionnaire d'évenements.
	 * @param rob	le robot à déplacer
	 * @param deb	la case d'où part le robot
	 * @param fin	la case où arrive le robot
	 * @return		la date à laquelle le robot arrivera à la case fin
	 */
    public int bougeRobot(Robot rob, Case deb, Case fin, int date_debut) {
        /* On génère la liste de déplacement */
        Carte carte = data.getCarte();
        List<Case> deplacement = AStar.trouveChemin(carte, rob, deb, fin, gE.getPasDeTemps());
        Evenement deplace;
        int [][][] couts = AStar.getCouts();
        int i;
        /* On parcours la liste et cree les evements adéquats on commence a l'indice 1 car à l'indice 0 on a la position initiale du robot*/
        for (i=1; i<deplacement.size(); i++){
        	Direction dir = deplacement.get(i-1).getDirection(deplacement.get(i));
            int date = date_debut + couts[deplacement.get(i-1).getLigne()][deplacement.get(i-1).getColonne()][0];
            deplace = new DeplaceRobot(date,rob, dir);
            gE.ajouteEvenement(deplace);
        }
        return couts[deplacement.get(i-1).getLigne()][deplacement.get(i-1).getColonne()][0];
    }
}
