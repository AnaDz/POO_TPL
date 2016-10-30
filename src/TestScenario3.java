
import gui.GUISimulator;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.*;
import DonneesSimulation.DonneesSimulation;
import interfacegraphique.Simulateur;
import evenements.*;
import robots.*;

/** Ce fichier de test sera à compléter au fur et à mesure de l'avancement de Simulateur.java **/

public class TestScenario3{

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestSimulateur <nomDeFichier>");
            System.exit(1);
        }

        try {
        	//Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);
            
            //Initialisation du gestionnaire d'évenements
            //Scenario 1
            //Doit lever une exception!!
            //A lever dans setPosition de robot de façon plus propre de ce qui ressort déjà
            Robot rob1 = data.getListeRobots().get(0);
            Robot rob2 = data.getListeRobots().get(1);
            Robot rob3 = data.getListeRobots().get(2);
            
            Evenement e1 = new DeplaceRobot(1, rob1, Direction.OUEST);
            Evenement e2 = new DeplaceRobot(10, rob1, Direction.NORD);
            Evenement e3 = new DeplaceRobot(19, rob1, Direction.EST);
            Evenement e4 = new DeplaceRobot(28, rob1, Direction.SUD);
            
            Evenement e5 = new DeplaceRobot(1, rob2, Direction.OUEST);
            Evenement e6 = new DeplaceRobot(20, rob2, Direction.NORD);
            Evenement e7 = new DeplaceRobot(40, rob2, Direction.EST);
            Evenement e8 = new DeplaceRobot(60, rob2, Direction.SUD);
            
            Evenement e9 = new DeplaceRobot(1, rob3, Direction.OUEST);
            Evenement e10 = new DeplaceRobot(100, rob3, Direction.NORD);
            Evenement e11 = new DeplaceRobot(200, rob3, Direction.EST);
            Evenement e12 = new DeplaceRobot(300, rob3, Direction.SUD);
            
            Evenement e13 = new ProgrammeFin(400);
            GestionnaireEvents GE = new GestionnaireEvents();
            GE.ajouteEvenement(e1);
            GE.ajouteEvenement(e2);
            GE.ajouteEvenement(e3);
            GE.ajouteEvenement(e4);
            GE.ajouteEvenement(e5);
            GE.ajouteEvenement(e6);
            GE.ajouteEvenement(e7);
            GE.ajouteEvenement(e8);
            GE.ajouteEvenement(e8);
            GE.ajouteEvenement(e9);
            GE.ajouteEvenement(e10);
            GE.ajouteEvenement(e11);
            GE.ajouteEvenement(e12);
            GE.ajouteEvenement(e13);
            
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

