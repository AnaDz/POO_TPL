package strategie;
import donneesSimulation.DonneesSimulation;
import evenements.GestionnaireEvents;
import carte.*;
import java.util.LinkedList;
import java.util.Deque;

public class ChefRobot {

	DonneesSimulation data;
	GestionnaireEvents GE;
	int [] nbIterationsRobots;
	Case [] caseCouranteRobots;
	
	public ChefRobot(DonneesSimulation data, GestionnaireEvents GE){
		this.data = data;
		this.GE = GE;
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
}
