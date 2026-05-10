package br.com.mvp.educagames.repository;

import br.com.mvp.educagames.entity.ScoreRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecord, Long> {

    List<ScoreRecord> findTop20ByUserIdOrderByPlayedAtDesc(Long userId);
}
