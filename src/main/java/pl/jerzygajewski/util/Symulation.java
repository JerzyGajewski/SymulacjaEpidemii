package pl.jerzygajewski.util;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.repository.RecordInfoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class Symulation {

//    RecordInfoRepository recordInfoRepository;
//
//    public Simulation(RecordInfoRepository recordInfoRepository) {
//        this.recordInfoRepository = recordInfoRepository;
//    }

    public List<RecordInfoEntity> startSimulation(User user) {
        List<RecordInfoEntity> recordInfoEntityList = new ArrayList<>();
        long[] sick = new long[user.getTs()];
        int daysForPr = 0;
        long peopleCured = 0;
        long peopleDied = 0;
        double numberFromM = user.getM()/100;
        int daysForPm = 0;

        for (int i = 0; i < user.getTs(); i++) {
            RecordInfoEntity recordInfoEntity = new RecordInfoEntity();

            sick[i] =  countingPi(user, i);

            if(i >= user.getTi()){
                peopleCured = sick[daysForPr] - Math.round(sick[daysForPr] * numberFromM) + peopleCured;
                recordInfoEntity.setPr(peopleCured);
                daysForPr++;
            }

            if(i >= user.getTm()){
                long deadPeople = Math.round(sick[daysForPm] * numberFromM);
                peopleDied = deadPeople + peopleDied;
                recordInfoEntity.setPm(peopleDied);
                daysForPm++;
            }
            long infected = getInfected(sick, i, recordInfoEntity);
            recordInfoEntity.setPi(infected);
            recordInfoEntity.setPv(user.getP() - infected);
            recordInfoEntity.setUser(user);
            recordInfoEntityList.add(recordInfoEntity);
        }
        return recordInfoEntityList;
    }

    private long getInfected(long[] sick, int i, RecordInfoEntity recordInfoEntity) {
        long infected;
        if(i == 0){
            infected = sick[i] - recordInfoEntity.getPm() - recordInfoEntity.getPr();
        } else {
            infected = sick[i -1] + sick[i] - recordInfoEntity.getPm() - recordInfoEntity.getPr();
        }
        return infected;
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
