package br.com.mvp.educagames.service;

import br.com.mvp.educagames.dto.GameResponse;
import br.com.mvp.educagames.entity.Game;
import br.com.mvp.educagames.exception.ResourceNotFoundException;
import br.com.mvp.educagames.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional(readOnly = true)
    public List<GameResponse> listActiveGames() {
        return gameRepository.findAllByActiveTrueOrderByTitleAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Game getActiveBySlug(String slug) {
        Game game = gameRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo nao encontrado."));

        if (!Boolean.TRUE.equals(game.getActive())) {
            throw new ResourceNotFoundException("Jogo nao encontrado.");
        }

        return game;
    }

    @Transactional(readOnly = true)
    public GameResponse getBySlug(String slug) {
        return toResponse(getActiveBySlug(slug));
    }

    public GameResponse toResponse(Game game) {
        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getSlug(),
                game.getDescription(),
                game.getCategory(),
                game.getDifficulty(),
                game.getCoverImageUrl(),
                game.getPathUrl(),
                game.getMaxScore(),
                game.getActive()
        );
    }
}
