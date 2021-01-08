package pl.jerzygajewski.symulator.service;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfo;
import pl.jerzygajewski.symulator.entity.StartData;
import pl.jerzygajewski.symulator.repository.RecordInfoRepository;
import pl.jerzygajewski.symulator.repository.StartDataRepository;
import pl.jerzygajewski.util.Simulation;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StartDataEntityService {
    private StartDataRepository startDataRepository;
    private RecordInfoRepository recordInfoRepository;

    public StartDataEntityService(StartDataRepository startDataRepository, RecordInfoRepository recordInfoRepository) {
        this.startDataRepository = startDataRepository;
        this.recordInfoRepository = recordInfoRepository;
    }

    public StartData addParameters(StartData startData) {
        startData = startDataRepository.save(startData);

        Simulation simulation = new Simulation();
        List<RecordInfo> recordInfoList = simulation.startSimulation(startData);
        RecordInfo[] arr = new RecordInfo[recordInfoList.size()];
        recordInfoList.toArray(arr);
        for (int i = 0; i < arr.length; i++) {
            recordInfoRepository.save(arr[i]);
        }
        return startData;
    }

    public List<StartData> getAllDatas() {
        return startDataRepository.findAll();
    }

    public StartData getDataById(long id) {
        return startDataRepository.findById(id).orElse(null);
    }

    @Transactional
    public StartData editStartData(StartData startData) {
        StartData startDataUpdate = startDataRepository.findById(startData.getId()).orElse(null);
        startDataUpdate.setP(startData.getP());
        startDataUpdate.setN(startData.getN());
        startDataUpdate.setM(startData.getM());
        startDataUpdate.setI(startData.getI());
        startDataUpdate.setR(startData.getR());
        startDataUpdate.setTi(startData.getTi());
        startDataUpdate.setTm(startData.getTm());
        startDataUpdate.setTs(startData.getTs());

        startDataUpdate = startDataRepository.save(startDataUpdate);
        List<RecordInfo> list = recordInfoRepository.findAllByStartData_Id(startData.getId());
        for (RecordInfo recordInfo : list) {
            recordInfoRepository.delete(recordInfo);
        }
        Simulation simulation = new Simulation();
        List<RecordInfo> recordInfoList = simulation.startSimulation(startData);
        RecordInfo[] arr = new RecordInfo[recordInfoList.size()];
        recordInfoList.toArray(arr);
        for (RecordInfo recordInfo : arr) {
            recordInfoRepository.save(recordInfo);
        }

        return startDataUpdate;
    }

    public String deleteStartData(long id) {
        List<RecordInfo> list = recordInfoRepository.findAllByStartData_Id(id);
        for (RecordInfo recordInfo : list) {
            recordInfoRepository.delete(recordInfo);
        }
        startDataRepository.deleteById(id);
        return "removed";
    }

    public List<RecordInfo> getRecords(long id) {
        return recordInfoRepository.findAllByStartData_Id(id);
    }
}
