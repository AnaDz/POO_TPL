
import gui.GUISimulator;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donneesSimulation.DonneesSimulation;
import interfacegraphique.Simulateur;
import evenements.*;

public class TestSimulateur {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestSimulateur <nomDeFichier>");
            System.exit(1);
        }

        try {
            //Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);

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
