package carte;

import java.util.ArrayList;

public class Carte {

    private int tailleCases;
    private int nbLignes;
    private int nbColonnes;
    private Case carte[][];

    /*
     * *****************************
     * CONSTRUCTEURS
     * *****************************
     */
    /**
     * Construit une instance de la classe Carte
     *
     * @param nbLignes de la carte
     * @param nbColonnes de la carte
     * @param tailleCases taille d'une case dans la carte
     */
    public Carte(int nbLignes, int nbColonnes, int tailleCases) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCases = tailleCases;
        carte = new Case[nbLignes][nbColonnes];
    }

    /*
     * ******************************
     * ACCESSEURS
     * ******************************
     */
    /**
     * Retourne la taille des cases de la carte
     *
     * @return taille des cases
     */
    public int getTailleCases() {
        return tailleCases;
    }

    /**
     * Retourne le nombre de lignes de la carte
     *
     * @return nombre de lignes
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * Retourne le nombre de colonnes de la carte
     *
     * @return nombre de colonnes
     */
    public int getNbColonnes() {
        return nbColonnes;
    }

    /**
     * Retourne la case (décrite dans Case.java) située à l'emplacement
     * (lig,col)
     *
     * @param lig ligne de la case demandée
     * @param col colonne de la case demandée
     * @return la case en question
     */
    public Case getCase(int lig, int col) {
        Case res = null;
        try {
            res = carte[lig][col];
        } catch (ArrayIndexOutOfBoundsException ep) {
            System.out.println("Vous essayez d'accéder à la case (" + lig + "," + col + ") qui n'existe pas.\nArrêt de la simulation");
            System.exit(0);
        }
        return res;
    }

    /**
     * Retourne la case voisine à la case en argument (selon une direction)
     *
     * @param src Case dont on veut connaitre la case voisine
     * @param dir NORD ou SUD ou EST ou OUEST
     * @return case voisine de src selon la direction dir
     */
    public Case getVoisin(Case src, Direction dir) {
        if (voisinExiste(src, dir)) {
            return (getCase(src.getLigne() + dir.getX(), src.getColonne() + dir.getY()));
        } else {
            return null;
        }
    }

    /**
     * Retourne les voisins de la case en argurment selon les 4 directions (s'il
     * y en a)
     *
     * @param src case dont on veut obtenir les voisins
     * @return Liste de case voisine
     */
    public ArrayList<Case> getAllVoisins(Case src) {
        ArrayList<Case> res = new ArrayList<Case>();
        Direction[] allDir = Direction.values();

        for (Direction allDir1 : allDir) {
            if (voisinExiste(src, allDir1)) {
                res.add(getCase(src.getLigne() + allDir1.getX(), src.getColonne() + allDir1.getY()));
            }
        }
        return res;
    }

    /*
     * *********************************
     * MUTATEURS
     * *********************************
     */
    /**
     * Modifie la case présente à (lig,col)
     *
     * @param lig emplacement de la case (ligne)
     * @param col emplacement de la case (colonne)
     * @param c la case à placer
     */
    public void setCase(int lig, int col, Case c) {
        try {
            carte[lig][col] = c;
        } catch (ArrayIndexOutOfBoundsException ep) {
            System.out.println("Vous essayez de modifier la case (" + lig + "," + col + ") qui n'existe pas.\nArrêt de la simulation");
            System.exit(0);
        }
    }

    /**
     * Dit s'il y a où non un voisin à une case suivant une certaine direction
     *
     * @param src case dont on veut savoir le voisin
     * @param dir NORD ou SUD ou EST ou OUEST
     * @return True s'il y a un voisin, False sinon
     */
    public boolean voisinExiste(Case src, Direction dir) {
        int newX = src.getLigne() + dir.getX();
        int newY = src.getColonne() + dir.getY();
        return (newX < getNbLignes() && newX >= 0 && newY < getNbColonnes() && newY >= 0);
    }

}
