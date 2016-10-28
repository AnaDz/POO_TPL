
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
            
            //Initialisation
            int dimFenX = 500;
            int dimFenY = 500;
            GUISimulator gui = new GUISimulator(dimFenX, dimFenY, Color.BLACK);
            Simulateur sim = new Simulateur(gui, data);
            
            //Appel au gestionnaire d'évenements
            Robot rob = data.getListeRobots().get(0);
            Case dest = data.getCarte().getCase(rob.getPosition().getLigne(), rob.getPosition().getColonne()+1);
            Evenement e1 = new DeplaceRobot(1, rob, dest);
            Evenement e2 = new DeplaceRobot(2, rob, dest);
            Evenement e3 = new DeplaceRobot(3, rob, dest);
            
            GestionnaireEvents GE = new GestionnaireEvents();
            GE.ajouteEvenement(e1);
            GE.ajouteEvenement(e2);
            GE.ajouteEvenement(e3);
            
            GE.incrementeDate();
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
    }
}

