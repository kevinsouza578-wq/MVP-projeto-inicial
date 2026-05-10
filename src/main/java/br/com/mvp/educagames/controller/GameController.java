package br.com.mvp.educagames.controller;

import br.com.mvp.educagames.dto.GameResponse;
import br.com.mvp.educagames.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<GameResponse> listGames() {
        return gameService.listActiveGames();
    }

    @GetMapping("/{slug}")
    public GameResponse getGame(@PathVariable String slug) {
        return gameService.getBySlug(slug);
    }
}
