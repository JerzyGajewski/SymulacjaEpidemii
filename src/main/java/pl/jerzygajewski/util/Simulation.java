package pl.jerzygajewski.util;

import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
import pl.jerzygajewski.symulator.entity.User;

public class Simulation {

    public RecordInfoEntity startSimulation(User user) {


        int daysGone = user.getTm();
        long[] sick = new long[user.getTs()];
        int z = 0;
        int zz = 0;
        long o = 0;
        long p = 0;
        long infected = 0;
        double mm = user.getM() / 100;
        int daysSurvived = user.getTi();
        for (int i = 0; i < user.getTs(); i++) {
            RecordInfoEntity recordInfoEntity = new RecordInfoEntity();
            sick[i] = countingPi(user, i);
            if (i == daysSurvived) {
                double l = sick[zz] * mm;
                o = sick[zz] - Math.round(l) + o;
                recordInfoEntity.setPr(o);
                zz++;
                daysSurvived++;
            } else recordInfoEntity.setPr(0L);
            if (i == daysGone) {
                p = Math.round(sick[z] * mm) + p;
                recordInfoEntity.setPm(p);
                z++;
                daysGone++;
            } else recordInfoEntity.setPm(0L);

            infected = sick[i] - recordInfoEntity.getPm() - recordInfoEntity.getPr() + infected;
            recordInfoEntity.setPi(infected);
            recordInfoEntity.setPv(user.getP() - infected);
            recordInfoRepository.save(recordInfoEntity);
        }
    }
}
