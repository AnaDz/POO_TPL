package DonneesSimulation;
import carte.*;
import robots.*;
import java.util.List;
import java.util.ArrayList;

public class DonneesSimulation {
	private Carte carte;
	private List<Incendie> listeIncendies;
	private List<Robot> listeRobots;
	
	public DonneesSimulation(int nbLignes, int nbColonnes, int tailleCases){
		carte = new Carte(nbLignes, nbColonnes, tailleCases);
		//listeIncendies = new ArrayList<Incendie>();
		listeRobots = new ArrayList<Robot>();
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
