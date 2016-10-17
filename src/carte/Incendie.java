package carte;

public class Incendie {
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