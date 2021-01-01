package pl.jerzygajewski.symulator.service.interfaces;


import pl.jerzygajewski.symulator.entity.UserEntity;

import java.util.List;

public interface UserEntityInterface {
    void saveUser(List<UserEntity> userEntityList);
}
