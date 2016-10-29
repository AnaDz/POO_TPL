package evenements;
import robots.*;
import carte.*;

public class DeplaceRobot extends Evenement {

	private Robot rob;
	private Case dest;
	
	public DeplaceRobot(long date, Robot rob, Case dest){
		super(date);
		this.rob = rob;
		this.dest = dest;
	}
	
	public void execute(){
		if(this.rob.getOccupe() == false){
			int diffX = dest.getLigne()-rob.getPosition().getLigne();
			int diffY = dest.getColonne()-rob.getPosition().getColonne();
			Direction dir = Direction.getDir(diffX, diffY);
			rob.setDirection(dir);
			rob.switchOccupe();
			rob.setPosition(dest);
		}
		else {
			System.out.println("Le robot en position "+rob.getPosition().toString()+" a ignoré l'ordre de se déplacer en "+dest.toString()+". Il est occupé.");
		}
	}
	
	public String toString(){
		return super.toString()+" : Le robot en position "+rob.getPosition().toString()+" se déplace en "+dest.toString()+"\n";
	}
}
