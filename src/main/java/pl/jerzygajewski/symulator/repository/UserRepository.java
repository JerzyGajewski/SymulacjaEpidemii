package pl.jerzygajewski.symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jerzygajewski.symulator.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
