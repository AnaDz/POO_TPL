package carte;

public enum Direction {

    /**
     * Les 4 directions seront décrites pas les points cardinaux
     */
    NORD(new int[]{-1, 0}), SUD(new int[]{1, 0}), EST(new int[]{0, 1}), OUEST(new int[]{0, -1});

    private int dir[] = new int[2];

    Direction(int[] dir) {
        this.dir[0] = dir[0];
        this.dir[1] = dir[1];
    }

    /*
     * ***************************
     * ACCESSEURS 
     * ***************************
     */
    /**
     * Retourne la valeur suivant l'axe x du point cardinal correspondant a la
     * direction
     *
     * @return -1 ou 0 ou 1
     */
    public int getX() {
        return dir[0];
    }

    /**
     * Retourne la valeur suivant l'axe y du point cardinal correspondant a la
     * direction
     *
     * @return -1 ou 0 ou 1
     */
    public int getY() {
        return dir[1];
    }

    /**
     * Retourne la direction correspondante au point cardinal
     *
     * @param x position sur l'axe x
     * @param y position sur l'axe y
     * @return NORD ou SUD ou EST ou OUEST ou null si le point ne correspond à
     * aucune direction
     */
    static public Direction getDir(int x, int y) {
        switch (x) {
            case 0:
                switch (y) {
                    case 1:
                        return EST;
                    case -1:
                        return OUEST;
                    default:
                        return null;
                }
            case -1:
                switch (y) {
                    case 0:
                        return NORD;
                    default:
                        return null;
                }
            case 1:
                switch (y) {
                    case 0:
                        return SUD;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}
