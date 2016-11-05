package io;

import donneesSimulation.DonneesSimulation;
import carte.*;
import exceptions.ErreurPosition;
import robots.*;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;



/**
 * Lecteur de cartes au format specifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 puis simplement affichées.
 A noter: pas de vérification sémantique sur les valeurs numériques lues.

 IMPORTANT:

 Cette classe ne fait que LIRE les infos et les afficher.
 A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 (ou non), qui CREENT les objets au moment adéquat pour construire une
 instance de la classe DonneesSimulation à partir d'un fichier.

 Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 contenant toutes les données lues:
    public static DonneesSimulation creeDonnees(String fichierDonnees);
 Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 créent les objets adéquats et les ajoutent ds l'instance de
 DonneesSimulation.
 */
public class LecteurDonnees {


    
	//On stocke ces 4 variables pour éviter de les passer en arguments à toutes les méthodes;
	private static Carte carte;
	private static boolean modeverbeux;
	private static List<Incendie> listeIncendies = new ArrayList<Incendie>();
	private static List<Robot> listeRobots = new ArrayList<Robot>();
	
	/**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
	
	public static DonneesSimulation lire(String fichierDonnees, Boolean modeverb)
        throws FileNotFoundException, DataFormatException, ErreurPosition {
		modeverbeux = modeverb;
		if(modeverbeux){
			System.out.println("\n == Lecture du fichier" + fichierDonnees);
		}
        
		LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        lecteur.lireCarte();
        lecteur.lireIncendies();
        lecteur.lireRobots();
        DonneesSimulation res = new DonneesSimulation(carte, listeIncendies, listeRobots);
        scanner.close();
        
        if(modeverbeux){
        	System.out.println("\n == Lecture terminee");
        }
        return res;
    }




    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
            if(modeverbeux){
            	System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);
            }
            
            //Instanciation de Carte
            carte = new Carte(nbLignes, nbColonnes, tailleCases);
            
            //Instanciation des cases
            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col);
                }
            }
            
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }




    /**
     * Lit et affiche les donnees d'une case.
     */
    private void lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        if(modeverbeux){
        	System.out.print("Case (" + lig + "," + col + "): ");
        }
        String chaineNature = new String();
        NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();
            if(modeverbeux){
            	System.out.print("nature = " + chaineNature);
            }
            nature = NatureTerrain.valueOf(chaineNature);
            Case c = new Case(lig, col, nature);
            carte.setCase(lig, col, c);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
    }


    /**
     * Lit et affiche les donnees des incendies.
     */
    private void lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            if(modeverbeux){
            	System.out.println("Nb d'incendies = " + nbIncendies);
            }
            
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i);
            }
            
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i
     */
    private void lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        if(modeverbeux){
        System.out.print("Incendie " + i + ": ");
        }
        
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();
            
            if(modeverbeux){
            	System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);
            }
            
            Incendie I = new Incendie(carte.getCase(lig, col), intensite);
            listeIncendies.add(I);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
    private void lireRobots() throws DataFormatException, ErreurPosition {
        ignorerCommentaires();
        
        try {
            int nbRobots = scanner.nextInt();
            if(modeverbeux){
            	System.out.println("Nb de robots = " + nbRobots);
            }
            
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i);
            }
            
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i
     */
    private void lireRobot(int i) throws DataFormatException, ErreurPosition {
        ignorerCommentaires();
        
        if(modeverbeux){
        	System.out.print("Robot " + i + ": ");
        }

        try {
        	Robot rob;
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            
            if(modeverbeux){
            	System.out.print("position = (" + lig + "," + col + ");");
            }
            
            String type = scanner.next();
            if(modeverbeux){
            	System.out.print("\t type = " + type);
            	System.out.print(";\t\tvitesse = ");
            }

            
            // lecture eventuelle d'une vitesse du robot (entier)
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
            	if(modeverbeux){
            		System.out.print("valeur par defaut");
            	}
                switch(type)
                {
                        case "DRONE" : 
                        	rob = new Drone(carte.getCase(lig, col));
                        	listeRobots.add(rob);
                        	break;
                        case "ROUES" :
                        	rob = new RobotARoues(carte.getCase(lig, col));
                        	listeRobots.add(rob);
                        	break;
                        case "PATTES" :
                        	rob = new RobotAPattes(carte.getCase(lig,col));
                        	listeRobots.add(rob);
                        	break;
                        case "CHENILLES" :
                        	rob = new RobotAChenilles(carte.getCase(lig,col));
                        	listeRobots.add(rob);
                        	break;
                        default : System.out.println("Le robot "+type+" n'existe pas.");
                        //levee exception
                }
            } else {
                int vitesse = Integer.parseInt(s);
                if(modeverbeux){
                	System.out.print(vitesse);
                }
                
                switch(type)
                {
                        case "DRONE" :
                        	rob = new Drone(carte.getCase(lig, col), vitesse);
                        	listeRobots.add(rob);
                        	break;
                        case "ROUES" :
                        	rob = new RobotARoues(carte.getCase(lig, col), vitesse);
                        	listeRobots.add(rob);
                        	break;
                        case "PATTES" :
                        	System.out.println("Attention, la vitesse du robot à pattes ne peut pas être modifié.");;
                        	rob = new RobotAPattes(carte.getCase(lig,col));
                        	listeRobots.add(rob);
                        	break;
                        case "CHENILLES" : 
                        	rob = new RobotAChenilles(carte.getCase(lig,col), vitesse);
                        	listeRobots.add(rob);
                        	break;
                        default : System.out.println("Le robot "+type+" n'existe pas.");
                        //levee exception
                }
            }
            verifieLigneTerminee();

            System.out.println();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
