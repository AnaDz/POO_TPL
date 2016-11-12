package evenements;

import java.util.*;
import strategie.ChefRobotElementaire;


public class GestionnaireEvents {
	
	// Le chef en charge de l'opération
	private ChefRobotElementaire chef;
	
	// indique si un restart a été effectué
	private boolean modeRestart = false;
	
	//Date courante de la simulation
	private int dateSimulation;
	
	//Pas de temps en minutes
	//Ce pas de temps correspond au temps (en minutes) alloué au robot entre chaque evenements.
	//Exemple :
	//Evenement i = "Déplacer Robot A vers le nord, Deplacer Robot B vers l'est, et remplir Robot C"
	//A l'execution : Le Robot A se deplace h min vers le nord, idem pour le B, et le robot C se remplit pendant h minutes
	private double h = 0.5;
	
	//File à priorité contenant des évènements;
	private PriorityQueue<Evenement> listeEvenements;
	
	private List<Evenement> poubelle;
	
	public GestionnaireEvents() {
		dateSimulation = -1;
		listeEvenements = new PriorityQueue<Evenement>();
		poubelle = new ArrayList<Evenement>();
	}
	
	public double getPasDeTemps() {
		//System.out.println(listeEvenements.comparator());
		return h;
	}
	
	public void setChef(ChefRobotElementaire chef) {
		this.chef = chef;
	}

	public void ajouteEvenement(Evenement e){
		listeEvenements.add(e);
	}
	
	public void incrementeDate(){
		dateSimulation += 1;
		if(modeRestart == false && chef != null && dateSimulation%chef.getPasDeTps() == 0) {
			System.out.println("Le chef élémentaire donne des directives, date = "+dateSimulation +" :");
			chef.donneDirectives(dateSimulation);
		}
		
		Iterator<Evenement> it = listeEvenements.iterator();
		while(it.hasNext()){
			Evenement e = it.next();
			if(e.getDate() <= dateSimulation){
				e.execute();
				poubelle.add(e);
				it.remove(); //on retire l'événement qu'on vient d'executer.
			}
			else 
				break;
		}
	}
	
	public long getDateSimulation(){
		return dateSimulation;
	}
	
	public void restartGestionnaireEvents(){
		this.dateSimulation = -1;
		this.listeEvenements.addAll(poubelle);
		poubelle.clear();
		modeRestart = true;
	}
	
	public boolean simulationTerminee(){
		return listeEvenements.isEmpty();
	}
	
	
	public PriorityQueue<Evenement> getListeEvenement(){
		return listeEvenements;
	}
	
	@Override
	public String toString(){
		Iterator<Evenement> it = listeEvenements.iterator();
		String res = "";
		while(it.hasNext()){
			res += it.next().toString();
		}
		return res;
	}
	
}
