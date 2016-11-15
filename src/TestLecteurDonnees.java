
import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import donneesSimulation.DonneesSimulation;
import carte.*;
import robots.*;

public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);
            //TEST
            //Affichage de la carte
            for (int i = 0; i < data.getCarte().getNbLignes(); i++) {
                for (int j = 0; j < data.getCarte().getNbColonnes(); j++) {
                    System.out.print("\t");
                    System.out.print(data.getCarte().getCase(i, j).getNatureTerrain().toString().substring(0, 1));
                }
                System.out.print("\n");
            }
            //Affichage de la liste des incendies

            for (Incendie it : data.getListeIncendies()) {
                System.out.println("Un incendie d'intensité " + it.getNbLitres() + " en (" + it.getCaseIncendie().getLigne() + "," + it.getCaseIncendie().getColonne() + ") détecté.");
            }

            //Affichage de la liste des robots
            for (Robot it : data.getListeRobots()) {
                System.out.println("Un robot " + it.getClass() + " détecté en (" + it.getPosition().getLigne() + "," + it.getPosition().getColonne() + ").");
            }
            //FIN TEST
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

    }
}
