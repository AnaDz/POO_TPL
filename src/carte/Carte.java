package carte;

public class Carte {
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







