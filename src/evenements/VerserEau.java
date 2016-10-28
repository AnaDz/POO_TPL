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
		rob.deverserEau(quantiteEau);
	}
}
