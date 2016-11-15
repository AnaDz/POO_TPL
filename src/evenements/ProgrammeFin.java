package evenements;

public class ProgrammeFin extends Evenement {
    //Ajoute un evenement fictif pour pousser la simulation jusqu'à programmeFin.date
    //Histoire de laisser le temps aux robots de faire leur dernier déplacement.

    /**
     * Création de l'événement fin de simulation
     *
     * @param date date de la fin de la simulation
     */
    public ProgrammeFin(long date) {
        super(date);
    }

    /**
     * Exécution de l'événement fin de simulation (ne fait rien)
     */
    @Override
    public void execute() {

    }

    /**
     * méthode toString()
     *
     * @return un petit message pour dire que la simulation est finie
     */
    @Override
    public String toString() {
        return super.toString() + " : Fin du simulateur\n";
    }

}
