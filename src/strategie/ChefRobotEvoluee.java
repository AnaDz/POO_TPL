package strategie;
import donneesSimulation.DonneesSimulation;
import evenements.GestionnaireEvents;
import carte.*;
import java.util.*;

public class ChefRobotEvoluee {

	DonneesSimulation data;
	GestionnaireEvents GE;
	
	/** Le nombre d'itérations à laquelle les robots seront dans l'action INNOCUPEE **/
	int [] nbIterationsRobots;
	
	/** La case courante des robots **/
	Case [] caseCouranteRobots;
	
	/** Les cases contenant de l'eau **/
	ArrayList <Case> caseEau;
	
	/** Une copie de la liste d'incendies **/
	ArrayList <Incendie> listeIncendies;
	
	public ChefRobotEvoluee(DonneesSimulation data, GestionnaireEvents GE){
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
		
		caseEau = new ArrayList<Case>();
		for(int i = 0; i < data.getCarte().getNbLignes(); i++)
			for(int j = 0; j < data.getCarte().getNbColonnes(); j++)
				if(data.getCarte().getCase(i, j).getNatureTerrain() == NatureTerrain.EAU)
					caseEau.add(data.getCarte().getCase(i, j));
		
		listeIncendies = new ArrayList<Incendie>();
		for(Incendie inc : data.getListeIncendies())
			listeIncendies.add(inc.clone());
	}
	
	private void phaseEteindre(int dateSimulation) {
		
	}
}
