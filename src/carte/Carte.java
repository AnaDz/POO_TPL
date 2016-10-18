package carte;

public class Carte {
	private int tailleCases;
	private int nbLignes;
	private int nbColonnes;
	private Case carte[][];
	
	public Carte(int nbLignes, int nbColonnes, int tailleCases){
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCases = tailleCases;
		carte = new Case[nbLignes][nbColonnes];
	}
	
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
	
	public void setCase(int lig, int col, Case c){
		carte[lig][col] = c;
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







