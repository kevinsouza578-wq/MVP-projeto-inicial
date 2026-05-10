package br.com.mvp.educagames.service;

import br.com.mvp.educagames.dto.RankingEntryResponse;
import br.com.mvp.educagames.entity.User;
import br.com.mvp.educagames.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService {

    private final UserRepository userRepository;
    private final LevelService levelService;

    public RankingService(UserRepository userRepository, LevelService levelService) {
        this.userRepository = userRepository;
        this.levelService = levelService;
    }

    @Transactional(readOnly = true)
    public List<RankingEntryResponse> getGeneralRanking() {
        List<User> users = userRepository.findAllByOrderByTotalScoreDescUsernameAsc();
        List<RankingEntryResponse> ranking = new ArrayList<>();

        for (int index = 0; index < users.size(); index++) {
            User user = users.get(index);
            ranking.add(new RankingEntryResponse(
                    index + 1,
                    user.getUsername(),
                    user.getTotalScore(),
                    levelService.calculateLevel(user.getTotalScore())
            ));
        }

        return ranking;
    }
}
