package evenements;
import robots.*;
import carte.*;

public class DeplaceRobot extends Evenement {

	private Robot rob;
	private Direction dir;
	
	public DeplaceRobot(long date, Robot rob, Direction dir){
		super(date);
		this.rob = rob;
		this.dir = dir;
	}
	
	public void execute(){
		if(this.rob.getAction() == Action.INNOCUPE){
			rob.setDirection(dir);
			rob.switchAction(Action.DEPLACE);
		}
		else {
			System.out.println("Le robot en position "+rob.getPosition().toString()+" a ignoré l'ordre de se déplacer vers le "+dir.toString()+". Il est occupé.");
		}
	}
	
	@Override
	public String toString(){
		return super.toString()+" : Le robot en position "+rob.getPosition().toString()+" se déplace vers le "+dir.toString()+"\n";
	}
}
