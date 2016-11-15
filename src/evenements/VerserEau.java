package evenements;

import robots.*;

public class VerserEau extends Evenement {

    private Robot rob;
    private int quantiteEau;

    /**
     * Création d'un événement verser de l'eau
     *
     * @param date date de l'évenement (en nombre d'itération)
     * @param rob robot qui versera de l'eau
     * @param quantiteEau la quantité d'eau versée
     */
    public VerserEau(long date, Robot rob, int quantiteEau) {
        super(date);
        this.rob = rob;
        this.quantiteEau = quantiteEau;
    }

    /**
     * Exécution de l'évenement verser de l'eau.
     */
    @Override
    public void execute() {
        if (rob.getAction() == Action.INNOCUPE && rob.peutDeverserEau(quantiteEau)) {
            rob.switchAction(Action.VERSER);
        } else {
            System.out.println("Le robot en position " + rob.getPosition().toString() + " a ignoré l'ordre de verser de l'eau.");
        }
    }
}
