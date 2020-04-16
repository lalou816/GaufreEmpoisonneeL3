/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbitre;

/**
 *
 * @author Sam
 */
public class Coup {

    int x;
    int y;
    long poid;

    public Coup(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coup(int _x, int _y,long _poid) {
        x = _x;
        y = _y;
        poid = _poid;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String afficher() {
        return "< " + x + " | " + y + " >\n";
    }

    public String afficherPourFichier() {
        return x + ";" + y + "\n";
    }
}
