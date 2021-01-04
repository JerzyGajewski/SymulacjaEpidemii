package pl.jerzygajewski.symulator.service;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.repository.RecordInfoRepository;
import pl.jerzygajewski.symulator.repository.UserRepository;

import java.util.List;

@Service
public class UserEntityService {

    private UserRepository userRepository;
    private RecordInfoRepository recordInfoRepository;

    public UserEntityService(UserRepository userRepository, RecordInfoRepository recordInfoRepository) {
        this.userRepository = userRepository;
        this.recordInfoRepository = recordInfoRepository;
    }

    public User addParameters(User user){
            int daysGone = user.getTm();
            long[] sick = new long[user.getTs()];
            int z = 0;
            int daysSurvived = user.getTi();
        for (int i = 0; i < user.getTs(); i++) {
        RecordInfoEntity recordInfoEntity = new RecordInfoEntity();
            sick[i] =  countingPi(user, i, recordInfoEntity);
           if(i == daysGone){
                recordInfoEntity.setPm(sick[z] * user.getM());
                z++;
                daysGone++;
            }
            recordInfoRepository.save(recordInfoEntity);
        }

        return userRepository.save(user);
    }

    private long countingPi(User user, int i, RecordInfoEntity recordInfoEntity) {
        long count;
        if(i ==0){
             count = user.getI();
             recordInfoEntity.setPi(count);
        } else {
             count = user.getI() * Math.round(Math.pow(user.getR(), i));
             recordInfoEntity.setPi(count);
        }
        return count;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User editUser(User user){
        User userUpdate = userRepository.findById(user.getId()).orElse(null);
        userUpdate.setN(user.getN());
        userUpdate.setP(user.getP());
        userUpdate.setN(user.getN());
        userUpdate.setI(user.getI());
        userUpdate.setR(user.getR());
        userUpdate.setTi(user.getTi());
        userUpdate.setTm(user.getTm());
        userUpdate.setTs(user.getTs());

        return userRepository.save(user);
    }

    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "removed";
    }
}
