package evenements;

import robots.*;
import carte.*;

public class DeplaceRobot extends Evenement {

    private Robot rob;
    private Direction dir;

    /**
     * Cree une instance de l'évenement déplacer un robot
     *
     * @param date date à laquelle on doit le déplacer (en nombre d'itération)
     * @param rob le robot à déplacer
     * @param dir la direction
     */
    public DeplaceRobot(long date, Robot rob, Direction dir) {
        super(date);
        this.rob = rob;
        this.dir = dir;
    }

    /**
     * Méthode qui ordonne au robot de se déplacer s'il n'est pas occupé
     */
    public void execute() {
        if (rob.getAction() == Action.INNOCUPE) {
            rob.setDirection(dir);
            rob.switchAction(Action.DEPLACE);
        } else {
            System.out.println("Date = " + date + " Le robot en position " + rob.getPosition().toString() + " a ignoré l'ordre de se déplacer vers le " + dir.toString() + ". Action = " + rob.getAction());
        }
    }

    @Override
    public String toString() {
        return super.toString() + " : Le robot en position " + rob.getPosition().toString() + " se déplace vers le " + dir.toString() + "\n";
    }
}
