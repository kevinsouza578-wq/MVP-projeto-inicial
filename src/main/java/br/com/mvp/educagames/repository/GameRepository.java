package br.com.mvp.educagames.repository;

import br.com.mvp.educagames.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Game> findAllByActiveTrueOrderByTitleAsc();
}
