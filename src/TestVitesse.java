
import carte.*;
import donneesSimulation.DonneesSimulation;
import evenements.DeplaceRobot;
import evenements.Evenement;
import evenements.GestionnaireEvents;
import evenements.ProgrammeFin;
import exceptions.ErreurPosition;
import gui.GUISimulator;
import interfacegraphique.Simulateur;
import io.LecteurDonnees;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestVitesse {

    public static void main(String[] args) throws ErreurPosition {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestSimulateur <nomDeFichier>");
            System.exit(1);
        }

        try {
        	//Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);
            
            GestionnaireEvents GE = new GestionnaireEvents();
            
            int [] nbiterations = {0, 0, 0, 0}; //Tableau servant à stocker le nombre d'itérations pour chaque robot.
            Case [] caseCourante = {data.getListeRobots().get(0).getPosition(), data.getListeRobots().get(1).getPosition(), data.getListeRobots().get(2).getPosition(), data.getListeRobots().get(3).getPosition()}; 
            
            Evenement e;
            for(int i = 0; i < data.getCarte().getNbColonnes() - 1; i++) {
                for(int j = 0; j < data.getListeRobots().size(); j++) {
                	e = new DeplaceRobot(nbiterations[j], data.getListeRobots().get(j), Direction.EST);
                	GE.ajouteEvenement(e);
                	nbiterations[j] += data.getListeRobots().get(j).getDureeDeplacement(GE.getPasDeTemps(), caseCourante[j]);
                	caseCourante[j] = data.getCarte().getVoisin(caseCourante[j], Direction.EST);
                }
            }
            
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
    
