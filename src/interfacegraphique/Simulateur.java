package interfacegraphique;
import gui.GUISimulator;
import gui.Rectangle;
import gui.ImageElement;
import gui.Simulable;
import gui.Text;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import carte.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import robots.*;
import DonneesSimulation.DonneesSimulation;

public class Simulateur implements Simulable {
	
	/**L'interface graphique associée**/
	private GUISimulator gui;
    
	/**Les données de la simulation**/
	private DonneesSimulation data;
	
	/**La nouvelle taille de case : en tenant compte des dimensions de la fenetre graphique **/
	private int tailleCases;
	
	public Simulateur(GUISimulator gui, DonneesSimulation data){
		//On instancie les attributs
		this.gui = gui;
		gui.setSimulable(this);
		this.data = data;
		Carte carte = data.getCarte();
		
		//On détermine la nouvelle tailleCases
    	int dimFenX = gui.getPanelWidth();
    	int dimFenY = gui.getPanelHeight();
    	if(carte.getTailleCases()*carte.getNbLignes() > dimFenX || carte.getTailleCases()*carte.getNbColonnes() > dimFenY) {
    		//pour adapter l'échelle de la carte à la taille de la fenetre graphique
    		int minDim = (dimFenX > dimFenY ?  dimFenY : dimFenX);
    		System.out.println("getHeight() = "+gui.getPanelHeight()+" et getWidth() = "+gui.getPanelWidth());
    		tailleCases = (minDim == dimFenX ? minDim/carte.getNbLignes() : minDim/carte.getNbColonnes());
    	}
    	else {
    		tailleCases = carte.getTailleCases();
    	}
    	
    	
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
    private void planCoordinates(){
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
    
    /**Partie Dessin
     * @throws IOException **/
    private void drawCase(int x, int y, NatureTerrain nature) {
    	String pathImage = null;
    	ImageElement image;
    	
    	switch(nature) {
	    	case EAU:
	    		pathImage = "images/water.png";
	    		image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		break;
	    	case FORET:
	    		pathImage = "images/herb.png";
	    		image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		pathImage = "images/tree.png";
	    		image = new ImageElement(x, y-tailleCases, pathImage, tailleCases, (int) 2.5*tailleCases, null);	
	    		gui.addGraphicalElement(image);
	    		break;
	    	case ROCHE:
	    		pathImage = "images/cherrygrove.png";
	    		image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		pathImage = "images/rock.png";
	    		image = new ImageElement(x, y, pathImage, tailleCases, tailleCases, null);
	    		gui.addGraphicalElement(image);
	    		break;
	    	case TERRAIN_LIBRE:
	    		pathImage = "images/cherrygrove.png";
	    		image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		break;
	    	case HABITAT:
	    		pathImage = "images/cherrygrove.png";
	    		image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		pathImage = "images/barktown.png";
	    		image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		break;
	    	default:
	    		System.out.println("Nature du terrain non reconnue. Fermeture de la fenêtre graphique");
	    		//lever exception
	    		break;
    	}
    	//gui.addGraphicalElement(new Rectangle(x , y , color, color, tailleCases));
    }
    
    private void drawCarte(){
    	gui.reset();
    	Carte carte = data.getCarte();
    	
    	for(int i = 0; i < carte.getNbLignes(); i++){
    		for(int j = 0; j < carte.getNbColonnes(); j++){
    			drawCase(j*tailleCases, i*tailleCases, carte.getCase(i, j).getNatureTerrain());
    		}
    	}
    }
    
    
    private void drawRobot(robots.Robot R){
    	String pathImage = R.getFileOfRobot()+"face1.png";
    	int x = R.getPosition().getLigne()*tailleCases;
    	int y = R.getPosition().getColonne()*tailleCases;
    	ImageElement image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
		gui.addGraphicalElement(image);
    }
    
    private void drawListRobots(){
    	for (robots.Robot it : data.getListeRobots()) {
    		drawRobot(it);
    	}
    }
    
    private void drawIncendie(Incendie inc){
    	String pathImage = "images/fire.png";
    	int x = inc.getCaseIncendie().getLigne()*tailleCases;
    	int y = inc.getCaseIncendie().getColonne()*tailleCases;
    	ImageElement image = new ImageElement(x, y, pathImage, tailleCases+1, tailleCases+1, null);
		gui.addGraphicalElement(image);
    }
    
    private void drawListIncendies(){
    	for (Incendie it : data.getListeIncendies()) {
    		drawIncendie(it);
    	}
    }
}
