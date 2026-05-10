package br.com.mvp.educagames.config;

import br.com.mvp.educagames.entity.Difficulty;
import br.com.mvp.educagames.entity.Game;
import br.com.mvp.educagames.entity.User;
import br.com.mvp.educagames.repository.GameRepository;
import br.com.mvp.educagames.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(GameRepository gameRepository, UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            seedGames(gameRepository);
            seedUsers(userRepository, passwordEncoder);
        };
    }

    private void seedGames(GameRepository gameRepository) {
        if (!gameRepository.existsBySlug("quiz-historia")) {
            gameRepository.save(new Game(
                    "Quiz Historia",
                    "quiz-historia",
                    "Perguntas rapidas de historia para validar a integracao de jogos com pontuacao.",
                    "Historia",
                    Difficulty.EASY,
                    "/img/covers/quiz-historia.svg",
                    "/games/quiz-historia/index.html",
                    100,
                    true
            ));
        }

        if (!gameRepository.existsBySlug("matematica-relampago")) {
            gameRepository.save(new Game(
                    "Matematica Relampago",
                    "matematica-relampago",
                    "Desafios simples de multiplicacao em sequencia, usado como placeholder jogavel.",
                    "Matematica",
                    Difficulty.MEDIUM,
                    "/img/covers/matematica-relampago.svg",
                    "/games/matematica-relampago/index.html",
                    120,
                    true
            ));
        }
    }

    private void seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if (!userRepository.existsByUsername("demo")) {
            userRepository.save(new User(
                    "Jogador Demo",
                    "demo",
                    "demo@educagames.local",
                    passwordEncoder.encode("123456"),
                    null
            ));
        }
    }
}
