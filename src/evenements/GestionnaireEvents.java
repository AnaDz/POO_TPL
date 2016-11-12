package evenements;

import java.util.*;



public class GestionnaireEvents {

	//Date courante de la simulation
	private long dateSimulation;
	
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

	public void ajouteEvenement(Evenement e){
		listeEvenements.add(e);
	}
	
	public void incrementeDate(){
		dateSimulation += 1;
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
	
	public void returnDebSimulation(){
		this.dateSimulation = -1;
		this.listeEvenements.addAll(poubelle);
		poubelle.clear();
	}
	
	public boolean simulationTerminee(){
		return (listeEvenements.isEmpty());
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
