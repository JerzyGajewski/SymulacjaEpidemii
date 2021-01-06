package pl.jerzygajewski.symulator.service;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.repository.RecordInfoRepository;
import pl.jerzygajewski.symulator.repository.UserRepository;
import pl.jerzygajewski.util.Simulation;

import java.util.List;

@Service
public class UserEntityService {

    private UserRepository userRepository;
    private RecordInfoRepository recordInfoRepository;
    private Simulation simulation;

    public UserEntityService(UserRepository userRepository, RecordInfoRepository recordInfoRepository) {
        this.userRepository = userRepository;
        this.recordInfoRepository = recordInfoRepository;
    }

    public User addParameters(User user){
            int daysGone = user.getTm();
            long[] sick = new long[user.getTs()];
            int z = 0;
            int zz = 0;
            long o = 0;
            long p = 0;
            long infected = 0;
            double mm = user.getM()/100;
            int daysSurvived = user.getTi();
        for (int i = 0; i < user.getTs(); i++) {
        RecordInfoEntity recordInfoEntity = new RecordInfoEntity();
            sick[i] =  countingPi(user, i);
           if(i == daysSurvived){
                double l = sick[zz] * mm;
                 o = sick[zz] - Math.round(l)+ o;
                recordInfoEntity.setPr(o);
                zz++;
                daysSurvived++;
            } else recordInfoEntity.setPr(0L);
           if(i == daysGone){
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

        return userRepository.save(user);
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

    public void Simulation(User user) {
        for (int i = 0; i < user.getTs(); i++) {
            RecordInfoEntity simulationFromDatas = simulation.startSimulation(user);
             recordInfoRepository.save(simulationFromDatas);
        }
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
