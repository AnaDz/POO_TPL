
import gui.GUISimulator;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.*;
import donneesSimulation.DonneesSimulation;
import interfacegraphique.Simulateur;
import evenements.*;
import robots.*;

/** Ce fichier de test sera à compléter au fur et à mesure de l'avancement de Simulateur.java **/

public class TestScenario1 {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestSimulateur <nomDeFichier>");
            System.exit(1);
        }

        try {
        	//Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);
            Robot.setDS(data);
            //Initialisation du gestionnaire d'évenements
            //Scenario 1
            //Doit lever une exception!!
            //A lever dans setPosition de robot de façon plus propre de ce qui ressort déjà
            Robot rob = data.getListeRobots().get(0);
            Evenement e1 = new DeplaceRobot(1, rob, Direction.NORD);
            Evenement e2 = new DeplaceRobot(10, rob, Direction.NORD);
            Evenement e3 = new DeplaceRobot(19, rob, Direction.NORD);
            Evenement e4 = new DeplaceRobot(28, rob, Direction.NORD);
            Evenement e5 = new ProgrammeFin(40);
            
            GestionnaireEvents GE = new GestionnaireEvents();
            GE.ajouteEvenement(e1);
            GE.ajouteEvenement(e2);
            GE.ajouteEvenement(e3);
            GE.ajouteEvenement(e4);
            GE.ajouteEvenement(e5);
            
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

