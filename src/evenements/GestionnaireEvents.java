package evenements;

import java.util.Iterator;
import java.util.PriorityQueue;


public class GestionnaireEvents {

	//Date courante de la simulation
	private long dateSimulation;
	
	//File à priorité contenant des évènements;
	PriorityQueue<Evenement> listeEvenements;
	
	public GestionnaireEvents() {
		dateSimulation = 0;
		listeEvenements = new PriorityQueue<Evenement>();
	}
	
	public void ajouteEvenement(Evenement e){
		listeEvenements.add(e);
	}
	
	public void incrementeDate(){
		dateSimulation += 1;
		
		Iterator<Evenement> it = listeEvenements.iterator();
		while(it.hasNext()){
			Evenement e = it.next();
			if(e.getDate() <= dateSimulation)
				e.execute();
			else 
				break;
		}
	}
	
	public PriorityQueue<Evenement> getListeEvenement(){
		return listeEvenements;
	}
	

}
