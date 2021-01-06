package pl.jerzygajewski.util;

import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
import pl.jerzygajewski.symulator.entity.User;

public class Simulation {
    int z = 0;
    int zz = 0;
    long o = 0;
    long p = 0;
    long infected = 0;

    public RecordInfoEntity startSimulation(User user, int i) {

        int daysGone = user.getTm();
        long[] sick = new long[user.getTs()];

        double mm = user.getM() / 100;
        int daysSurvived = user.getTi();
            RecordInfoEntity recordInfoEntity = new RecordInfoEntity();
            sick[i] = countingPi(user, i);
            if (i >= daysSurvived) {
                double l = sick[zz] * mm;
                o = sick[zz] - Math.round(l) + o;
                recordInfoEntity.setPr(o);
                zz++;
            } else recordInfoEntity.setPr(0L);
            if (i >= daysGone) {
                p = Math.round(sick[z] * mm) + p;
                recordInfoEntity.setPm(p);
                z++;
            } else recordInfoEntity.setPm(0L);

            infected = sick[i] - recordInfoEntity.getPm() - recordInfoEntity.getPr() + infected;
            recordInfoEntity.setPi(infected);
            recordInfoEntity.setPv(user.getP() - infected);
            return recordInfoEntity;
        }

    private long countingPi(User user, int i) {
        long count;
        if(i == 0){
            count = user.getI();
        } else {
            count = user.getI() * Math.round(Math.pow(user.getR(), i));
        }
        return count;
    }
}
