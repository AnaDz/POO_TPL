package evenements;
import robots.*;

public class RemplirReservoir extends Evenement{
	
	private Robot rob;
	
	public RemplirReservoir(long date, Robot rob){
		super(date);
		this.rob = rob;
	}
	
	public void execute(){
		if(this.rob.getAction() == Action.INNOCUPE && this.rob.peutRemplirReservoir()){
			//On appelle pas rob.remplirReservoir(), on relaye ça au Simulateur qui va le remplir au fur et à mesure
			rob.switchAction(Action.REMPLIR);
		} else {
			System.out.println("Le robot en position "+rob.getPosition().toString()+" a ignoré l'ordre de remplir son réservoir. Il est occupé ou ne peut pas remplir son reservoir.");
		}
	}

}
