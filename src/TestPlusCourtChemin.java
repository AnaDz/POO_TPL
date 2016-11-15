
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.List;
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
 * Ce fichier teste l'algorithme de plus court chemin. Il est a appeler avec
 * cartes/cartePlusCourtChemin.map
 */
public class TestPlusCourtChemin {

    public static void main(String[] args) {
       String nomCarte = "cartes/cartePlusCourtChemin.map";

        try {
            //Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(nomCarte, false);
            Robot.setDS(data);
            GestionnaireEvents gE = new GestionnaireEvents();
            //Calcul des plus courts chemins
            ChefRobotElementaire chef = new ChefRobotElementaire(data, gE, 0);
            Carte carte = data.getCarte();
            Robot rob = data.getListeRobots().get(0);
            Case deb = rob.getPosition();
            Case fin = carte.getCase(7, 3);
            List<Case> chemin = AStar.trouveChemin(data.getCarte(), rob, deb, fin, gE.getPasDeTemps());
            int[][][] couts = AStar.getCouts();
            chef.bougeRobot(rob, chemin, couts, 0);
            
            
            rob = data.getListeRobots().get(1);
            deb = rob.getPosition();
            chemin = AStar.trouveChemin(data.getCarte(), rob, deb, fin, gE.getPasDeTemps());
            couts = AStar.getCouts();
            chef.bougeRobot(rob, chemin, couts, 0);
            
            rob = data.getListeRobots().get(2);
            deb = rob.getPosition();
            chemin = AStar.trouveChemin(data.getCarte(), rob, deb, fin, gE.getPasDeTemps());
            couts = AStar.getCouts();
            chef.bougeRobot(rob, chemin, couts, 0);
            
            rob = data.getListeRobots().get(3);
            deb = rob.getPosition();
            chemin = AStar.trouveChemin(data.getCarte(), rob, deb, fin, gE.getPasDeTemps());
            couts = AStar.getCouts();
            chef.bougeRobot(rob, chemin, couts, 0);
            
            
            //Lancement de la simulation
            Evenement e = new ProgrammeFin(700);
            gE.ajouteEvenement(e);
            int dimFenX = 500;
            int dimFenY = 500;
            GUISimulator gui = new GUISimulator(dimFenX, dimFenY, Color.BLACK);
            Simulateur sim = new Simulateur(gui, data, gE);

        } catch (FileNotFoundException e) {
            System.out.println("fichier "+nomCarte+" inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier "+nomCarte+" invalide: " + e.getMessage());
        }

    }

}
