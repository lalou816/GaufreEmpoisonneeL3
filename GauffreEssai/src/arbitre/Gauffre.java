/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbitre;

import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class Gauffre {

    boolean[][] cases;
    int width;
    int height;
    ArrayList<Coup> suitedeCoups = new ArrayList<>();

    public Gauffre(int width, int height) {
        this.cases = new boolean[width][height];
        this.width = width;
        this.height = height;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cases[i][j] = true;
            }
        }

    }

    public boolean[][] getCases() {
        return cases;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String printMouvements() {
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < suitedeCoups.size(); i++) {
            Coup c = suitedeCoups.get(i);
            strB.append("N° ").append(i).append(" : ").append(c.afficher());
        }
        return strB.toString();
    }
    
    public String saveMouvements() {
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < suitedeCoups.size(); i++) {
            Coup c = suitedeCoups.get(i);
            strB.append(c.afficherPourFichier());
        }
        return strB.toString();
    }
    

    public boolean manger(int x, int y) {
        if ((x == 0 && y == 0) || x >= width || y >= height || !cases[x][y]) {
            return false;
        }

        for (int i = x; i < width; i++) {
            for (int j = y; j < height; j++) {
                cases[i][j] = false;
            }
        }
        suitedeCoups.add(new Coup(x, y));
        return true;
    }

    public void addCoup(Coup coup) {
        suitedeCoups.add(coup);
    }

    boolean estTerminee() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!(i == 0 && j == 0)) {
                    if (cases[i][j] == true) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean [][] getCasesCopy(){
        boolean[][] copy = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                copy[i][j] = cases[i][j];
            }
        }
        return copy;
    }

    public void SetCases(boolean[][] _cases){
        cases = _cases;
    }
}
