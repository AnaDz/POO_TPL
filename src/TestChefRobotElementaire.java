import donneesSimulation.DonneesSimulation;
import evenements.Evenement;
import evenements.GestionnaireEvents;
import evenements.ProgrammeFin;
import gui.GUISimulator;
import interfacegraphique.Simulateur;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import robots.Robot;
import strategie.ChefRobotElementaire;


public class TestChefRobotElementaire {
	public static void main(String[] args) {
		if (args.length < 1) {
            System.out.println("Syntaxe: java TestSimulateur <nomDeFichier>");
            System.exit(1);
        }

        try {
        	//Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);
            Robot.setDS(data);
            GestionnaireEvents GE = new GestionnaireEvents();
          
            
            ChefRobotElementaire chef = new ChefRobotElementaire(data, GE);
            chef.phaseAssignement(0);
            Evenement e = new ProgrammeFin(10000);
            GE.ajouteEvenement(e);
            
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

