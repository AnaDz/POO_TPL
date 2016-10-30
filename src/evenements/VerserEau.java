package evenements;
import robots.*;

public class VerserEau extends Evenement{

	private Robot rob;
	private int quantiteEau;
	
	public VerserEau(long date, Robot rob, int quantiteEau){
		super(date);
		this.rob = rob;
		this.quantiteEau = quantiteEau;
	}
	
	public void execute(){
		if(this.rob.getAction() == Action.INNOCUPE && rob.peutDeverserEau(quantiteEau)){
			//Il faudrait un argument dans Robot quantiteEauAVerser pour pouvoir l'utiliser dans simulateur
			rob.switchAction(Action.VERSER);
		}
		else {
			System.out.println("Le robot en position "+rob.getPosition().toString()+" a ignoré l'ordre de verser de l'eau. Il est occupé");
		}
	}
}
