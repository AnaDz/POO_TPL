package evenements;

public abstract class Evenement implements Comparable<Evenement>, Cloneable {

    private long date;

    /*
     * *****************************
     * CONSTRUCTEURS 
     * *****************************
     */
    /**
     * Construit un évenement
     *
     * @param date de l'évènement
     */
    public Evenement(long date) {
        this.date = date;
    }

    /*
     * ***************************
     * ACCESSEURS 
     * ***************************
     */
    /**
     * Retourne la date de l'évènement
     *
     * @return date
     */
    public long getDate() {
        return date;
    }

    /**
     * Exécute l'évenement
     */
    public abstract void execute();

    @Override
    public String toString() {
        return "Evenement de date = " + date;
    }

    //On réalise l'interface Comparable<E> pour pouvoir insérer les evenements dans une file à priorités.
    /**
     * Compare deux évnement suivant leur date pour la file de priorité
     *
     * @param e evenement
     * @return -1 si l'évènement appelé est avant l'évènement en argument 1 si
     * l'évènement appelé est après l'évènement en argument 0 s'ils sont en même
     * temps
     */
    @Override
    public int compareTo(Evenement e) {
        if (this.date < e.getDate()) {
            return -1;
        } else if (this.date == e.getDate()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Réalisation de l'interface Cloneable
     *
     * @return évenement
     */
    @Override
    public Evenement clone() {
        Evenement copie = null;
        try {
            copie = (Evenement) super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return copie;
    }
}
