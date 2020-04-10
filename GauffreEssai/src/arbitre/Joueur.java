/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbitre;

import gauffreessai.FXMLDocumentController;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam
 */
public class Joueur {

    boolean estIA = false;
    int difficulteIA;
    boolean monTour = false;
    Joueur opposant = null;
    String nom;
    int score;
    boolean stopThread = false;
    Thread t;

    public Joueur(String nom, int difficulteIA, boolean monTour, int score) {

        this.difficulteIA = difficulteIA;
        this.estIA = (difficulteIA != 0);
        this.monTour = monTour;
        this.nom = nom;
        this.score = score;

    }

    public boolean estMonTour() {
        return monTour;
    }

    public void rename(String newNom) {
        nom = newNom;
    }

    public String getNom() {
        if (estIA) {
            return nom + " (IA)";
        } else {
            return nom;
        }
    }

    public void setupIA(Jeu j, FXMLDocumentController controller) {
        stopThread = false;
        if (estIA) {
            t = new Thread(() -> {
                while (!stopThread && !j.gauffre.estTerminee()) {
                    System.out.print("");
                    if (monTour) {//TODO ça marche pas
                        Coup c = nextCoupIA(j.gauffre);
                        controller.jouerJoueur(this, opposant, c.x, c.y);
                        /*System.out.println("C'EST LE TOUR DE " + getNom().toUpperCase());
                        nextCoupIA(j.gauffre);
                        changeTour(opposant);
                        controller.drawShapes(controller.gc, 0);
                        controller.updateCoups(j.gauffre.printMouvements());*/
                    }
                }
            });
            t.start();
        }
    }

    public Coup nextCoupIA(Gauffre gauffre) {
        Random r = new Random();

        int x = -1;
        int y = -1;
        Coup c;
        switch (difficulteIA) {
            case 1:
                //IA Aléatoire, simplement

                x = r.nextInt(gauffre.getWidth());
                y = r.nextInt(gauffre.getHeight());
                c = new Coup(x, y);

                break;
            case 2:
                x = r.nextInt(gauffre.getWidth());
                y = r.nextInt(gauffre.getHeight());
                c = new Coup(x, y);
                break;
            case 3:
                x = r.nextInt(gauffre.getWidth());
                y = r.nextInt(gauffre.getHeight());
                c = new Coup(x, y);
                break;
            default:
                c = null;
            //Impossible, la difficulté est trop haute ou le joueur est humain.
        }

        return c;
    }

    public ArrayList<Coup> EvaluationGauffre(Gauffre gauffre) {
        ArrayList<Coup> uneSolution = new ArrayList<>();
        //Ici calcul minimax de la disposition du jeu

        return uneSolution;
    }

    public boolean estIA() {
        return estIA;
    }

    public boolean estHumain() {
        return !estIA;
    }

    void changeTour() {
        monTour = !monTour;
    }

    public void changeTour(Joueur opposant) {
        changeTour();
        opposant.changeTour();
    }

    public int score() {
        return score;
    }

    public void addScore() {
        score++;
    }

    public void setOpposant(Joueur opposant) {
        this.opposant = opposant;
    }

    public Joueur Opposant() {
        return opposant;
    }

    public void stopIA() {
        stopThread = true;
    }

    public void setMonTour(boolean monTour) {
        this.monTour = monTour;
    }

    private void pause(double secondes) {
        try {
            Thread.sleep((long) (secondes * 1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
