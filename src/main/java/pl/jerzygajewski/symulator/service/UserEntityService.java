package pl.jerzygajewski.symulator.service;

import org.springframework.stereotype.Service;
import pl.jerzygajewski.symulator.entity.UserEntity;
import pl.jerzygajewski.symulator.repository.UserRepository;
import pl.jerzygajewski.symulator.service.interfaces.UserEntityInterface;

import java.util.List;

@Service
public class UserEntityService implements UserEntityInterface {

    private UserRepository userRepository;

    public UserEntityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(List<UserEntity> userEntityList) {
        for (UserEntity userEntity : userEntityList) {
            userRepository.save(userEntity);
        }
    }
}
