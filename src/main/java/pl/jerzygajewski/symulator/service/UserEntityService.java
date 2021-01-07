package pl.jerzygajewski.symulator.service;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
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
    private Simulation simulation;
    public UserEntityService(UserRepository userRepository, RecordInfoRepository recordInfoRepository) {
        this.userRepository = userRepository;
        this.recordInfoRepository = recordInfoRepository;
    }

    public User addParameters(User user){
        user = userRepository.save(user);
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
            recordInfoRepository.save(recordInfoEntity);
        }

        return user;

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


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
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

        return userUpdate;
    }

    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "removed";
    }


    public List<RecordInfoEntity> getRecords(long id) {
        List<RecordInfoEntity> recordInfoList =  recordInfoRepository.findAllByUser_Id(id);
        return recordInfoList;
    }
}
