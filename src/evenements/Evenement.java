package evenements;

public abstract class Evenement implements Comparable<Evenement> {

	private long date;
	
	public Evenement(long date){
		this.date = date;
	}
	
	public long getDate(){
		return date;
	}
	
	public abstract void execute();
	
	@Override
	public String toString(){
		return "Evenement de date = "+date;
	}
	
	//On réalise l'interface Comparable<E> pour pouvoir insérer les evenements dans une file à priorités.
	public int compareTo(Evenement e){
		if (this.date < e.getDate())
			return -1;
		else if (this.date == e.getDate())
			return 0;
		else
			return 1;
	}
}
