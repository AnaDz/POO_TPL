package strategie;
import donneesSimulation.DonneesSimulation;
import evenements.*;
import robots.*;
import carte.*;
import java.util.*;
import robots.Robot;

public class ChefRobotEvolue {

	DonneesSimulation data;
	GestionnaireEvents GE;
	
	/** Le nombre d'itérations à laquelle les robots seront dans l'action INNOCUPEE **/
	int [] nbIterationRobots;
	
	/** Les cases contenant de l'eau **/
	ArrayList <Case> caseEau;
	
	public ChefRobotEvolue(DonneesSimulation data, GestionnaireEvents GE){
		this.data = data;
		this.GE = GE;
		initialise();
	}
	
	private void initialise(){
		nbIterationRobots = new int[data.getListeRobots().size()];
		
		caseEau = new ArrayList<Case>();
		for(int i = 0; i < data.getCarte().getNbLignes(); i++)
			for(int j = 0; j < data.getCarte().getNbColonnes(); j++)
				if(data.getCarte().getCase(i, j).getNatureTerrain() == NatureTerrain.EAU)
					caseEau.add(data.getCarte().getCase(i, j));		
	}
	
	/**
	 * Programme la liste des déplacements élémentaires du robot pour se rendre à une position et les ajoute au Gestionnaire d'évenements.
	 * @param rob			le robot à déplacer
	 * @param deb			la case d'où part le robot
	 * @param fin			la case où arrive le robot
	 * @param date_debut	la date à laquelle part le robot
	 * @return				la date à laquelle le robot arrivera à la case fin
	 */
    /*public int bougeRobot(Robot rob, Case deb, Case fin, int date_debut) {
        
        Carte carte = data.getCarte();
        List<Case> deplacement = AStar.trouveChemin(carte, rob, deb, fin, GE.getPasDeTemps());
        Evenement deplace;
        int [][][] couts = AStar.getCouts();
        int i;
        for (i=1; i<deplacement.size(); i++){
        	Direction dir = deplacement.get(i-1).getDirection(deplacement.get(i));
            int date = date_debut + couts[deplacement.get(i-1).getLigne()][deplacement.get(i-1).getColonne()][0];
            deplace = new DeplaceRobot(date,rob, dir);
            GE.ajouteEvenement(deplace);
        }
        return couts[deplacement.get(i-1).getLigne()][deplacement.get(i-1).getColonne()][0];
    }*/
    
    public void bougeRobot(Robot rob, List<Case> chemin, int [][][] couts, int date_debut) {
    	Evenement deplace;
    	int i;
    	for (i=1; i<chemin.size(); i++){
        	Direction dir = chemin.get(i-1).getDirection(chemin.get(i));
            int date = date_debut + couts[chemin.get(i-1).getLigne()][chemin.get(i-1).getColonne()][0];
            deplace = new DeplaceRobot(date,rob, dir);
            GE.ajouteEvenement(deplace);
    	}
    }
 	
	public void phaseEteindre(int date_debut) {
		List<Case> plusCourtChemin1 = null;
		List<Case> plusCourtChemin2 = null;
		int [][][] coutsDep = null;
		int tempsRob1 = Integer.MAX_VALUE;
		int tempsRob2;
		Robot robAssigne = null;
		boolean assignement; //indique si un incendie a été assigné à un robot
		boolean [] assignes = new boolean[data.getListeRobots().size()]; // assignes[i] = true sur le Robot i est assigné à un incendie, false sinon
		
		for(Incendie inc : data.getListeIncendies()) {
			assignement = false;
			tempsRob1 = Integer.MAX_VALUE;
			for(Robot rob : data.getListeRobots()) {
				if(inc != null && rob.getAction() == Action.INNOCUPE && assignes[data.getListeRobots().indexOf(rob)] == false) {
					assignement = true;
					plusCourtChemin2 = AStar.trouveChemin(data.getCarte(), rob, rob.getPosition(), inc.getCaseIncendie(), GE.getPasDeTemps());
					tempsRob2 = AStar.getCouts()[inc.getCaseIncendie().getLigne()][inc.getCaseIncendie().getColonne()][0];
					if(tempsRob2 < tempsRob1) {
						tempsRob1 = tempsRob2;
						plusCourtChemin1 = plusCourtChemin2;
						coutsDep = AStar.getCouts();
						robAssigne = rob;
					}
				}
			}
			if(assignement == true) {
				int indexRob = data.getListeRobots().indexOf(robAssigne);
				assignes[indexRob] = true;
				bougeRobot(robAssigne, plusCourtChemin1, coutsDep, date_debut);
				nbIterationRobots[indexRob] += tempsRob1;
			}
		}
	}
	
	private List<Incendie> trieIncendies() {
		List<Incendie> listeTriee = new ArrayList<Incendie>();
		for(Incendie inc : data.getListeIncendies()) {
			for(Robot rob : data.getListeRobots()) {
				Case caseInc = inc.getCaseIncendie();
				Case caseRob = rob.getPosition();
				
			}
		}
		
		return listeTriee;
	}
}
