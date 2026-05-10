package br.com.mvp.educagames;

import br.com.mvp.educagames.service.LevelService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelServiceTest {

    private final LevelService levelService = new LevelService();

    @Test
    void shouldCalculateLevelsFromScoreThresholds() {
        assertThat(levelService.calculateLevel(0)).isEqualTo(1);
        assertThat(levelService.calculateLevel(99)).isEqualTo(1);
        assertThat(levelService.calculateLevel(100)).isEqualTo(2);
        assertThat(levelService.calculateLevel(299)).isEqualTo(2);
        assertThat(levelService.calculateLevel(300)).isEqualTo(3);
        assertThat(levelService.calculateLevel(599)).isEqualTo(3);
        assertThat(levelService.calculateLevel(600)).isEqualTo(4);
        assertThat(levelService.calculateLevel(999)).isEqualTo(4);
        assertThat(levelService.calculateLevel(1000)).isEqualTo(5);
        assertThat(levelService.calculateLevel(1499)).isEqualTo(5);
        assertThat(levelService.calculateLevel(1500)).isEqualTo(6);
    }
}
