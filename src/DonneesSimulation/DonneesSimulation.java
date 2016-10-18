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
}
