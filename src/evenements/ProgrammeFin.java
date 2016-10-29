package evenements;

public class ProgrammeFin extends Evenement{
	//Ajoute un evenement fictif pour pousser la simulation jusqu'à programmeFin.date
	//Histoire de laisser le temps aux robots de faire leur dernier déplacement.
	
	public ProgrammeFin(long date){
		super(date);
	}

	public void execute(){
		
	}
	
	public String toString(){
		return super.toString()+ " : Fin du simulateur\n";
	}
	

}
