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
	
}