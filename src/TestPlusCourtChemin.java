import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import carte.Direction;
import gui.GUISimulator;
import interfacegraphique.Simulateur;
import io.LecteurDonnees;
import robots.Robot;
import donneesSimulation.DonneesSimulation;
import evenements.DeplaceRobot;
import evenements.Evenement;
import evenements.GestionnaireEvents;
import evenements.ProgrammeFin;
import carte.*;
import strategie.*;

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
            Carte carte = data.getCarte();
            Robot rob = data.getListeRobots().get(0);
            Case deb = rob.getPosition();
            Case fin = carte.getCase(carte.getNbLignes()-1, 0);
            AstarPlusCourtChemin ASPCC = new AstarPlusCourtChemin(carte, rob, deb, fin);
            System.out.println(ASPCC.trouveChemin());
     
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

	}

}
