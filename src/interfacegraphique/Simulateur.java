package interfacegraphique;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;

import java.awt.image.ImageObserver;
import java.io.IOException;
import carte.*;
import donneesSimulation.DonneesSimulation;
import java.util.*;
import evenements.*;
import exceptions.ErreurPosition;
import robots.*;
import java.lang.Math;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    /**La liste des ImageElement associées aux incendies**/
    private List<ImageElement> listeIncendies;
    
    private int [][] coordImageRobot;
    
    private int indiceImage = 0;
    
    public Simulateur(GUISimulator gui, DonneesSimulation data, GestionnaireEvents GE) {
        //On instancie les attributs
        this.gui = gui;
        gui.setSimulable(this);
        this.data = data;
        Carte carte = data.getCarte();
        this.GE = GE;
        //listeRobots = new ArrayList<ImageElement>();
        listeIncendies = new ArrayList<ImageElement>();
        coordImageRobot = new int[data.getListeRobots().size()][2];
        
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
    
    public int getTailleCase() {
        return this.tailleCases;
    }

    

    @Override
    public void next() {
    	if(!GE.simulationTerminee()){
	        gui.reset();
	        GE.incrementeDate();
	        drawCarte();
	        refreshIncendies();
                /* CORRECTION AUTOMATIQUE ! A REVOIR */
                try {
                    refreshRobots();
                } catch (ErreurPosition ex) {
                    Logger.getLogger(Simulateur.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        coordImageRobot[data.getListeRobots().indexOf(R)][0] = y;
        coordImageRobot[data.getListeRobots().indexOf(R)][1] = x;
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
    
    private void refreshIncendies(){
    	Incendie inc;
    	int x, y;
    	for(int i = 0; i < data.getListeIncendies().size(); i++){
    		inc = data.getListeIncendies().get(i);
    		x = inc.getCaseIncendie().getLigne() * tailleCases;
    		y = inc.getCaseIncendie().getColonne() * tailleCases;
    		ImageElement image = new ImageElement(y, x, "images/fire.png", tailleCases+1, tailleCases+1, null);
    		gui.addGraphicalElement(image);
    	}
    }
    private void refreshRobots() throws ErreurPosition{
    //Ce refresh à lieu toutes les GestionnaireEvents.h minutes
    	Robot rob;
    	for(int i = 0; i < data.getListeRobots().size(); i++){
    		rob = data.getListeRobots().get(i);
    		switch(rob.getAction()){
    		case DEPLACE:
    			bougeRobot(rob, i);
    			break;
    		case REMPLIR:
    			remplitReservoir(rob, i, (int) (GE.getPasDeTemps() * rob.getCapaciteMax()/rob.getTempsRemplissageComp()) +1);
    			break;
    		case VERSER:
    			verseReservoir(rob, i, (int) (GE.getPasDeTemps() * rob.getInterventionUnitaire() * 60)+1);
    			break;
    		case INNOCUPE:
    			//On réactualise juste l'image du robot, et on le met de face pour montrer qu'il attend de nouvelles instructions
    			String pathImage = rob.getFileOfRobot() + "SUD1.png";
    			ImageElement image = new ImageElement(coordImageRobot[i][0], coordImageRobot[i][1], pathImage, tailleCases + 1, tailleCases + 1, null);
    			gui.addGraphicalElement(image);
    			break;
    		default:
    			System.out.println("Action non reconnue.");
    			break;
    		}
    	}
    }
    
    private void bougeRobot(Robot rob, int indexRob) throws ErreurPosition{
    	double vitesse = rob.getVitesse(rob.getPosition().getNatureTerrain());
    	int distanceParcourue = (int) (vitesse*GE.getPasDeTemps()*1000/60); //distance parcourue à echelle réelle
    	distanceParcourue = distanceParcourue*tailleCases/data.getCarte().getTailleCases();//distance parcourue à échelle de la carte
    	int depX = coordImageRobot[indexRob][0];
    	int depY = coordImageRobot[indexRob][1];
    	int arriveX = (rob.getPosition().getColonne()+rob.getDirection().getY())*tailleCases;
    	int arriveY = (rob.getPosition().getLigne()+rob.getDirection().getX())*tailleCases;
    	
    	if(rob.getDirection().getX() == 0){
    		//DEPLACEMENT HORIZONTAL
    		int distanceRestante = Math.abs(depX-arriveX); 
    		if(distanceParcourue >= distanceRestante){
    			//on est arrivés
    			coordImageRobot[indexRob][0] += rob.getDirection().getY()*distanceRestante;
    			int lig = rob.getPosition().getLigne();
    			int col = rob.getPosition().getColonne()+rob.getDirection().getY();
    			rob.setPosition(data.getCarte().getCase(lig, col));
    			rob.switchAction(Action.INNOCUPE);
    		}
    		else {
    			//on est pas arrivés
    			coordImageRobot[indexRob][0] += rob.getDirection().getY()*distanceParcourue; 
    		}
    	}
    	else {
    		//DEPLACEMENT VERTICAL
    		int distanceRestante = Math.abs(depY-arriveY);
    		if(distanceParcourue >= distanceRestante){
    			//on est arrivés
    			coordImageRobot[indexRob][1] += rob.getDirection().getX()*distanceRestante;
    			int lig = rob.getPosition().getLigne()+rob.getDirection().getX();
    			int col = rob.getPosition().getColonne();
    			rob.setPosition(data.getCarte().getCase(lig, col));
    			rob.switchAction(Action.INNOCUPE);
    		}
    		else {
    			//on est pas arrivés
    			coordImageRobot[indexRob][1] += rob.getDirection().getX()*distanceParcourue; 
    		}
    	}
    	String pathImage = rob.getFileOfRobot() + rob.getDirection().toString() + indiceImage + ".png";
    	indiceImage = (indiceImage+1)%2;
    	ImageElement image = new ImageElement(coordImageRobot[indexRob][0], coordImageRobot[indexRob][1], pathImage, tailleCases + 1, tailleCases + 1, null);
    	
    	gui.addGraphicalElement(image);
    }
    
    private void remplitReservoir(Robot rob, int indexRob, int qte){
    	String pathImage;
    	if(rob.getDirection().toString() == null) {
    		pathImage = rob.getFileOfRobot() + "SUD" + indiceImage + ".png";
    	} else {
    		pathImage = rob.getFileOfRobot() + rob.getDirection().toString() + indiceImage + ".png";
    	}
    	//Elements graphiques
    	ImageElement image = new ImageElement(coordImageRobot[indexRob][0], coordImageRobot[indexRob][1], pathImage, tailleCases + 1, tailleCases + 1, null);
    	gui.addGraphicalElement(image);
    	pathImage = "images/remplir.png";
    	image = new ImageElement(coordImageRobot[indexRob][0], coordImageRobot[indexRob][1], pathImage, tailleCases + 1, tailleCases + 1, null);
    	gui.addGraphicalElement(image);
    	//On remplit le robot
    	rob.remplirReservoir(qte);
    	
    	//On ajoute une "alarme graphique" si le robot est totalement remplit.
    	if(rob.getCapaciteMax() == rob.getVolumeRestant()){
    		pathImage = "images/bubble.png";
    		image = new ImageElement(coordImageRobot[indexRob][0], coordImageRobot[indexRob][1]+tailleCases, pathImage, tailleCases + 1, tailleCases + 1, null);
    		gui.addGraphicalElement(image);
    		//Ajouter un wait pour qu'on ai le temps de voir l'alarme
    		rob.switchAction(Action.INNOCUPE);
    	}
    }
    
    private void verseReservoir(Robot rob, int indexRob, int qte){
    	String pathImage;
    	if(rob.getDirection().toString() == null) {
    		pathImage = rob.getFileOfRobot() + "SUD" + indiceImage + ".png";
    	} else {
    		pathImage = rob.getFileOfRobot() + rob.getDirection().toString() + indiceImage + ".png";
    	}
    	//Elements graphiques
    	ImageElement image = new ImageElement(coordImageRobot[indexRob][0], coordImageRobot[indexRob][1], pathImage, tailleCases + 1, tailleCases + 1, null);
    	gui.addGraphicalElement(image);
    	pathImage = "images/verser.png";
    	image = new ImageElement(coordImageRobot[indexRob][0], coordImageRobot[indexRob][1], pathImage, tailleCases + 1, tailleCases + 1, null);
    	gui.addGraphicalElement(image);
    	//On remplit le robot
    	if(rob.getVolumeRestant() < qte){
    		rob.deverserEau(rob.getVolumeRestant());
    	} else {
    		rob.deverserEau(qte);
    	}
    	
    	if(rob.getVolumeRestant() <= 0){
    		rob.switchAction(Action.INNOCUPE);
    	}
    	
    	Incendie incendievise = data.getIncendie(rob.getPosition());
    	if(incendievise.getNbLitres() <= 0) {
    		data.getListeIncendies().remove(incendievise);
    		rob.switchAction(Action.INNOCUPE);
    	}
    }
}
