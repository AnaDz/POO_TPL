package strategie;
import java.util.*;
import robots.Robot;
import carte.*;
import java.util.PriorityQueue;

public class AstarPlusCourtChemin {
	
	/** La carte dans laquelle les robots se déplacent **/
	private Carte carte;
	Robot rob;
	Case dep;
	Case fin;
	
	/** Le tableau des couts **/
	double [][][] couts;
	
	public AstarPlusCourtChemin(Carte carte, Robot rob, Case dep, Case fin) {
		this.carte = carte;
		this.rob = rob;
		this.dep = dep;
		this.fin = fin;
		this.couts =  new double [carte.getNbLignes()][carte.getNbColonnes()][5];
		/** Explication
		 * g := cout[i][j][0] est la distance par rapport à la source
		 * h := cout[i][j][1] est l'heuristique par rapport à la destination
		 * f := cout[i][j][2] est la somme g+h
		 * x := cout[i][j][3] et y := cout[i][j][3] représente le parent (x,y) de (i,j) 
 		 */
	}
	
	public class NodeComparator implements Comparator<Case> {
		public int compare(Case x, Case y) {
			/*double h1 = Math.abs(fin.getLigne()-x.getLigne())+ Math.abs(fin.getColonne()-x.getColonne());
	        double h2 = Math.abs(fin.getLigne()-y.getLigne())+ Math.abs(fin.getColonne()-y.getColonne());
			double g1 = couts[x.getLigne()][x.getColonne()][0];
			double g2 = couts[y.getLigne()][y.getColonne()][0];*/
			double f1 = couts[x.getLigne()][x.getColonne()][2];
			double f2 = couts[y.getLigne()][y.getColonne()][2];
	        if(f1 < f2) {
	       	 return -1;
	        } else if (f1 == f2) {
	       	 return 0;
	        } else {
	       	 return 1;
	        }
		}
	}
	
	public List<Case> trouveChemin(){
		/** L'ensemble des noeuds qui restent à explorer **/
		PriorityQueue<Case> openQueue = new PriorityQueue<Case> (10, new NodeComparator());
		/** L'ensemble des noeuds qui ont été exploré **/
		Set<Case> closedList = new HashSet<Case>();
		/** La variable destiné à contenir le chemin **/
	    Map<Case, Case> path = new HashMap<Case, Case>();
		
	    /** INITIALISATION **/
	    for(int i = 0; i < carte.getNbLignes(); i++) {
	    	for(int j = 0; j < carte.getNbColonnes(); j++){
	    		couts[i][j][0] = Double.POSITIVE_INFINITY;
	    		couts[i][j][1] = Double.POSITIVE_INFINITY;
 	    	}
	    }
	    /** Algorithme A* **/
	    couts[dep.getLigne()][dep.getColonne()][0] = 0;
	    couts[dep.getLigne()][dep.getColonne()][2] = 0 + Math.abs(fin.getLigne()-dep.getLigne())+ Math.abs(fin.getColonne()-dep.getColonne());
		openQueue.add(dep);
		while(!openQueue.isEmpty()) {
			Case u = openQueue.poll(); //on dépile u
			if(u == fin) {
				return path(path, fin);
			}
			closedList.add(u);
			ArrayList<Case> voisin = carte.getAllVoisins(u);
			/*for(Case c : voisin) {
				System.out.println(c.toString());
			}
			System.out.println("\n");*/
			
			for(Case v : voisin) {
				//On ignore les voisins déjà évalués et les cases où le robot ne peut pas aller
				double vitesse = rob.getVitesse(v.getNatureTerrain());
				if(vitesse == 0 || closedList.contains(v)) continue; 
				
				//La distance du début jusqu'au voisin.
				double distanceBetweenUandV = rob.getDureeDeplacement(0.5, u);
				double tentativeG = distanceBetweenUandV + couts[u.getLigne()][u.getColonne()][0];
				
				if(!openQueue.contains(v))
					openQueue.add(v);
				else if (tentativeG >= couts[v.getLigne()][v.getColonne()][0])
					continue; //ce n'est pas un meilleur chemin
				
				//C'est le meilleur chemin jusqu'à présent. On le sauvegarde
				//On modifie le f de v
				//On est donc forcés de le remove, pour pouvoir l'add ensuite
				//afin de conserver la propriété de la priorityQueue
				openQueue.remove(v);
				couts[v.getLigne()][v.getColonne()][0] = tentativeG;
				couts[v.getLigne()][v.getColonne()][2] = tentativeG + Math.abs(fin.getLigne()-v.getLigne())+ Math.abs(fin.getColonne()-v.getColonne());
				openQueue.add(v);
				
				//for(Case i : openQueue) {
				//	System.out.println(i.toString()+" f = "+couts[i.getLigne()][i.getColonne()][2]);
				//}
				Case tete = openQueue.peek();
				//System.out.println("La tete : "+tete.toString()+ " f = "+couts[tete.getLigne()][tete.getColonne()][2]);
				//System.out.println("\n");
				path.put(v, u);
				
			}
		}
		return null;
	}
	
	private List<Case> path (Map<Case,Case> path, Case destination) {
		assert path != null;
        assert destination != null;

        List<Case> pathList = new ArrayList<Case>();
        pathList.add(destination);
        while (path.containsKey(destination)) {
            destination = path.get(destination);
            pathList.add(destination);
        }
        Collections.reverse(pathList);
        return pathList;
	}
	
}

