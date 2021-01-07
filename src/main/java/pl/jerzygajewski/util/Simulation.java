package pl.jerzygajewski.util;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfo;
import pl.jerzygajewski.symulator.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class Simulation {

    public List<RecordInfo> startSimulation(User user) {
        List<RecordInfo> recordInfoList = new ArrayList<>();
        long[] sick = new long[user.getTs()];
        int daysForPr = 0;
        long peopleCured = 0;
        long peopleDied = 0;
        double numberFromM = user.getM() / 100;
        int daysForPm = 0;

        for (int i = 0; i < user.getTs(); i++) {

            RecordInfo recordInfo = new RecordInfo();
            if(recordInfoList.size() > 0 && recordInfoList.get(i-1).getPv() <= recordInfoList.get(i-1).getPi()){
                if (i >= user.getTi() && recordInfoList.get(i-1).getPi() > recordInfoList.get(i-1).getPr()) {
                    peopleCured = sick[daysForPr] - Math.round(sick[daysForPr] * numberFromM) + peopleCured;
                    recordInfo.setPr(peopleCured);
                    daysForPr++;
                } else {
                    peopleCured = recordInfoList.get(i-1).getPi() - Math.round(recordInfoList.get(i-1).getPi() * numberFromM) + peopleCured;
                    recordInfo.setPr(peopleCured);
                }
                if (i >= user.getTm() && recordInfoList.get(i-1).getPi() > recordInfoList.get(i-1).getPm()) {
                    long deadPeople = Math.round(sick[daysForPm] * numberFromM);
                    peopleDied = deadPeople + peopleDied;
                    recordInfo.setPm(peopleDied);
                    daysForPm++;
                } else {
                    long deadPeople = Math.round(recordInfoList.get(i-1).getPi() * numberFromM);
                    peopleDied = deadPeople + peopleDied;
                    recordInfo.setPm(peopleDied);
                }
                long infected = getInfected(sick, i, recordInfo);
                long peopleDeadCuredInfected = infected + recordInfo.getPm() + recordInfo.getPr();
                if (user.getP() - peopleDeadCuredInfected + infected > 0) {
                    recordInfo.setPi(user.getP() - peopleDeadCuredInfected + infected);
                } else {
                recordInfo.setPi(0L);
                }
                recordInfo.setPv(0L);
            } else {

                sick[i] = countingPi(user, i);

                if (i >= user.getTi()) {
                    peopleCured = sick[daysForPr] - Math.round(sick[daysForPr] * numberFromM) + peopleCured;
                    recordInfo.setPr(peopleCured);
                    daysForPr++;
                }

                if (i >= user.getTm()) {
                    long deadPeople = Math.round(sick[daysForPm] * numberFromM);
                    peopleDied = deadPeople + peopleDied;
                    recordInfo.setPm(peopleDied);
                    daysForPm++;
                }
                long infected = getInfected(sick, i, recordInfo);
                recordInfo.setPi(infected);

                long peopleDeadCuredInfected = infected + recordInfo.getPm() + recordInfo.getPr();

                recordInfo.setPv(user.getP() - peopleDeadCuredInfected);
            }
            recordInfo.setUser(user);
            recordInfoList.add(recordInfo);
        }
        return recordInfoList;
    }

    private long getInfected(long[] sick, int i, RecordInfo recordInfo) {
        long infected;
        if (i == 0) {
            infected = sick[i] - recordInfo.getPm() - recordInfo.getPr();
        } else {
            infected = sick[i - 1] + sick[i] - recordInfo.getPm() - recordInfo.getPr();
        }
        return infected;
    }

    private long countingPi(User user, int i) {
        long count;
        if (i == 0) {
            count = user.getI();
        } else {
            count = user.getI() * Math.round(Math.pow(user.getR(), i));
        }

        return count;
    }

}
