package carte;

public class Incendie implements Cloneable{
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
        
    public void eteindre(int vol) {
        this.nbLitres -= vol;
    }
    
    @Override
    public String toString() {
    	return "Incendie en position "+caseIncendie.toString()+" d'intensité "+nbLitres;
    }
    
    @Override
    public Incendie clone() {
    	Incendie copie = null;
    	try {
    		copie = (Incendie)super.clone();
    	} catch(CloneNotSupportedException cnse) {
    		cnse.printStackTrace(System.err);
    	}
    	return copie;
    }
}