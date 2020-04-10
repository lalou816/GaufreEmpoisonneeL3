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
public class Jeu {
    Gauffre gauffre;
    public Jeu() {
        this.gauffre = new Gauffre(7, 5);
    }
    
    public int widthGauffre () {
        return gauffre.getWidth();
    }
    
    public int heightGauffre() {
        return gauffre.getHeight();
    }
    
    public Gauffre gauffre() {
        return gauffre;
    }
    
    public boolean estTermine() {
        return gauffre.estTerminee();
    }
    
}
