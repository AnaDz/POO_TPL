
import carte.Direction;
import donneesSimulation.DonneesSimulation;
import evenements.DeplaceRobot;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author skip
 */
public class TestScenarioErreurPosition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            //Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire("cartes/carteSujet.map", false);

            GestionnaireEvents GE = new GestionnaireEvents();

            Robot rob = data.getListeRobots().get(1);

            Evenement e1 = new DeplaceRobot(1, rob, Direction.OUEST);
            Evenement e2 = new DeplaceRobot(rob.getDureeDeplacement(GE.getPasDeTemps(), rob.getPosition()) + 1, rob, Direction.OUEST);
            GE.ajouteEvenement(e1);
            GE.ajouteEvenement(e2);

            Evenement e13 = new ProgrammeFin(1000);

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
