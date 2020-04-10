package arbitre;

import java.util.Random;

public class IA {
    static Coup Prediction1(Gauffre gauffre, Random r){
        //Executer un coup gagnant
        boolean fin = true;
        for (int i = 1; i < gauffre.width; i++) {
            for (int j = 0; j < gauffre.height; j++) {
                if (gauffre.cases[i][j]){
                    fin = false;
                    break;
                }
            }
        }
        if(fin)
            return new Coup(1,0);

        fin = true;
        for (int i = 0; i < gauffre.width; i++) {
            for (int j = 1; j < gauffre.height; j++) {
                if (gauffre.cases[i][j]){
                    fin = false;
                    break;
                }
            }
        }
        if(fin)
            return new Coup(0,1);

        //Eviter un coup perdant
        int x = r.nextInt(gauffre.getWidth());
        int y = r.nextInt(gauffre.getHeight());
        while((x == 1 && y == 0) ||(x == 0 && y == 1)){
            x = r.nextInt(gauffre.getWidth());
            y = r.nextInt(gauffre.getHeight());
        }

        return new Coup(x,y);
    }


}
