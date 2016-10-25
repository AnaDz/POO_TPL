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

public class Simulateur implements Simulable {
	
	/**L'interface graphique associée**/
	private GUISimulator gui;
    
	/**La carte**/
	private Carte carte;
	
	/**La liste des incencies**/
	
	/**La liste des robots**/
	
	public Simulateur(GUISimulator gui, Carte carte){
		this.gui = gui;
		gui.setSimulable(this);
		this.carte = carte;
		
		drawCarte();
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
    private void drawCase(int x, int y, NatureTerrain nature, int tailleCases) {
    	Color color = null;
    	String pathImage = null;
    	ImageElement image;
    	
    	switch(nature) {
	    	case EAU:
	    		pathImage = "images/water.png";
	    		image = new ImageElement(x-tailleCases/2, y-tailleCases/2, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		break;
	    	case FORET:
	    		color = Color.decode("#006600"); //vert
	    		gui.addGraphicalElement(new Rectangle(x , y , color, color, tailleCases));
	    		pathImage = "images/tree.png";
	    		image = new ImageElement(x-tailleCases/2, y-(int) 2*tailleCases, pathImage, tailleCases, (int) 2.5*tailleCases, null);	
	    		gui.addGraphicalElement(image);
	    		break;
	    	case ROCHE:
	    		color = Color.decode("#663300"); //vert
	    		gui.addGraphicalElement(new Rectangle(x , y , color, color, tailleCases));
	    		pathImage = "images/rock.png";
	    		image = new ImageElement(x-tailleCases/2, y-tailleCases/2, pathImage, tailleCases, tailleCases, null);
	    		gui.addGraphicalElement(image);
	    		break;
	    	case TERRAIN_LIBRE:
	    		pathImage = "images/cherrygrove.png";
	    		image = new ImageElement(x-tailleCases/2, y-tailleCases/2, pathImage, tailleCases+1, tailleCases+1, null);
	    		gui.addGraphicalElement(image);
	    		break;
	    	case HABITAT:
	    		color = Color.decode("#990033"); //violet
	    		gui.addGraphicalElement(new Rectangle(x , y , color, color, tailleCases));
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
    	
    	int tailleCases;
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
    	
    	int centreRectangle = tailleCases/2;
    	for(int i = 0; i < carte.getNbLignes(); i++){
    		for(int j = 0; j < carte.getNbColonnes(); j++){
    			drawCase(j*tailleCases+centreRectangle, i*tailleCases+centreRectangle, carte.getCase(i, j).getNatureTerrain(), tailleCases);
    		}
    	}
    }
    
}
