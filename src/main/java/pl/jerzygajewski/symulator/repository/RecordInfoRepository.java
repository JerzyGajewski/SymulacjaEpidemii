package pl.jerzygajewski.symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jerzygajewski.symulator.entity.RecordInfoEntity;

import java.util.List;

@Repository
public interface RecordInfoRepository extends JpaRepository<RecordInfoEntity, Long> {
    @Query("select r from RecordInfoEntity r where r.user.id = ?1")
    List<RecordInfoEntity> findAllByUser_Id(long user_id);
}
