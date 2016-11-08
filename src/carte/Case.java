package carte;

public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	
	
	public Case(int l, int c, NatureTerrain n){
		ligne = l;
		colonne = c;
		nature = n;
	}
	
	public int getLigne(){
		return ligne;
	}
	
	public int getColonne(){
		return colonne;
	}
	
	public NatureTerrain getNatureTerrain(){
		return nature;
	}
	
	public String toString(){
		return ("("+ligne+","+colonne+")");
	}
	
	public void setNatureTerrain(NatureTerrain n){
		nature = n;
	}
	
	/**
     * Retourne la direction de la case passée en paramètre par rapport à la case courante.
     * @param p 	la case voisine à la case courante
     * @return		NORD ou SUD ou OUEST ou EST
     */
    public Direction getDirection(Case p) {
        int dif_ligne = p.getLigne() - this.getLigne();
        int dif_colonne = p.getColonne() - this.getColonne();
        if(Math.abs(dif_ligne) > 1 || Math.abs(dif_colonne) > 1)
        	throw new IllegalArgumentException("La case n'est pas voisine. Impossible de retourner la direction.");
        return Direction.getDir(dif_ligne, dif_colonne);
    }
	
}