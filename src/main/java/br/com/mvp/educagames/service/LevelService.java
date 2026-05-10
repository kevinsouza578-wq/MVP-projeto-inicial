package br.com.mvp.educagames.service;

import org.springframework.stereotype.Service;

@Service
public class LevelService {

    public int calculateLevel(int totalScore) {
        if (totalScore >= 1500) {
            return 6;
        }
        if (totalScore >= 1000) {
            return 5;
        }
        if (totalScore >= 600) {
            return 4;
        }
        if (totalScore >= 300) {
            return 3;
        }
        if (totalScore >= 100) {
            return 2;
        }
        return 1;
    }
}
