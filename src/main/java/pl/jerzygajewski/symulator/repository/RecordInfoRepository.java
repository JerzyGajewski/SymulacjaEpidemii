package pl.jerzygajewski.symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;

@Repository
public interface RecordInfoRepository extends JpaRepository<RecordInfoEntity, Long> {
}
