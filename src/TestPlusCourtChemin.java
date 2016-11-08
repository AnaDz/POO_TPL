import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Case;
import gui.GUISimulator;
import interfacegraphique.Simulateur;
import io.LecteurDonnees;
import robots.Robot;
import donneesSimulation.DonneesSimulation;
import evenements.*;
import carte.*;
import strategie.*;
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
            GestionnaireEvents gE = new GestionnaireEvents();
            //Calcul des plus courts chemins
            ChefRobot chef = new ChefRobot(data, gE);
            Carte carte = data.getCarte();
            Robot rob = data.getListeRobots().get(0);
            Case deb = rob.getPosition();
            Case fin = carte.getCase(7, 3);
            chef.bougeRobot(rob, deb, fin, 0);
            
            
            rob = data.getListeRobots().get(1);
            deb = rob.getPosition();
            chef.bougeRobot(rob, deb, fin, 0);
            
            rob = data.getListeRobots().get(2);
            deb = rob.getPosition();
            chef.bougeRobot(rob, deb, fin, 0);
            
            rob = data.getListeRobots().get(3);
            deb = rob.getPosition();
            chef.bougeRobot(rob, deb, fin, 0);
            
            
            //Lancement de la simulation
            Evenement e = new ProgrammeFin(700);
            gE.ajouteEvenement(e);
            int dimFenX = 500;
            int dimFenY = 500;
            GUISimulator gui = new GUISimulator(dimFenX, dimFenY, Color.BLACK);
            Simulateur sim = new Simulateur(gui, data, gE);
     
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

	}

}
