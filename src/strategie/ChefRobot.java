package strategie;
import donneesSimulation.DonneesSimulation;
import evenements.GestionnaireEvents;
import carte.*;
import evenements.*;
import java.util.LinkedList;
import java.util.Deque;
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
        
        public void bougeRobot(Robot rob, Carte carte, Case deb, Case fin) {
            /* On génère la liste de déplacement */
            List<Case> deplacement = AStar.trouveChemin(carte, rob, deb, fin);
            GestionnaireEvents gE = new GestionnaireEvents();
            Evenement deplace;
            /* On parcours la liste et cree les evements adéquats on commence a l'indice 1 car à l'indice 0 on a la position initiale du robot*/
            for (int i=1; i<deplacement.size(); i++){
                Direction dir = rob.getDirection(deplacement.get(i));
                /*CONVERSION A REVOIR*/
                int date = (int) AStar.couts[deplacement.get(i).getLigne()][deplacement.get(i).getColonne()][0];
                System.out.println(i);
                deplace = new DeplaceRobot(date,rob, dir);
                gE.ajouteEvenement(deplace);
            }
                       
        }
}
