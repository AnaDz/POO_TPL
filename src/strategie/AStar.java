package strategie;

import java.util.*;
import robots.Robot;
import carte.*;
import java.util.PriorityQueue;

/**
 * Cette classe statique implémente l'algorithme de A* de recherche de plus
 * court chemin. Vous trouverez une très bonne description de l'algorithme sur
 * la page anglophone associée de Wikipédia :
 * https://en.wikipedia.org/wiki/A*_search_algorithm
 */
public class AStar {

    /**
     * Le tableau de f et h Voir description de l'algorithme de A*
     */
    private static int[][][] couts;

    /**
     * Cette fonction trouve le plus court chemin grâce à l'algorithme de A*.
     *
     * @param carte	la carte dans laquelle le robot évolue
     * @param rob	le robot qui doit se déplacer
     * @param deb	la case d'où part le robot
     * @param fin	la case où doit arriver le robot
     * @param h		le pas de temps définit dans le gestionnaire d'événements
     * @return		le plus court chemin de deb à fin
     */
    public static List<Case> trouveChemin(Carte carte, Robot rob, Case deb, Case fin, double h) {
        /**
         * L'ensemble des noeuds qui restent à explorer *
         */
        PriorityQueue<Case> openQueue = new PriorityQueue<Case>(10, new NodeComparator());
        /**
         * L'ensemble des noeuds qui ont été exploré *
         */
        Set<Case> closedList = new HashSet<Case>();
        /**
         * La variable destiné à contenir le chemin *
         */
        Map<Case, Case> path = new HashMap<Case, Case>();

        /**
         * INITIALISATION *
         */
        couts = new int[carte.getNbLignes()][carte.getNbColonnes()][2];
        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                couts[i][j][0] = Integer.MAX_VALUE;
            }
        }
        /**
         * Algorithme A* *
         */
        couts[deb.getLigne()][deb.getColonne()][0] = 0;
        couts[deb.getLigne()][deb.getColonne()][1] = heuristique(deb, fin);
        openQueue.add(deb);
        while (!openQueue.isEmpty()) {
            Case u = openQueue.poll(); //on dépile u, on obtient l'élément ayant le plus bas score de f
            if (u == fin) {
                return path(path, fin);
            }
            closedList.add(u);
            ArrayList<Case> voisin = carte.getAllVoisins(u);

            for (Case v : voisin) {
                //On ignore les voisins déjà évalués et les cases où le robot ne peut pas aller
                double vitesse = rob.getVitesse(v.getNatureTerrain());
                if (vitesse == 0 || closedList.contains(v)) {
                	//System.out.println("vitesse =" +rob.getVitesse(v.getNatureTerrain())+" en "+v);
                    continue;
                }
                
                //La distance du début jusqu'au voisin.
                int distanceBetweenUandV = rob.getDureeDeplacement(h, u);
                int tentativeG = distanceBetweenUandV + couts[u.getLigne()][u.getColonne()][0];

                if (!openQueue.contains(v)) {
                    openQueue.add(v);
                } else if (tentativeG >= couts[v.getLigne()][v.getColonne()][0]) {
                    continue; //ce n'est pas un meilleur chemin
                }
                //C'est le meilleur chemin jusqu'à présent. On le sauvegarde
                //On modifie le f de v
                //On est donc forcés de le remove, pour pouvoir l'add ensuite
                //afin de conserver la propriété de la priorityQueue
                openQueue.remove(v);
                couts[v.getLigne()][v.getColonne()][0] = tentativeG;
                couts[v.getLigne()][v.getColonne()][1] = tentativeG + heuristique(v, fin);
                openQueue.add(v);

                path.put(v, u);

            }
        }
        return null;
    }

    /**
     * Calcule l'heuristique par rapport à la destination utilisée dans
     * l'algorithme de A*. Ici on utilise la distance de Manhattan en guise
     * d'heuristique.
     *
     * @param x	le noeud dont on doit calculer l'heuristique
     * @param fin	le noeud destination
     * @return	l'heuristique associé au noeud x
     */
    private static int heuristique(Case x, Case fin) {
        return Math.abs(fin.getLigne() - x.getLigne()) + Math.abs(fin.getColonne() - x.getColonne());
    }

    /**
     * Cette fonction retourne le chemin que le robot doit parcourir en
     * remontant la liste retournée par A*.
     *
     * @param path	le chemin retourné par la fonction trouveChemin
     * @param destination	la destination finale du robot, qui est aussi la
     * dernière case visité par l'algorithme de A
     *
     * @return	la suite des cases que le chemin doit parcourir de la position
     * deb à la position fin
     */
    private static List<Case> path(Map<Case, Case> path, Case destination) {
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

    /**
     * Cette fonction retourne le tableau des couts utilisés dans l'algorithme
     * de A*.
     */
    public static int[][][] getCouts() {
        return couts;
    }

    /**
     * Cette fonction implémente l'interface Comparator<Case> dans le but
     * d'utiliser une PriorityQueue sur les cases. Cette PriorityQueue trie les
     * cases en fonction de leur cout f = g + h (:= la distance +
     * l'heuristique).
     */
    private static class NodeComparator implements Comparator<Case> {

        public int compare(Case x, Case y) {
            double f1 = couts[x.getLigne()][x.getColonne()][1];
            double f2 = couts[y.getLigne()][y.getColonne()][1];
            if (f1 < f2) {
                return -1;
            } else if (f1 == f2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

}
