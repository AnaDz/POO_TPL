package interfacegraphique;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import java.io.IOException;
import carte.*;
import DonneesSimulation.DonneesSimulation;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
public class Simulateur implements Simulable {

    /**
     * L'interface graphique associée*
     */
    private GUISimulator gui;

    /**
     * Les données de la simulation*
     */
    private DonneesSimulation data;

    /**
     * La nouvelle taille de case : en tenant compte des dimensions de la
     * fenetre graphique *
     */
    private int tailleCases;

    /**La liste des ImageElement associées aux robots**/
    private List<ImageElement> ListeRobots;
    
    public Simulateur(GUISimulator gui, DonneesSimulation data) {
        //On instancie les attributs
        this.gui = gui;
        gui.setSimulable(this);
        this.data = data;
        Carte carte = data.getCarte();

        //On détermine la nouvelle tailleCases
        int dimFenX = gui.getPanelWidth();
        int dimFenY = gui.getPanelHeight();
        if (carte.getTailleCases() * carte.getNbLignes() > dimFenX || carte.getTailleCases() * carte.getNbColonnes() > dimFenY) {
            //pour adapter l'échelle de la carte à la taille de la fenetre graphique
            int minDim = (dimFenX > dimFenY ? dimFenY : dimFenX);
            tailleCases = (minDim == dimFenX ? minDim / carte.getNbLignes() : minDim / carte.getNbColonnes());
        } else {
            tailleCases = carte.getTailleCases();
        }
        

        gui.reset();
        
        //On dessine la carte
        drawCarte();

        //On dessine les robots
        drawListRobots();

        //On dessine les incencies
        drawListIncendies();
        
    }

    /**
     * Programme les déplacements des robots.
     */
    private void planCoordinates() {
        //à implémenter
    }

    @Override
    public void next() {
        //à implémenter
    }

    @Override
    public void restart() {
        //à implémenter
    }

    /**
     * Partie Dessin
     *
     * @throws IOException *
     */
    private void drawCase(int x, int y, NatureTerrain nature) {
        String pathImage = null;
        ImageElement image;

        switch (nature) {
            case EAU:
                pathImage = "images/water.png";
                image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
                gui.addGraphicalElement(image);
                break;
            case FORET:
                pathImage = "images/herb.png";
                image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
                gui.addGraphicalElement(image);
                pathImage = "images/tree.png";
                image = new ImageElement(x, y - tailleCases, pathImage, tailleCases, (int) 2.5 * tailleCases, null);
                gui.addGraphicalElement(image);
                break;
            case ROCHE:
                pathImage = "images/cherrygrove.png";
                image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
                gui.addGraphicalElement(image);
                pathImage = "images/rock.png";
                image = new ImageElement(x, y, pathImage, tailleCases, tailleCases, null);
                gui.addGraphicalElement(image);
                break;
            case TERRAIN_LIBRE:
                pathImage = "images/cherrygrove.png";
                image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
                gui.addGraphicalElement(image);
                break;
            case HABITAT:
                pathImage = "images/cherrygrove.png";
                image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
                gui.addGraphicalElement(image);
                pathImage = "images/barktown.png";
                image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
                gui.addGraphicalElement(image);
                break;
            default:
                System.out.println("Nature du terrain non reconnue. Fermeture de la fenêtre graphique");
                //lever exception
                break;
        }
    }

    private void drawCarte() {
        Carte carte = data.getCarte();

        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                drawCase(j * tailleCases, i * tailleCases, carte.getCase(i, j).getNatureTerrain());
            }
        }
    }

    private void drawRobot(robots.Robot R) {
        String pathImage = R.getFileOfRobot() + "face1.png";
        int x = R.getPosition().getLigne() * tailleCases;
        int y = R.getPosition().getColonne() * tailleCases;
        ImageElement image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
        gui.addGraphicalElement(image);
    }

    private void drawListRobots() {
        for (robots.Robot it : data.getListeRobots()) {
            drawRobot(it);
        }
    }

    private void drawIncendie(Incendie inc) {
        String pathImage = "images/fire.png";
        int x = inc.getCaseIncendie().getLigne() * tailleCases;
        int y = inc.getCaseIncendie().getColonne() * tailleCases;
        ImageElement image = new ImageElement(x, y, pathImage, tailleCases + 1, tailleCases + 1, null);
        gui.addGraphicalElement(image);
    }

    private void drawListIncendies() {
        for (Incendie it : data.getListeIncendies()) {
            drawIncendie(it);
        }
    }
}
