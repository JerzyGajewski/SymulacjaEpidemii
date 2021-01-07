package pl.jerzygajewski.symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jerzygajewski.symulator.entity.StartData;

@Repository
public interface StartDataRepository extends JpaRepository<StartData, Long> {
}
