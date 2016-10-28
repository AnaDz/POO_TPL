package evenements;
import robots.*;
import carte.Case;

public class DeplaceRobot extends Evenement {

	private Robot rob;
	private Case dest;
	
	public DeplaceRobot(long date, Robot rob, Case dest){
		super(date);
		this.rob = rob;
		this.dest = dest;
	}
	
	public void execute(){
		rob.setPosition(dest);
	}
}
