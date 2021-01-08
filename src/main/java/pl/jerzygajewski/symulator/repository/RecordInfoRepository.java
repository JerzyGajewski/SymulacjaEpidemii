package pl.jerzygajewski.symulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jerzygajewski.symulator.entity.RecordInfo;

import java.util.List;

@Repository
public interface RecordInfoRepository extends JpaRepository<RecordInfo, Long> {
    @Query("select r from RecordInfo r where r.startData.id = ?1")
    List<RecordInfo> findAllByStartData_Id(long user_id);
}
