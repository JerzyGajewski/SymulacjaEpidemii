package pl.jerzygajewski.util;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfo;
import pl.jerzygajewski.symulator.entity.StartData;

import java.util.ArrayList;
import java.util.List;

@Service
public class Simulation {

    public List<RecordInfo> startSimulation(StartData startData) {
        List<RecordInfo> recordInfoList = new ArrayList<>();
        long[] sick = new long[startData.getTs()];
        int daysForPr = 0;
        long peopleCured = 0;
        long peopleDied = 0;
        double numberFromM = startData.getM() / 100;
        int daysForPm = 0;

        for (int i = 0; i < startData.getTs(); i++) {

            RecordInfo recordInfo = new RecordInfo();
            if(recordInfoList.size() > 0 && recordInfoList.get(i-1).getPv() <= recordInfoList.get(i-1).getPi()){
                if (i >= startData.getTi() && recordInfoList.get(i-1).getPi() > recordInfoList.get(i-1).getPr()) {
                    peopleCured = sick[daysForPr] - Math.round(sick[daysForPr] * numberFromM) + peopleCured;
                    recordInfo.setPr(peopleCured);
                    daysForPr++;
                } else {
                    peopleCured = recordInfoList.get(i-1).getPi() - Math.round(recordInfoList.get(i-1).getPi() * numberFromM) + peopleCured;
                    recordInfo.setPr(peopleCured);
                }
                if (i >= startData.getTm() && recordInfoList.get(i-1).getPi() > recordInfoList.get(i-1).getPm()) {
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
                if (startData.getP() - peopleDeadCuredInfected + infected > 0) {
                    recordInfo.setPi(startData.getP() - peopleDeadCuredInfected + infected);
                } else {
                recordInfo.setPi(0L);
                }
                recordInfo.setPv(0L);
            } else {

                sick[i] = countingPi(startData, i);

                if (i >= startData.getTi()) {
                    peopleCured = sick[daysForPr] - Math.round(sick[daysForPr] * numberFromM) + peopleCured;
                    recordInfo.setPr(peopleCured);
                    daysForPr++;
                }

                if (i >= startData.getTm()) {
                    long deadPeople = Math.round(sick[daysForPm] * numberFromM);
                    peopleDied = deadPeople + peopleDied;
                    recordInfo.setPm(peopleDied);
                    daysForPm++;
                }
                long infected = getInfected(sick, i, recordInfo);
                recordInfo.setPi(infected);

                long peopleDeadCuredInfected = infected + recordInfo.getPm() + recordInfo.getPr();

                recordInfo.setPv(startData.getP() - peopleDeadCuredInfected);
            }
            recordInfo.setStartData(startData);
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

    private long countingPi(StartData startData, int i) {
        long count;
        if (i == 0) {
            count = startData.getI();
        } else {
            count = startData.getI() * Math.round(Math.pow(startData.getR(), i));
        }

        return count;
    }

}
