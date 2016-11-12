package strategie;
import donneesSimulation.DonneesSimulation;
import evenements.*;
import carte.*;

import java.util.ArrayList;
import java.util.List;
import robots.*;

public class ChefRobotElementaire {
	DonneesSimulation data;
	GestionnaireEvents GE;

	/** Les cases contenant de l'eau **/
	ArrayList <Case> caseEau;
	
	/** Le tableau d'assignement des incendies
	 * incendieAssignes[i] = true si le ième incendie est assigné*/
	boolean [] incendiesAssignes;
	
	/** Le tableau d'assignement des robots
	 * incendieAssignes[i] = true si le ième robot est assigné*/
	boolean [] robotsAssignes;
	
	public ChefRobotElementaire(DonneesSimulation data, GestionnaireEvents GE){
		this.data = data;
		this.GE = GE;
		initialise();
	}
	
	private void initialise(){
		
		caseEau = new ArrayList<Case>();
		for(int i = 0; i < data.getCarte().getNbLignes(); i++)
			for(int j = 0; j < data.getCarte().getNbColonnes(); j++)
				if(data.getCarte().getCase(i, j).getNatureTerrain() == NatureTerrain.EAU)
					caseEau.add(data.getCarte().getCase(i, j));
		
		incendiesAssignes = new boolean[data.getListeIncendies().size()];
		robotsAssignes = new boolean[data.getListeRobots().size()];
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
 	
	public void phaseAssignement(int date_debut) {
		for(int i = 0; i < robotsAssignes.length; i++) {
				for(int j = 0; j < incendiesAssignes.length; j++) {
					if(robotsAssignes[i] == false && incendiesAssignes[j] == false) {
						System.out.println("J'assigne le robot "+i);
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
			robotsAssignes[indexRob] = true;
			int finDeplacement = bougeRobot(rob, chemin, couts, date_debut);
			verseEau(rob, inc, finDeplacement);
			if(rob.getCapaciteMax() != (int) Double.POSITIVE_INFINITY) {
				remplirEau(rob, inc.getCaseIncendie(), finDeplacement+rob.getDureeVerser(GE.getPasDeTemps(), rob.getVolumeRestant()));
			}
		}
	}
	
	public void verseEau(Robot rob, Incendie inc, int date_debut) {
		int qte;
		if(rob.getVolumeRestant() > inc.getNbLitres())
			qte = inc.getNbLitres();
		else
			qte = rob.getVolumeRestant();
		System.out.println(qte);
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
		return cEau;
	}
	
	public void remplirEau(Robot rob, Case pos, int date_debut) {
		Case cEau = trouveCaseEau(rob, pos);
		if(rob.getVitesse(NatureTerrain.EAU) == 0) {
			//Alors le robot ne pourra pas se recharger sur une case contenant de l'eau
			//On cherche la case adjacente la plus proche
			int diffX = pos.getLigne()-cEau.getLigne();
			int diffY = pos.getColonne()-cEau.getColonne();
			if(diffY == 0 || Math.abs(diffX) <= Math.abs(diffY)) {
				cEau = data.getCarte().getCase(cEau.getLigne()+diffX, cEau.getColonne());
			} else {
				cEau = data.getCarte().getCase(cEau.getLigne(), cEau.getColonne()+diffY);
			}
		}
		//On devrait vérifier que la cEau est bien accessible (elle peut être bloquées par des rochers par exemple)
		//mais on va supposer qu'un tel cas n'existe pas.
		List<Case> chemin = AStar.trouveChemin(data.getCarte(), rob, pos, cEau, GE.getPasDeTemps());
		int [][][] couts = AStar.getCouts();
		if(chemin != null) {
			int finDeplacement = bougeRobot(rob, chemin, couts, date_debut);
			RemplirReservoir e = new RemplirReservoir(finDeplacement, rob);
			GE.ajouteEvenement(e);
		} else {
			System.out.println("La case "+cEau.toString()+" n'est pas accessible. Un des robots ne peut pas se remplir.");
		}
	}
	
}
