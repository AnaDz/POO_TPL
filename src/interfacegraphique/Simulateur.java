package interfacegraphique;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import java.io.IOException;
import carte.*;
import DonneesSimulation.DonneesSimulation;
import java.util.*;
import evenements.*;
import robots.*;
import java.lang.Math;
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
     * Le gestionnaire d'événements associé à la simulation
     */
    
    private GestionnaireEvents GE;
    /**
     * La nouvelle taille de case : en tenant compte des dimensions de la
     * fenetre graphique *
     */
    private int tailleCases;

    /**La liste des ImageElement associées aux robots**/
    private List<ImageElement> listeRobots;
    
    /**La liste des ImageElement associées aux incendies**/
    private List<ImageElement> listeIncendies;
    
    
    private int indiceImage = 0;
    
    public Simulateur(GUISimulator gui, DonneesSimulation data, GestionnaireEvents GE) {
        //On instancie les attributs
        this.gui = gui;
        gui.setSimulable(this);
        this.data = data;
        Carte carte = data.getCarte();
        this.GE = GE;
        listeRobots = new ArrayList<ImageElement>();
        listeIncendies = new ArrayList<ImageElement>();

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

    

    @Override
    public void next() {
    	if(!GE.simulationTerminee()){
	        GE.incrementeDate();
	        refreshRobots();
    	}
    	else
    		System.out.println("Simulation terminée.");
    }

    @Override
    public void restart() {
        //a implementer
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
        String pathImage = R.getFileOfRobot() + "SUD1.png";
        int x = R.getPosition().getLigne() * tailleCases;
        int y = R.getPosition().getColonne() * tailleCases;
        ImageElement image = new ImageElement(y, x, pathImage, tailleCases + 1, tailleCases + 1, null);
        listeRobots.add(image);
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
        ImageElement image = new ImageElement(y, x, pathImage, tailleCases + 1, tailleCases + 1, null);
        listeIncendies.add(image);
        gui.addGraphicalElement(image);
    }

    private void drawListIncendies() {
        for (Incendie it : data.getListeIncendies()) {
            drawIncendie(it);
        }
    }
    
    private void refreshRobots(){
    //Ce refresh à lieu toutes les GestionnaireEvents.h minutes
    	int imageX, imageY, robotX, robotY;
    	Robot rob;
    	for(int i = 0; i < listeRobots.size(); i++){
    		imageX = listeRobots.get(i).getY();
    		imageY = listeRobots.get(i).getX();
    		rob = data.getListeRobots().get(i);
    		robotX = rob.getPosition().getLigne()*tailleCases;
    		robotY = rob.getPosition().getColonne()*tailleCases;
    		if(imageX != robotX || imageY != robotY){
    			bougeRobot(rob, i, imageX, imageY);
    		}
    		else {
    			//si le robot ne bouge pas et qu'il est sur une case appropriée, alors il remplit son réservoir
    		}
    	}
    }
    
    private void bougeRobot(Robot rob, int indexRob, int imageX, int imageY){
    	double vitesse = rob.getVitesse(rob.getPosition().getNatureTerrain());
    	int distanceParcourue = (int) (vitesse*GE.getPasDeTemps()*1000/60); //distance parcourue à echelle réelle
    	distanceParcourue = distanceParcourue*tailleCases/data.getCarte().getTailleCases();//distance parcourue à échelle de la carte
    	
    	String pathImage = rob.getFileOfRobot() + rob.getDirection().toString() + indiceImage + ".png";
    	indiceImage = (indiceImage+1)%3;
    	ImageElement image = new ImageElement(listeRobots.get(indexRob).getX(), listeRobots.get(indexRob).getY(), pathImage, tailleCases + 1, tailleCases + 1, null);
    	
    	if(rob.getDirection().getX() == 0){
    		//DEPLACEMENT HORIZONTAL
    		int distanceRestante = Math.abs(imageY-rob.getPosition().getColonne()*tailleCases); 
    		if(distanceParcourue > distanceRestante){
    			//on est arrivés
    			listeRobots.get(indexRob).translate(rob.getDirection().getY()*distanceRestante, 0);
    			rob.switchOccupe();
    		}
    		else {
    			//on est pas arrivés
    			listeRobots.get(indexRob).translate(rob.getDirection().getY()*distanceParcourue, 0);
    		}
    	}
    	else {
    		//DEPLACEMENT VERTICAL
    		int distanceRestante = Math.abs(imageX-rob.getPosition().getLigne()*tailleCases);
    		if(distanceParcourue > distanceRestante){
    			//on est arrivés
    			listeRobots.get(indexRob).translate(0, rob.getDirection().getX()*distanceRestante);
    			rob.switchOccupe();
    			System.out.println(rob.getOccupe());
    		}
    		else {
    			//on est pas arrivés
    			listeRobots.get(indexRob).translate(0, rob.getDirection().getX()*distanceParcourue);
    		}
    	}
    		
    }
}
