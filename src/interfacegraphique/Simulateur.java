package interfacegraphique;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import carte.*;
import donneesSimulation.DonneesSimulation;
import java.util.*;
import evenements.*;
import robots.*;
import java.lang.Math;
import java.text.DecimalFormat;

public class Simulateur implements Simulable {

    /**
     * L'interface graphique associée à la simulation
     */
    private GUISimulator gui;

    /**
     * Les données de la simulation obtenues par lecture de la carte
     */
    private DonneesSimulation data;

    /**
     * Le gestionnaire d'évenements associé à la simulation
     */
    private GestionnaireEvents GE;

    /**
     * La taille des cases à l'échelle du simulateur
     */
    private int tailleCases;

    /**
     * Les coordonnées des robots à l'échelle réelle au cours du temps.
     */
    private int[][] coordRobot;

    /**
     * Variable permettant de choisir l'image appropriée afin de donner
     * l'impression que les robots marchent
     */
    private int indiceImage = 0;

    /**
     * ************************
     * Nécessaires au restart
     ************************
     */
    /**
     * Tableau sauvegardant la position des robots
     */
    private int[][] savePosRobots;
    /**
     * Tableau sauvegardant l'intensité des incendies
     */
    private int[] saveIntensiteIncendies;

    /**
     * Construit une instance de Simulateur.
     *
     * @param gui la fenetre graphique associée à la simulation
     * @param data les données de la simulation
     * @param GE le gestionnaire d'evenements associée à la simulation
     */
    public Simulateur(GUISimulator gui, DonneesSimulation data, GestionnaireEvents GE) {
        //On instancie les attributs...
        this.gui = gui;
        gui.setSimulable(this);
        this.data = data;
        this.GE = GE;
        //... en initialisant le tableau des coordonnées des robots
        coordRobot = new int[data.getListeRobots().size()][2];
        for (robots.Robot R : data.getListeRobots()) {
            coordRobot[data.getListeRobots().indexOf(R)][0] = R.getPosition().getColonne() * data.getCarte().getTailleCases();
            coordRobot[data.getListeRobots().indexOf(R)][1] = R.getPosition().getLigne() * data.getCarte().getTailleCases();
        }

        //On sauvegarde pour pouvoir restart plus tard
        save();

        //On détermine la nouvelle tailleCases
        Carte carte = data.getCarte();
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
        refreshRobots();

        //On dessine les incencies
        refreshIncendies();

    }

    /**
     * Sauvegarde la position des robots, et l'intensité des incendies A appeler
     * au début de la simulation pour pouvoir restart par la suite.
     */
    private void save() {
        List<Robot> listeRobots = data.getListeRobots();
        List<Incendie> listeIncendies = data.getListeIncendies();
        savePosRobots = new int[2][listeRobots.size()];
        saveIntensiteIncendies = new int[listeIncendies.size()];

        //On sauvegarde la position des robots
        for (int i = 0; i < listeRobots.size(); i++) {
            savePosRobots[0][i] = listeRobots.get(i).getPosition().getLigne();
            savePosRobots[1][i] = listeRobots.get(i).getPosition().getColonne();
        }

        //On sauvegarde les intensités des incendies
        for (int i = 0; i < listeIncendies.size(); i++) {
            saveIntensiteIncendies[i] = listeIncendies.get(i).getNbLitres();
        }

    }

    /**
     * Retourne la taille des cases à l'échelle de la simulation
     *
     * @return taille case
     */
    public int getTailleCase() {
        return this.tailleCases;
    }

    @Override
    /**
     * Incrémente la date du gestionnaire d'evenements puis rafraichit les
     * robots et les incendies.
     */
    public void next() {
        if (!GE.simulationTerminee()) {
            gui.reset();
            GE.incrementeDate();
            drawCarte();
            refreshIncendies();
            refreshRobots();
        } else {
            System.out.println("Simulation terminée.");
            try {
                Thread.sleep(3000);
                DecimalFormat format = new DecimalFormat("#.00");
                System.out.print("########################################\n");
                System.out.print("Les POKEMON™ ont travaillé dur pendant\n\t\t");
                System.out.print(format.format(GE.getPasDeTemps() * GE.getDateSimulation() / 60));
                System.out.print("\t\t\nheures pour éteindre tous les incendies !\n");
                System.out.print("########################################");
                System.exit(0);
            } catch (InterruptedException e) {
                System.exit(1);
            }
        }
    }

    @Override
    /**
     * Réinitialise la simulation
     */
    public void restart() {
        //On remet les robots à leur état initial
        List<Robot> listeRobots = data.getListeRobots();
        Robot rob;
        for (int i = 0; i < listeRobots.size(); i++) {
            rob = listeRobots.get(i);
            rob.setPosition(data.getCarte().getCase(savePosRobots[0][i], savePosRobots[1][i]));

            rob.setDirection(null);
            rob.switchAction(Action.INNOCUPE);
            rob.setVolumeRestant(rob.getCapaciteMax());
        }

        //On réinitialise le tableau des coordonnées
        for (robots.Robot R : data.getListeRobots()) {
            coordRobot[data.getListeRobots().indexOf(R)][0] = R.getPosition().getColonne() * data.getCarte().getTailleCases();
            coordRobot[data.getListeRobots().indexOf(R)][1] = R.getPosition().getLigne() * data.getCarte().getTailleCases();
        }

        //On remet les incendies à leur état initial
        List<Incendie> listeIncendies = data.getListeIncendies();

        for (int i = 0; i < listeIncendies.size(); i++) {
            listeIncendies.get(i).setNbLitres(saveIntensiteIncendies[i]);
        }

        //On réinitialise le gestionnaire d'évenements
        GE.restartGestionnaireEvents();
        gui.reset();
        drawCarte();
        refreshRobots();
        refreshIncendies();
    }

    /**
     * ****************************************************
     * PARTIE DESSIN
     *****************************************************
     */
    /**
     * Upload une image à partir des coordonnées à l'échelle réelle.
     *
     * @param x	la coordonnée sur l'axe des abscisses de l'image à uploader
     * @param y	la coordonnée sur l'axe des ordonnées de l'image à uploader
     * @param taille	la taille de l'image
     * @param path	le chemin de l'image à uploader
     * @return l'image uploadée dont les coordonnées ont été ramenées à
     * l'échelle de la simulation
     */
    private ImageElement loadImage(int x, int y, int taille, String path) {
        return new ImageElement(x * tailleCases / data.getCarte().getTailleCases(), y * tailleCases / data.getCarte().getTailleCases(), path, taille, taille, null);
    }

    /**
     * Dessine une case. (x,y) sont à l'échelle réelle
     *
     * @param x	la coordonnée sur l'axe des abscisses de la case
     * @param y	la coordonnée sur l'axe des ordonnées de la case
     * @param nature	la nature du terrain de la case
     */
    private void drawCase(int x, int y, NatureTerrain nature) {
        String pathImage = null;
        ImageElement image;

        switch (nature) {
            case EAU:
                pathImage = "images/water.png";
                image = loadImage(x, y, tailleCases + 2, pathImage);
                gui.addGraphicalElement(image);
                break;
            case FORET:
                pathImage = "images/herb.png";
                image = loadImage(x, y, tailleCases + 1, pathImage);
                gui.addGraphicalElement(image);
                pathImage = "images/tree.png";
                image = loadImage(x, y, tailleCases + 1, pathImage);
                gui.addGraphicalElement(image);
                break;
            case ROCHE:
                pathImage = "images/cherrygrove.png";
                image = loadImage(x, y, tailleCases + 1, pathImage);
                gui.addGraphicalElement(image);
                pathImage = "images/rock.png";
                image = loadImage(x, y, tailleCases + 1, pathImage);
                gui.addGraphicalElement(image);
                break;
            case TERRAIN_LIBRE:
                pathImage = "images/cherrygrove.png";
                image = loadImage(x, y, tailleCases + 1, pathImage);
                gui.addGraphicalElement(image);
                break;
            case HABITAT:
                pathImage = "images/cherrygrove.png";
                image = loadImage(x, y, tailleCases + 1, pathImage);
                gui.addGraphicalElement(image);
                pathImage = "images/barktown.png";
                image = loadImage(x, y, tailleCases + 1, pathImage);
                gui.addGraphicalElement(image);
                break;
            default:
                System.out.println("Nature du terrain non reconnue. Fermeture de la fenêtre graphique");
                System.exit(1);
                break;
        }
    }

    /**
     * Dessine la carte, case par case.
     */
    private void drawCarte() {
        Carte carte = data.getCarte();

        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                drawCase(j * data.getCarte().getTailleCases(), i * data.getCarte().getTailleCases(), carte.getCase(i, j).getNatureTerrain());
            }
        }
    }

    /**
     * Rafraichit les incendies. Appelée à chaque next().
     */
    private void refreshIncendies() {
        if (data.getListeIncendies() != null) {
            ImageElement image = null;
            boolean resteIncendie = false;
            for (Incendie inc : data.getListeIncendies()) {
                int x = inc.getCaseIncendie().getLigne() * data.getCarte().getTailleCases();
                int y = inc.getCaseIncendie().getColonne() * data.getCarte().getTailleCases();
                if (inc.getNbLitres() > 0) {
                    resteIncendie = true;
                    image = loadImage(y, x, tailleCases, "images/fire.png");
                } else {
                    image = loadImage(y, x - (data.getCarte().getTailleCases()) / 3, tailleCases, "images/smoke.png");
                }
                gui.addGraphicalElement(image);
            }
            if (resteIncendie == false) {
                GE.supprimeDernierElement();
            }
        }
    }

    /**
     * Rafraichit les robots en fonction de leur ACTION courante. Appelée à
     * chaque next().
     */
    private void refreshRobots() {
        Robot rob;
        for (int i = 0; i < data.getListeRobots().size(); i++) {
            rob = data.getListeRobots().get(i);
            switch (rob.getAction()) {
                case DEPLACE:
                    bougeRobot(rob, i);
                    break;
                case REMPLIR:
                    remplitReservoir(rob, i);
                    break;
                case VERSER:
                    verseReservoir(rob, i);
                    break;
                case INNOCUPE:
                    String pathImage = rob.getFileOfRobot() + "SUD1.png";
                    ImageElement image = loadImage(coordRobot[i][0], coordRobot[i][1], tailleCases + 1, pathImage);
                    gui.addGraphicalElement(image);
                    break;
                default:
                    System.out.println("Action non reconnue.");
                    break;
            }
        }
    }

    /**
     * Translate un robot vers sa direction. Le robot se déplace de la distance
     * qu'il a pu parcourir en GE.getPasDeTemps() minutes
     *
     * @param rob le robot a déplacer.
     * @param indexRob la position du robot dans data.ListeRobots
     */
    private void bougeRobot(Robot rob, int indexRob) {
        double vitesse = rob.getVitesse(rob.getPosition().getNatureTerrain());
        int distanceParcourue = (int) (vitesse * GE.getPasDeTemps() * 1000 / 60); //distance parcourue à echelle réelle
        int depX = coordRobot[indexRob][0];
        int depY = coordRobot[indexRob][1];
        int arriveX = (rob.getPosition().getColonne() + rob.getDirection().getY()) * data.getCarte().getTailleCases();
        int arriveY = (rob.getPosition().getLigne() + rob.getDirection().getX()) * data.getCarte().getTailleCases();

        if (rob.getDirection().getX() == 0) {
            //DEPLACEMENT HORIZONTAL
            int distanceRestante = Math.abs(depX - arriveX);
            if (distanceParcourue >= distanceRestante) {
                //on est arrivés
                coordRobot[indexRob][0] += rob.getDirection().getY() * distanceRestante;
                int lig = rob.getPosition().getLigne();
                int col = rob.getPosition().getColonne() + rob.getDirection().getY();
                rob.setPosition(data.getCarte().getCase(lig, col));
                rob.switchAction(Action.INNOCUPE);
            } else {
                //on est pas arrivés
                coordRobot[indexRob][0] += rob.getDirection().getY() * distanceParcourue;
            }
        } else {
            //DEPLACEMENT VERTICAL
            int distanceRestante = Math.abs(depY - arriveY);
            if (distanceParcourue >= distanceRestante) {
                //on est arrivés
                coordRobot[indexRob][1] += rob.getDirection().getX() * distanceRestante;
                int lig = rob.getPosition().getLigne() + rob.getDirection().getX();
                int col = rob.getPosition().getColonne();
                rob.setPosition(data.getCarte().getCase(lig, col));
                rob.switchAction(Action.INNOCUPE);
            } else {
                //on est pas arrivés
                coordRobot[indexRob][1] += rob.getDirection().getX() * distanceParcourue;
            }
        }
        String pathImage = rob.getFileOfRobot() + rob.getDirection().toString() + indiceImage + ".png";
        indiceImage = (indiceImage + 1) % 2;
        ImageElement image = loadImage(coordRobot[indexRob][0], coordRobot[indexRob][1], tailleCases + 1, pathImage);

        gui.addGraphicalElement(image);
    }

    /**
     * Remplit le réservoir d'un robot. Le volume ajoutée dans le reservoir
     * correspond au volume que le robot peut puiser en GE.getPasDeTemps()
     *
     * @param rob le robot concerné
     * @param indexRob la position du robot dans data.ListeRobots
     */
    private void remplitReservoir(Robot rob, int indexRob) {
        String pathImage;
        int qte = (int) (GE.getPasDeTemps() * rob.getCapaciteMax() / rob.getTempsRemplissageComp());
        if (rob.getDirection() == null) {
            pathImage = rob.getFileOfRobot() + "SUD" + indiceImage + ".png";
        } else {
            pathImage = rob.getFileOfRobot() + rob.getDirection().toString() + indiceImage + ".png";
        }
        //Elements graphiques
        ImageElement image = loadImage(coordRobot[indexRob][0], coordRobot[indexRob][1], tailleCases + 1, pathImage);
        gui.addGraphicalElement(image);
        pathImage = "images/remplir.png";
        image = loadImage(coordRobot[indexRob][0], coordRobot[indexRob][1], tailleCases + 1, pathImage);
        gui.addGraphicalElement(image);
        //On remplit le robot
        rob.remplirReservoir(qte);

        //On ajoute une "alarme graphique" si le robot est totalement remplit.
        if (rob.getCapaciteMax() == rob.getVolumeRestant()) {
            pathImage = "images/bubble.png";
            image = loadImage(coordRobot[indexRob][0], coordRobot[indexRob][1] - tailleCases, tailleCases + 1, pathImage);
            gui.addGraphicalElement(image);
            //Ajouter un wait pour qu'on ai le temps de voir l'alarme
            rob.switchAction(Action.INNOCUPE);
        }
    }

    /**
     * Verse le reservoir d'un robot. Le volume versée correspond au volume que
     * peut verser le robot en GE.getPasDeTemps()
     *
     * @param rob le robot concerné.
     * @param indexRob la position du robot dans data.ListeRobots
     * @param qte	la quantité à verser
     */
    private void verseReservoir(Robot rob, int indexRob) {
        String pathImage;
        int qte = (int) (GE.getPasDeTemps() * rob.getInterventionUnitaire() * 60);
        if (rob.getDirection() == null) {
            pathImage = rob.getFileOfRobot() + "SUD" + indiceImage + ".png";
        } else {
            pathImage = rob.getFileOfRobot() + rob.getDirection().toString() + indiceImage + ".png";
        }
        //Elements graphiques
        ImageElement image = loadImage(coordRobot[indexRob][0], coordRobot[indexRob][1], tailleCases + 1, pathImage);
        gui.addGraphicalElement(image);
        pathImage = "images/verser.png";
        image = loadImage(coordRobot[indexRob][0], coordRobot[indexRob][1], tailleCases + 1, pathImage);
        gui.addGraphicalElement(image);

        if (data.getIncendie(rob.getPosition()).getNbLitres() <= 0) {
            if (GE.getChefEvolue() != null) {
                GE.getChefEvolue().libereAssignement(data.getListeIncendies().indexOf(data.getIncendie(rob.getPosition())));
            }
            rob.switchAction(Action.INNOCUPE);
        } else if (rob.getVolumeRestant() < qte) {
            rob.deverserEau(rob.getVolumeRestant());
        } else {
            rob.deverserEau(qte);
        }

        //Si le reservoir du robot est vide alors il devient innocupé
        if (rob.getVolumeRestant() <= 0) {
            rob.switchAction(Action.INNOCUPE);
            if (GE.getChefEvolue() != null) {
                GE.getChefEvolue().libereAssignement(data.getListeIncendies().indexOf(data.getIncendie(rob.getPosition())));
            }
        }

    }
}
