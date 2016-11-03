
import carte.Direction;
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
import robots.Robot;

public class TestVitesse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ErreurPosition {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestSimulateur <nomDeFichier>");
            System.exit(1);
        }

        try {
        	//Lecture des Données
            DonneesSimulation data = LecteurDonnees.lire(args[0], false);
            
            int dimFenX = 500;
            int dimFenY = 500;
            GUISimulator gui = new GUISimulator(dimFenX, dimFenY, Color.BLACK);
            Simulateur sim = new Simulateur(gui, data, null);
            GestionnaireEvents GE = new GestionnaireEvents();   
            Robot rob1 = data.getListeRobots().get(0);
            Robot rob2 = data.getListeRobots().get(1);
            Robot rob3 = data.getListeRobots().get(2);
            Robot rob4 = data.getListeRobots().get(3);
            
            Evenement e1 = new DeplaceRobot(1, rob1, Direction.EST);
            Evenement e2 = new DeplaceRobot(1, rob2, Direction.EST);
            Evenement e3 = new DeplaceRobot(1, rob3, Direction.EST);
            Evenement e4 = new DeplaceRobot(1, rob4, Direction.EST);

            GE.ajouteEvenement(e1);
            GE.ajouteEvenement(e2);
            GE.ajouteEvenement(e3);
            GE.ajouteEvenement(e4);
            
            Evenement e5;
            Evenement e6;
            Evenement e7;
            Evenement e8;
            
            for (int i =1 ; i<=8 ; i++) {
            e5 = new DeplaceRobot(i*rob1.getDureeDeplacement(data, rob1, sim, GE), rob1, Direction.EST);
            e6 = new DeplaceRobot(i*rob2.getDureeDeplacement(data, rob2, sim, GE) , rob2, Direction.EST);
            e7 = new DeplaceRobot(i*rob3.getDureeDeplacement(data, rob3, sim, GE), rob3, Direction.EST);
            e8 = new DeplaceRobot(i*rob4.getDureeDeplacement(data, rob4, sim, GE), rob4, Direction.EST);

            GE.ajouteEvenement(e5);
            GE.ajouteEvenement(e6);
            GE.ajouteEvenement(e7);
            GE.ajouteEvenement(e8);  
            }
            
            


            Evenement e13 = new ProgrammeFin(1000);


            GE.ajouteEvenement(e13);
            
            //Lancement de la simulation
           
            sim = new Simulateur(gui, data, GE);
            
            
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
    }
}
    
