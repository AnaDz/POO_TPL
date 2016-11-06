package strategie;
import carte.Case;
import java.util.Comparator;

public class Heuristique implements Comparator<Case> {
	 
	private Case fin;
	
	public Heuristique(Case fin){
		this.fin = fin;
	}
	
	@Override
    public int compare(Case x, Case y) {
         int h1 = Math.abs(fin.getLigne()-x.getLigne())+ Math.abs(fin.getColonne()-x.getColonne());
         int h2 = Math.abs(fin.getLigne()-y.getLigne())+ Math.abs(fin.getColonne()-y.getColonne());
         if(h1 < h2) {
        	 return 1;
         } else if (h1 == h2) {
        	 return 0;
         } else {
        	 return -1;
         }
     }
	
}
