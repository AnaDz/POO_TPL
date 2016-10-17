package utilities;

class Carte {
	private int tailleCases;
	private int nbLignes;
	private int nbColonnes;
	private Case carte[][];
	
	public int getTailleCases(){
		return tailleCases;
	}
	
	public int getNbLignes(){
		return nbLignes;
	}
	
	public int getNbColonnes(){
		return nbColonnes;
	}
	
	public Case getCase(int lig, int col){
		return carte[lig][col];
	}
	
	public boolean voisinExiste(Case src, Direction dir){
		return (src.getLigne()+dir.getX() < getNbLignes() && src.getColonne()+dir.getY() < getNbColonnes());
	}
	
	public Case getVoisin(Case src, Direction dir){
		if(voisinExiste(src,dir))
			return (getCase(src.getLigne()+dir.getX(),src.getColonne()+dir.getY()));
		else
			return null;
		// Ne pas oublier de lever exception
	}
}

enum NatureTerrain {
	EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT;	
}


enum Direction {
	NORD(new int[] {0,1}), SUD(new int[] {0,-1}), EST(new int[] {1,0}), OUEST(new int[] {-1,0});
	
	private int dir[] = new int[2];
	
	Direction(int[] dir){
		this.dir[0] = dir[0];
		this.dir[1] = dir[1];
	}
	
	int getX(){
		return dir[0];
	}
	
	int getY(){
		return dir[1];
	}
}

class Case {
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
	
}

class Incendie {
	private Case caseIncendie;
	private int nbLitres;
	
	public Incendie (Case c, int n){
		caseIncendie = c;
		nbLitres = n;
	}
	
	public Case getCaseIncendie(){
		return caseIncendie;
	}
	
	public int getNbLitres(){
		return nbLitres;
	}
}
