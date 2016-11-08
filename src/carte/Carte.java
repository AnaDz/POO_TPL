package carte;

import java.util.ArrayList;

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
		Case res = null;
		try {
			res = carte[lig][col];
		} catch(ArrayIndexOutOfBoundsException ep) {
			System.out.println("Vous essayez d'accéder à la case ("+lig+","+col+") qui n'existe pas.\nArrêt de la simulation");
			System.exit(0);
		}
		return res;
	}
	
	public void setCase(int lig, int col, Case c){
		try {
			carte[lig][col] = c;
		} catch(ArrayIndexOutOfBoundsException ep) {
			System.out.println("Vous essayez de modifier la case ("+lig+","+col+") qui n'existe pas.\nArrêt de la simulation");
			System.exit(0);
		}
	}
	
	public boolean voisinExiste(Case src, Direction dir){
		int newX = src.getLigne()+dir.getX();
		int newY = src.getColonne()+dir.getY();
		return (newX < getNbLignes() && newX >=0 && newY < getNbColonnes() && newY >= 0);
	}
	
	public Case getVoisin(Case src, Direction dir){
		if(voisinExiste(src,dir))
			return (getCase(src.getLigne()+dir.getX(),src.getColonne()+dir.getY()));
		else
			return null;
	}
	
	public ArrayList<Case> getAllVoisins(Case src){
		 ArrayList<Case> res = new ArrayList<Case>();
		 Direction [] allDir = Direction.values();
		 
		 for(int i = 0; i < allDir.length; i++){
			 if(voisinExiste(src, allDir[i])) {
				 res.add(getCase(src.getLigne()+allDir[i].getX(),src.getColonne()+allDir[i].getY()));
			 }
		 }
		 return res;
	}
}







