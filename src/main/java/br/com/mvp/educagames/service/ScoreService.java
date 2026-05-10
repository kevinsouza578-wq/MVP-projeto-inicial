package br.com.mvp.educagames.service;

import br.com.mvp.educagames.dto.ScoreResponse;
import br.com.mvp.educagames.dto.ScoreSubmitRequest;
import br.com.mvp.educagames.entity.Game;
import br.com.mvp.educagames.entity.ScoreRecord;
import br.com.mvp.educagames.entity.User;
import br.com.mvp.educagames.exception.BadRequestException;
import br.com.mvp.educagames.repository.ScoreRecordRepository;
import br.com.mvp.educagames.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreService {

    private final ScoreRecordRepository scoreRecordRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final GameService gameService;
    private final LevelService levelService;

    public ScoreService(ScoreRecordRepository scoreRecordRepository, UserRepository userRepository,
                        UserService userService, GameService gameService, LevelService levelService) {
        this.scoreRecordRepository = scoreRecordRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.gameService = gameService;
        this.levelService = levelService;
    }

    @Transactional
    public ScoreResponse submitScore(Long userId, ScoreSubmitRequest request) {
        User user = userService.getById(userId);
        Game game = gameService.getActiveBySlug(request.gameSlug());

        if (request.score() > game.getMaxScore()) {
            throw new BadRequestException("Pontuacao maior que a pontuacao maxima do jogo.");
        }

        ScoreRecord record = scoreRecordRepository.save(new ScoreRecord(user, game, request.score()));
        user.addScore(request.score());
        userRepository.save(user);

        return new ScoreResponse(
                record.getId(),
                game.getSlug(),
                record.getScore(),
                user.getTotalScore(),
                levelService.calculateLevel(user.getTotalScore()),
                record.getPlayedAt()
        );
    }
}
