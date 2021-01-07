package pl.jerzygajewski.symulator.service;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfo;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.repository.RecordInfoRepository;
import pl.jerzygajewski.symulator.repository.UserRepository;
import pl.jerzygajewski.util.Simulation;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserEntityService {
    private UserRepository userRepository;
    private RecordInfoRepository recordInfoRepository;

    public UserEntityService(UserRepository userRepository, RecordInfoRepository recordInfoRepository) {
        this.userRepository = userRepository;
        this.recordInfoRepository = recordInfoRepository;
    }

    public User addParameters(User user) {
        user = userRepository.save(user);

        Simulation simulation = new Simulation();
        List<RecordInfo> recordInfoList = simulation.startSimulation(user);
        RecordInfo[] arr = new RecordInfo[recordInfoList.size()];
        recordInfoList.toArray(arr);
        for (int i = 0; i < arr.length; i++) {
            recordInfoRepository.save(arr[i]);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User editUser(User user) {
        User userUpdate = userRepository.findById(user.getId()).orElse(null);
        userUpdate.setP(user.getP());
        userUpdate.setN(user.getN());
        userUpdate.setM(user.getM());
        userUpdate.setI(user.getI());
        userUpdate.setR(user.getR());
        userUpdate.setTi(user.getTi());
        userUpdate.setTm(user.getTm());
        userUpdate.setTs(user.getTs());

        userUpdate = userRepository.save(userUpdate);
        List<RecordInfo> list = recordInfoRepository.findAllByUser_Id(user.getId());
        for (RecordInfo recordInfo : list) {
            recordInfoRepository.delete(recordInfo);
        }
        Simulation simulation = new Simulation();
        List<RecordInfo> recordInfoList = simulation.startSimulation(user);
        RecordInfo[] arr = new RecordInfo[recordInfoList.size()];
        recordInfoList.toArray(arr);
        for (RecordInfo recordInfo : arr) {
            recordInfoRepository.save(recordInfo);
        }

        return userUpdate;
    }

    public String deleteUser(long id) {
        List<RecordInfo> list = recordInfoRepository.findAllByUser_Id(id);
        for (RecordInfo recordInfo : list) {
            recordInfoRepository.delete(recordInfo);
        }
        userRepository.deleteById(id);
        return "removed";
    }

    public List<RecordInfo> getRecords(long id) {
        return recordInfoRepository.findAllByUser_Id(id);
    }
}
