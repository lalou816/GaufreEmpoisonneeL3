package arbitre;

public class IA {
    static Coup Prediction1(Gauffre gauffre){
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

        return null;
    }


}
