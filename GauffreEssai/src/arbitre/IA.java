package arbitre;

import java.util.Random;

public class IA {
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
        int x = r.nextInt(gauffre.getWidth());
        int y = r.nextInt(gauffre.getHeight());
        while((x == 1 && y == 0) ||(x == 0 && y == 1)){
            x = r.nextInt(gauffre.getWidth());
            y = r.nextInt(gauffre.getHeight());
        }

        return new Coup(x,y);
    }

    static Coup Prediction2(Gauffre gauffre) {
        int max = 0,x=0,y=0;
        //On test pour chacune des cases disponible de la gauffre quelle est son ratio de victoire
        for (int i = 0; i < gauffre.getWidth(); i++) {
            for (int j = 0; j < gauffre.getHeight(); j++) {
                if(gauffre.cases[i][j] && (i!=0 && j!=0)){
                    //On entre dans la recursion si la case est ok
                    boolean[][] save  = gauffre.getCasesCopy();
                    gauffre.manger(i,j);
                    int tmp = Prediction_rec(gauffre,true);
                    gauffre.SetCases(save);

                    //On remplace si une valeur est meilleur    ##IL FAUDRA RANDOMIZER ICI##
                    if(tmp>max){
                        x=i;
                        y=j;
                        max = tmp;
                    }
                }
            }
        }
        return new Coup(x,y);
    }

    static int Prediction_rec(Gauffre gauffre, boolean iaturn) {
        //Si est terminé, on renvoi +1 si on gagne et -1 si on perd
        if(gauffre.estTerminee()){
            if(iaturn)
                return 1;
            else
                return -1;
        }

        //Autrement on retourne la somme des sous branches
        int max = 0;
        for (int i = 0; i < gauffre.getWidth(); i++) {
            for (int j = 0; j < gauffre.getHeight(); j++) {
                if(gauffre.cases[i][j] && (i!=0 && j!=0)) {
                    //On entre dans la recursion si la case est ok
                    boolean[][] save = gauffre.getCasesCopy();
                    gauffre.manger(i, j);
                    int tmp = Prediction_rec(gauffre, !iaturn);
                    gauffre.SetCases(save);
                    if(tmp>max)
                        max = tmp;
                }
            }
        }
        return max;
    }

}
