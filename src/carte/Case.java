package carte;

public class Case {

    private int ligne;
    private int colonne;
    private NatureTerrain nature;

    /*
     * *****************************
     * CONSTRUCTEURS
     * *****************************
     */
    /**
     * Construit une instance de la classe Case
     *
     * @param l ligne de la case dans la carte
     * @param c colonne de la case dans la carte
     * @param n nature du terrain de la case
     */
    public Case(int l, int c, NatureTerrain n) {
        ligne = l;
        colonne = c;
        nature = n;
    }

    /*
     * ******************************
     * ACCESSEURS
     * ******************************
     */
    /**
     * Retourne la ligne de la case dans la carte
     *
     * @return ligne
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Retourne la colonne de la case dans la carte
     *
     * @return colonne
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Retourne la nature de la case
     *
     * @return nature
     */
    public NatureTerrain getNatureTerrain() {
        return nature;
    }

    /*
     * *********************************
     * MUTATEURS
     * *********************************
     */
    /**
     * Modifie la nature du terrain
     *
     * @param n la nature donnée
     */
    public void setNatureTerrain(NatureTerrain n) {
        nature = n;
    }

    /**
     * Retourne la direction de la case passée en paramètre par rapport à la
     * case courante.
     *
     * @param p la case voisine à la case courante
     * @return	NORD ou SUD ou OUEST ou EST
     */
    public Direction getDirection(Case p) {
        int dif_ligne = p.getLigne() - this.getLigne();
        int dif_colonne = p.getColonne() - this.getColonne();
        if (Math.abs(dif_ligne) > 1 || Math.abs(dif_colonne) > 1) {
            throw new IllegalArgumentException("La case n'est pas voisine. Impossible de retourner la direction.");
        }
        return Direction.getDir(dif_ligne, dif_colonne);
    }

    /**
     * Méthode toString
     *
     * @return retourne la case sous la forme (ligne,colonne)
     */
    @Override
    public String toString() {
        return ("(" + ligne + "," + colonne + ")");
    }

}
