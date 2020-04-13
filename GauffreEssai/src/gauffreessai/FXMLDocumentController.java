/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gauffreessai;

import arbitre.Joueur;
import arbitre.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Sam
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label titre;
    @FXML
    private Label score;
    @FXML
    private TextArea historique;
    @FXML
    private Label console;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private MenuBar menu;
    Joueur joueurGauche, joueurDroit;
    Jeu j;
    public GraphicsContext gc;
    @FXML
    private MenuItem reset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        j = new Jeu();
        gc = mainCanvas.getGraphicsContext2D();
        System.out.println("On a créé une instance de jeu avec une gauffre de " + j.widthGauffre() + " par " + j.heightGauffre());
        joueurGauche = new Joueur("Alice", 0, true, 0);
        joueurDroit = new Joueur("Bob", 3, false, 0);
        joueurGauche.setOpposant(joueurDroit);
        joueurDroit.setOpposant(joueurGauche);
        drawShapes(gc, 0);
        joueurGauche.setupIA(j, this);
        joueurDroit.setupIA(j, this);
        score.setText(joueurGauche.getNom() + " : " + joueurGauche.score() + "\n"
                + joueurDroit.getNom() + " : " + joueurDroit.score());
        console.setText("Tour de " + joueurGauche.getNom());
    }

    @FXML
    private void clickCanvas(MouseEvent event) {
        //Si c'est le tour d'un joueur : 
        Joueur joueurTour;
        Joueur opposant;
        if (joueurDroit.estMonTour() && joueurDroit.estHumain()) {
            //C'est au tour de l'"humain droit" de jouer
            joueurTour = joueurDroit;
            opposant = joueurGauche;
        } else if (joueurGauche.estMonTour() && joueurGauche.estHumain()) {
            //C'est au tour de l'"humain gauche" de jouer
            joueurTour = joueurGauche;
            opposant = joueurDroit;
        } else {
            return;
        }

        if (!j.estTermine()) {

            int h = (int) mainCanvas.getHeight();
            int w = (int) mainCanvas.getWidth();
            double wG = j.widthGauffre();
            double hG = j.heightGauffre();
            double clickX = event.getX();
            double clickY = event.getY();
            double ratioX = clickX * wG / w;
            double ratioY = clickY * hG / h;
            jouerJoueur(joueurTour, opposant, ratioX, ratioY);
        }

    }

    public void drawShapes(GraphicsContext gc, double secondesDeDelai) {
        Platform.runLater(new Thread(() -> {

            if (secondesDeDelai > 0) {
                try {
                    Thread.sleep((long) (secondesDeDelai * 1000));
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //On reset le dessin
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
            //Pour chaque case de la gauffre, on affiche un rectangle
            double wG = j.widthGauffre();
            double hG = j.heightGauffre();
            double h = mainCanvas.getHeight();
            double w = mainCanvas.getWidth();
            double ratioX = w / wG;
            double ratioY = h / hG;
            int margin = 5;

            for (int x = 0; x < wG; x++) {
                for (int y = 0; y < hG; y++) {

                    double coordX = (ratioX * x) + margin;
                    double coordY = (ratioY * y) + margin;
                    double sizeX = ratioX - (2 * margin);
                    double sizeY = ratioY - (2 * margin);

                    if (j.gauffre().getCases()[x][y]) {
                        gc.setFill(Color.DARKRED);
                    } else {
                        gc.setFill(Color.GRAY);
                    }
                    gc.fillRect(coordX, coordY, sizeX, sizeY);
                }
            }

            double coordX = margin;
            double coordY = margin;
            double sizeX = ratioX - (2 * margin);
            double sizeY = ratioY - (2 * margin);
            gc.setFill(Color.GREEN);
            gc.fillOval(coordX, coordY, sizeX, sizeY);
        }));
    }

    @FXML
    private void resetGame(ActionEvent event) {
        j = new Jeu();
        historique.clear();
        int scoreG = joueurGauche.score();
        int scoreD = joueurDroit.score();

        joueurGauche = new Joueur("Alice", 0, true, scoreG);
        joueurDroit = new Joueur("Bob", 2, false, scoreD);
        joueurGauche.setOpposant(joueurDroit);
        joueurDroit.setOpposant(joueurGauche);
        drawShapes(gc, 0);
        joueurGauche.setupIA(j, this);
        joueurDroit.setupIA(j, this);
        updateConsole("Tour de " + joueurGauche.getNom());
        drawShapes(gc, 0);
    }

    @FXML
    private void closeProgram(ActionEvent event) {
        closeAllThreads();
        System.exit(0);
    }

    void closeAllThreads() {
        joueurGauche.stopIA();
        joueurGauche.stopIA();
    }

    public void updateCoups(String s) {
        Platform.runLater(new Thread(() -> {
            historique.setText(s);
        }));
    }

    public void updateConsole(String s) {
        Platform.runLater(new Thread(() -> {
            console.setText(s);
        }));
    }

    public void updateScore(String s) {
        Platform.runLater(new Thread(() -> {
            score.setText(s);
        }));
    }

    public boolean jouerJoueur(Joueur joueurTour, Joueur opposant, double ratioX, double ratioY) {
        if (j.gauffre().manger((int) ratioX, (int) ratioY)) {
            drawShapes(gc, 0);
            updateCoups(j.gauffre().printMouvements());
            joueurTour.changeTour(opposant);
            updateConsole("Tour de " + opposant.getNom());
        } else {
            updateConsole("Coup invalide de\n" + joueurTour.getNom());
            return false;
        }
        if (j.estTermine()) {
            updateConsole("TERMINÉ !\n" + joueurTour.getNom() + " a gagné !");
            joueurTour.addScore();
            updateScore(joueurGauche.getNom() + " : " + joueurGauche.score() + "\n"
                    + joueurDroit.getNom() + " : " + joueurDroit.score());
        }
        return true;
    }

}
