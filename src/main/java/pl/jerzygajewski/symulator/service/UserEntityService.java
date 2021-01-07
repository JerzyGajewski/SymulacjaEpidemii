package pl.jerzygajewski.symulator.service;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;
import pl.jerzygajewski.symulator.entity.User;
import pl.jerzygajewski.symulator.repository.RecordInfoRepository;
import pl.jerzygajewski.symulator.repository.UserRepository;
import pl.jerzygajewski.util.Symulation;

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

    public User addParameters(User user){
        user = userRepository.save(user);

        Symulation symulation = new Symulation();
        List<RecordInfoEntity> recordInfoEntityList = symulation.startSimulation(user);
        for(RecordInfoEntity recordInfoEntity : recordInfoEntityList){
            recordInfoRepository.save(recordInfoEntity);
        }

        return user;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
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

    public String deleteUser(long id){
        List<RecordInfoEntity> list = recordInfoRepository.findAllByUser_Id(id);
        for(RecordInfoEntity recordInfoEntity : list) {
            recordInfoRepository.delete(recordInfoEntity);
        }
        userRepository.deleteById(id);
        return "removed";
    }

    public List<RecordInfoEntity> getRecords(long id) {
        return recordInfoRepository.findAllByUser_Id(id);
    }
}
