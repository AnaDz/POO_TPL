package strategie;

import donneesSimulation.DonneesSimulation;
import evenements.*;
import carte.*;
import java.util.*;
import robots.Robot;

public class ChefRobotEvolue {

	private DonneesSimulation data;
	private GestionnaireEvents GE;
	
	/** Les cases contenant de l'eau **/
	ArrayList <Case> caseEau;
	
	/** Le tableau d'assignement des incendies
	 * incendieAssignes[i] = true si le ième incendie est assigné*/
	boolean [] incendiesAssignes;
	
	/** La date à partir de laquelle le robot est libre */
	int [] dateLibreRobot;
	
	/** Le nombre de robots assignés */
	int nbOcc = 0;
	
	
	public ChefRobotEvolue(DonneesSimulation data, GestionnaireEvents GE){
		this.data = data;
		this.GE = GE;
		initialise();
	}
	
	public void initialise(){

		incendiesAssignes = new boolean[data.getListeIncendies().size()];
		dateLibreRobot = new int[data.getListeRobots().size()];
		
		caseEau = new ArrayList<Case>();
		for(int i = 0; i < data.getCarte().getNbLignes(); i++)
			for(int j = 0; j < data.getCarte().getNbColonnes(); j++)
				if(data.getCarte().getCase(i, j).getNatureTerrain() == NatureTerrain.EAU)
					caseEau.add(data.getCarte().getCase(i, j));
	}
	
	/**
	 * Programme la liste des déplacements élémentaires pour qu'un robot se déplace suivant un chemin
	 * @param rob			le robot à déplacer
	 * @param chemin		le chemin à suivre
	 * @param couts			le couts du chemin = les dates auxquelles le robot arrive sur les cases
	 * @param date_debut	la date à laquelle le robot commence à se déplacer
	 * @return				la date à laquelle le robot est arrivé sur la dernière case du chemin
	 */
    public int bougeRobot(Robot rob, List<Case> chemin, int [][][] couts, int date_debut) {
    	Evenement deplace;
    	int i;
    	for (i=1; i<chemin.size(); i++){
        	Direction dir = chemin.get(i-1).getDirection(chemin.get(i));
            long date = date_debut + couts[chemin.get(i-1).getLigne()][chemin.get(i-1).getColonne()][0];
            deplace = new DeplaceRobot(date,rob, dir);
            GE.ajouteEvenement(deplace);
        }
        return date_debut + couts[chemin.get(i - 1).getLigne()][chemin.get(i - 1).getColonne()][0];
    }
 	
	public void donneDirectives(int date_debut) {
		int nbRobots = data.getListeRobots().size();
		
		
		//On cherche un incendie non assigné (n'importe lequel) et un incendie d'intensite max
		int nbIncendies = data.getListeIncendies().size();
		Incendie incNonAssigne = null;
		Incendie incIntMax = null;
		int intensiteMax = 0;
		for(Incendie inc : data.getListeIncendies()){
			if(inc.getNbLitres() <= 0) {
				nbIncendies--;
			} else if(incendiesAssignes[data.getListeIncendies().indexOf(inc)] == false) {
				incNonAssigne = inc;
			}
			
			if(inc.getNbLitres() > intensiteMax){
				intensiteMax = inc.getNbLitres();
				incIntMax = inc;
			}
		}
		
		Incendie incPropose; //l'incendie qu'on va proposer au robot
		if(nbRobots <= nbIncendies)
			incPropose = incNonAssigne;
		else
			incPropose = incIntMax;
		
		int minCout = Integer.MAX_VALUE;
		Robot robAssigne = null;
		int indexRob;
		if(nbIncendies != 0) {
			for(Robot rob : data.getListeRobots()) {
				indexRob = data.getListeRobots().indexOf(rob);
				if(dateLibreRobot[indexRob] <= date_debut) {
					AStar.trouveChemin(data.getCarte(), rob, rob.getPosition(), incPropose.getCaseIncendie(), GE.getPasDeTemps());
					int newCout = AStar.getCouts()[incPropose.getCaseIncendie().getLigne()][incPropose.getCaseIncendie().getColonne()][0];
					if(newCout < minCout) {
						minCout = newCout;
						robAssigne = rob;
					}
				}
			}
		}
		
		if(robAssigne != null && incPropose != null) {
			assigneRobotIncendie(data.getListeRobots().indexOf(robAssigne), data.getListeIncendies().indexOf(incPropose), date_debut);
		}
			
	}
	
	
	public void assigneRobotIncendie(int indexRob, int indexInc, int date_debut) {
		Robot rob = data.getListeRobots().get(indexRob);
		Incendie inc = data.getListeIncendies().get(indexInc);
		List<Case> chemin = AStar.trouveChemin(data.getCarte(), rob, rob.getPosition(), inc.getCaseIncendie(), GE.getPasDeTemps());
		int [][][] couts = AStar.getCouts();
		if(chemin != null) {
			System.out.println("\tLe chef évolué assigne le robot en "+data.getListeRobots().get(indexRob).getPosition()+ " à l'incendie en "+data.getListeIncendies().get(indexInc).getCaseIncendie());
			
			incendiesAssignes[indexInc] = true;
			nbOcc++;
			
			int finDeplacement = bougeRobot(rob, chemin, couts, date_debut);
			
			int vol;
			if(rob.getVolumeRestant() > inc.getNbLitres())
				vol = inc.getNbLitres();
			else
				vol = rob.getVolumeRestant();

			verseEau(rob, inc, finDeplacement, vol);
			if(rob.getCapaciteMax() != (int) Double.POSITIVE_INFINITY || (rob.getVolumeRestant()-vol) < rob.getCapaciteMax()/4) {
				remplirEau(rob, inc.getCaseIncendie(), finDeplacement+rob.getDureeVerser(GE.getPasDeTemps(), rob.getCapaciteMax()));
			} else {
				dateLibreRobot[indexRob] = finDeplacement+rob.getDureeVerser(GE.getPasDeTemps(), data.getListeIncendies().get(indexInc).getNbLitres());
			}
		} else {
			System.out.println("Malheur ! Le robot en "+rob.getPosition()+" n'arrive pas à aller en "+inc.getCaseIncendie());
		}
	}
	
	public void verseEau(Robot rob, Incendie inc, int date_debut, int vol) {
		VerserEau e = new VerserEau(date_debut, rob, vol);
		GE.ajouteEvenement(e);
	}
	
	public Case trouveCaseEau(Robot rob, Case pos) {
		Case cEau = caseEau.get(0); //plus proche case contenant de l'eau
		int diffX = Math.abs(pos.getLigne()-cEau.getLigne());
		int diffY = Math.abs(pos.getColonne()-cEau.getColonne());
		int newDiffX, newDiffY;
		Case newcEau;
		for(int i = 1; i < caseEau.size(); i++) {
			newcEau = caseEau.get(i);
			newDiffX = Math.abs(newcEau.getLigne()-pos.getLigne());
			newDiffY = Math.abs(newcEau.getColonne()-pos.getColonne());
			if(newDiffX+newDiffY < diffX+diffY) {
				diffX = newDiffX;
				diffY = newDiffY;
				cEau = newcEau;
			}
		}
		
		
		if(rob.getVitesse(NatureTerrain.EAU) == 0) {
			//Alors le robot ne pourra pas se recharger sur une case contenant de l'eau
			//On cherche la case adjacente la plus proche
			diffX = pos.getLigne()-cEau.getLigne();
			diffY = pos.getColonne()-cEau.getColonne();
			if(diffY == 0 || Math.abs(diffX) <= Math.abs(diffY) && data.getCarte().getCase(cEau.getLigne()+diffX, cEau.getColonne()).getNatureTerrain() != NatureTerrain.EAU) {
				cEau = data.getCarte().getCase(cEau.getLigne()+(diffX/Math.abs(diffX)), cEau.getColonne());
			} else {
				cEau = data.getCarte().getCase(cEau.getLigne(), cEau.getColonne()+(diffY/Math.abs(diffY)));
			}
		}
		return cEau;
	}
	
	public void remplirEau(Robot rob, Case pos, int date_debut) {
		Case cEau = trouveCaseEau(rob, pos);
		
		//On devrait vérifier que la cEau est bien accessible (elle peut être bloquées par des rochers par exemple)...
		//...et en choisir une autre si ce n'est pas le cas...
		//...mais on va supposer qu'un tel cas n'existe pas.
		List<Case> chemin = AStar.trouveChemin(data.getCarte(), rob, pos, cEau, GE.getPasDeTemps());
		int [][][] couts = AStar.getCouts();
		if(chemin != null) {
			int finDeplacement = bougeRobot(rob, chemin, couts, date_debut);
			dateLibreRobot[data.getListeRobots().indexOf(rob)] = finDeplacement+rob.getDureeRemplir(GE.getPasDeTemps(), rob.getCapaciteMax());
			RemplirReservoir e = new RemplirReservoir(finDeplacement, rob);
			GE.ajouteEvenement(e);
		} else {
			System.out.println("La case "+cEau+" n'est pas accessible. Le robot en "+rob.getPosition()+" ne peut pas aller se remplir");
		}
	}
	
	public void libereAssignement(int indexInc) {
		incendiesAssignes[indexInc] = false;
	}
	
	public int getNbRobots(){
		return data.getListeRobots().size();
	}
	
}
