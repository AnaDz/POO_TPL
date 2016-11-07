import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.DataFormatException;

import carte.Case;
import carte.Direction;
import gui.GUISimulator;
import interfacegraphique.Simulateur;
import io.LecteurDonnees;
import robots.Robot;
import donneesSimulation.DonneesSimulation;
import evenements.*;
import carte.*;
import strategie.*;
import java.util.*;
/**
 * Ce fichier teste l'algorithme de plus court chemin. Il est a appeler avec cartes/cartePlusCourtChemin.map
 */
public class TestPlusCourtChemin {

	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestSimulateur <nomDeFichier>");
            System.exit(1);
        }

        try {
        	//Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);
            Robot.setDS(data);
            
            //Calcul des plus courts chemins
            Carte carte = data.getCarte();
            Robot rob = data.getListeRobots().get(0);
            Case deb = rob.getPosition();
            Case fin = carte.getCase(7, 3);
            List<Case> C0 = AStar.trouveChemin(carte, rob, deb, fin);
            System.out.println("Chemin du robot 0 : "+C0);
            
            rob = data.getListeRobots().get(1);
            deb = rob.getPosition();
            List<Case> C1 = AStar.trouveChemin(carte, rob, deb, fin);
            System.out.println("Chemin du robot 1 : "+C1);
            
            rob = data.getListeRobots().get(2);
            deb = rob.getPosition();
            List<Case> C2 = AStar.trouveChemin(carte, rob, deb, fin);
            System.out.println("Chemin du robot 2 : "+C2);
            
            rob = data.getListeRobots().get(3);
            deb = rob.getPosition();
            List<Case> C3 = AStar.trouveChemin(carte, rob, deb, fin);
            System.out.println("Chemin du robot 3 : "+C3);
            
            //TO DO
            //Traduire ça en successions de déplacements. Les robots doivent tous arriver à la case (7,3)
            GestionnaireEvents GE = new GestionnaireEvents();
            
            //Lancement de la simulation
            int dimFenX = 500;
            int dimFenY = 500;
            GUISimulator gui = new GUISimulator(dimFenX, dimFenY, Color.BLACK);
            Simulateur sim = new Simulateur(gui, data, GE);
     
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

	}

}
