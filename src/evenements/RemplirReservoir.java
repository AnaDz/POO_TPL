package evenements;
import robots.*;

public class RemplirReservoir extends Evenement{
	
	private Robot rob;
	
	public RemplirReservoir(long date, Robot rob){
		super(date);
		this.rob = rob;
	}
	
	public void execute(){
		rob.remplirReservoir();
	}
	

}
