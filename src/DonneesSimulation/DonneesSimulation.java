package DonneesSimulation;
import carte.*;
import java.util.List;
import robots.*;

public class DonneesSimulation {
	private Carte carte;
	private List<Incendie> listeIncendies;
	private List<Robot> listeRobots;
	
	public DonneesSimulation(Carte carte, List<Incendie> listeIncendies, List<Robot> listeRobots){
		this.carte = carte; 
		this.listeIncendies = listeIncendies;
		this.listeRobots = listeRobots;
                //On n'oublie pas de donner les données à nos robots.
                Robot.setDS(this);
	}
	
	//Accesseurs
	public Carte getCarte(){
		return carte;
	}
	
	public List<Incendie> getListeIncendies(){
		return listeIncendies;
	}
	
	public List<Robot> getListeRobots(){
		return listeRobots;
	}
        
        public Incendie getIncendie(Case position){
            for(int i=0; i<this.getListeIncendies().size(); i++){
                Incendie incendie = this.listeIncendies.get(i);
                if (incendie.getCaseIncendie() == position ) {
                    return incendie;
                }
            }
            return null;
        }
}
