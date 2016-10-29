
import gui.GUISimulator;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Case;
import DonneesSimulation.DonneesSimulation;
import interfacegraphique.Simulateur;
import evenements.*;
import robots.*;

/** Ce fichier de test sera à compléter au fur et à mesure de l'avancement de Simulateur.java **/

public class TestSimulateur {

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
            Robot rob = data.getListeRobots().get(0);
            Case dest = data.getCarte().getCase(rob.getPosition().getLigne()-1, rob.getPosition().getColonne());
            Evenement e1 = new DeplaceRobot(1, rob, dest);
            Case dest2 = data.getCarte().getCase(rob.getPosition().getLigne()-2, rob.getPosition().getColonne());
            Evenement e2 = new DeplaceRobot(10, rob, dest2);
            Case dest3 = data.getCarte().getCase(rob.getPosition().getLigne()-3, rob.getPosition().getColonne());
            Evenement e3 = new DeplaceRobot(20, rob, dest3);
            //Case dest4 = data.getCarte().getCase(rob.getPosition().getLigne()-4, rob.getPosition().getColonne());
            //Evenement e4 = new DeplaceRobot(30, rob, dest4);
            Evenement e5 = new ProgrammeFin(40);
            
            GestionnaireEvents GE = new GestionnaireEvents();
            GE.ajouteEvenement(e1);
            GE.ajouteEvenement(e2);
            GE.ajouteEvenement(e3);
            //GE.ajouteEvenement(e4);
            GE.ajouteEvenement(e5);
            //Scenario 2
            
            /*Robot rob = data.getListeRobots().get(1);
            Case dest = data.getCarte().getCase(rob.getPosition().getLigne()-1, rob.getPosition().getColonne());
            Evenement e1 = new DeplaceRobot(1, rob, dest);
            Evenement e2 = new DeplaceRobot(2, rob, dest);
            Evenement e3 = new DeplaceRobot(3, rob, dest);
            Evenement e4 = new DeplaceRobot(4, rob, dest);
            
            GestionnaireEvents GE = new GestionnaireEvents();
            GE.ajouteEvenement(e1);
            GE.ajouteEvenement(e2);
            GE.ajouteEvenement(e3);
            GE.ajouteEvenement(e4);*/
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

