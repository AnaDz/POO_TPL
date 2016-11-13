package strategie;
import donneesSimulation.DonneesSimulation;
import evenements.*;
import carte.*;

import java.util.ArrayList;
import java.util.List;
import robots.*;

public class ChefRobotElementaire {
	private DonneesSimulation data;
	private GestionnaireEvents GE;
	private int pasDeTps;
	
	/** Les cases contenant de l'eau **/
	ArrayList <Case> caseEau;
	
	/** Le tableau d'assignement des incendies
	 * incendieAssignes[i] = true si le ième incendie est assigné*/
	boolean [] incendiesAssignes;
	
	/** La date à partir de laquelle le robot est libre */
	int [] dateLibreRobot;
	
	public ChefRobotElementaire(DonneesSimulation data, GestionnaireEvents GE, int pasDeTps){
		this.data = data;
		this.GE = GE;
		this.pasDeTps = pasDeTps;
		initialise();
	}
	
	public int getPasDeTps(){
		return pasDeTps;
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
    	return date_debut + couts[chemin.get(i-1).getLigne()][chemin.get(i-1).getColonne()][0];
    }
 	
	public void donneDirectives(int date_debut) {
		System.out.println(data.getListeRobots());
		incendiesAssignes = new boolean[data.getListeIncendies().size()];
		for(int i = 0; i < data.getListeRobots().size(); i++) {
				for(int j = 0; j < incendiesAssignes.length; j++) {
					if(dateLibreRobot[i] <= date_debut && incendiesAssignes[j] == false && data.getListeRobots().get(i).getAction() == Action.INNOCUPE && data.getListeIncendies().get(j).getNbLitres() > 0) {
						System.out.println("\tJ'assigne le robot en "+data.getListeRobots().get(i).getPosition()+ " à l'incendie en "+data.getListeIncendies().get(j).getCaseIncendie());
						assigneRobotIncendie(i, j, date_debut);
					}
				}
		}
	}
	
	public void assigneRobotIncendie(int indexRob, int indexInc, int date_debut) {
		Robot rob = data.getListeRobots().get(indexRob);
		Incendie inc = data.getListeIncendies().get(indexInc);
		List<Case> chemin = AStar.trouveChemin(data.getCarte(), rob, rob.getPosition(), inc.getCaseIncendie(), GE.getPasDeTemps());
		int [][][] couts = AStar.getCouts();
		if(chemin != null) {
			incendiesAssignes[indexInc] = true;
			int finDeplacement = bougeRobot(rob, chemin, couts, date_debut);
			verseEau(rob, inc, finDeplacement);
			if(rob.getCapaciteMax() != (int) Double.POSITIVE_INFINITY) {
				remplirEau(rob, inc.getCaseIncendie(), finDeplacement+rob.getDureeVerser(GE.getPasDeTemps(), rob.getCapaciteMax()));
			} else {
				dateLibreRobot[indexRob] = finDeplacement+rob.getDureeVerser(GE.getPasDeTemps(), data.getListeIncendies().get(indexInc).getNbLitres());
			}
		} else {
			System.out.println("Malheur ! Le robot en "+rob.getPosition()+" n'arrive pas à aller en "+inc.getCaseIncendie());
		}
	}
	
	public void verseEau(Robot rob, Incendie inc, int date_debut) {
		int qte;
		if(rob.getVolumeRestant() > inc.getNbLitres())
			qte = inc.getNbLitres();
		else
			qte = rob.getVolumeRestant();
		VerserEau e = new VerserEau(date_debut, rob, qte);
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
	
}
