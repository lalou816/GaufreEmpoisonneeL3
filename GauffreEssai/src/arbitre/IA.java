package arbitre;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

public class IA {
    static Hashtable<Integer,Coup> arbre;

    static Coup Prediction1(Gauffre gauffre){
        //Executer un coup gagnant
        boolean fin = true;
        for (int i = 1; i < gauffre.getWidth(); i++) {
            for (int j = 0; j < gauffre.getHeight(); j++) {
                if (gauffre.cases[i][j]){
                    fin = false;
                    break;
                }
            }
        }
        if(fin)
            return new Coup(0,1);

        fin = true;
        for (int i = 0; i < gauffre.getWidth(); i++) {
            for (int j = 1; j < gauffre.getHeight(); j++) {
                if (gauffre.cases[i][j]){
                    fin = false;
                    break;
                }
            }
        }
        if(fin)
            return new Coup(1,0);

        //Eviter un coup perdant
        Random r = new Random();
        if(!gauffre.cases[1][1] && !gauffre.cases[2][0]  && !gauffre.cases[0][2])
            if(r.nextBoolean())
                return new Coup(0,1);
            else
                return new Coup(1,0);

        int x = r.nextInt(gauffre.getWidth());
        int y = r.nextInt(gauffre.getHeight());
        while(!gauffre.cases[x][y] || (x == 1 && y == 0) ||(x == 0 && y == 1)){
            x = r.nextInt(gauffre.getWidth());
            y = r.nextInt(gauffre.getHeight());
        }

        return new Coup(x,y);
    }

    public static void InitIa3(Gauffre gorigin){
        arbre = new Hashtable<>();
        Gauffre gauffre = new Gauffre(gorigin.width,gorigin.height);
        gauffre.SetCases(gorigin.getCasesCopy());
        Prediction_rec(gauffre,true);
        Prediction_rec(gauffre,false);
        System.out.println("IA initialisée");
    }

    static Coup Prediction2(Gauffre gauffre) {
        return arbre.get(Arrays.deepHashCode(gauffre.cases));
    }

    static long Prediction_rec(Gauffre gauffre, boolean iaturn) {
        Coup coupmax =null;

        //Si on connait deja cette configuration on sort
        if(iaturn)coupmax = arbre.get(Arrays.deepHashCode(gauffre.cases));
        if(coupmax!=null)
            return coupmax.poid;

        //Si est terminé, on renvoi +1 si on gagne et -1 si on perd
        if(gauffre.estTerminee()){
            if(iaturn)
                return -1;
            else
                return 1;
        }

        //Autrement on retourne la somme des sous branches
        coupmax = new Coup(0,0,-10000);
        long total = 0;
        int prob = 1;
        for (int i = 0; i < gauffre.getWidth(); i++) {
            for (int j = 0; j < gauffre.getHeight(); j++) {
                if(gauffre.cases[i][j] && i+j!=0) {
                    //On entre dans la recursion si la case est ok
                    boolean[][] save = gauffre.getCasesCopy();
                    gauffre.manger(i, j);
                    long tmp = Prediction_rec(gauffre, !iaturn);
                    gauffre.SetCases(save);
                    total += tmp;

                    //On remplace si plus grand
                    if(tmp > coupmax.poid) {
                        coupmax = new Coup(i, j, tmp);
                        prob = 1;
                    }

                    //On remplace avec une probabilite si identiques
                    if(false && tmp == coupmax.poid && new Random().nextInt(prob)==0){
                        coupmax = new Coup(i,j,tmp);
                        prob++;
                    }
                }
            }
        }
        if(iaturn)arbre.put(Arrays.deepHashCode(gauffre.cases),coupmax);
        return total;
    }

}
