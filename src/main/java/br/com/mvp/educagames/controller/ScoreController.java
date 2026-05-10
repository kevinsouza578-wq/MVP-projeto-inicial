package br.com.mvp.educagames.controller;

import br.com.mvp.educagames.dto.ScoreResponse;
import br.com.mvp.educagames.dto.ScoreSubmitRequest;
import br.com.mvp.educagames.security.UserPrincipal;
import br.com.mvp.educagames.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("/submit")
    public ScoreResponse submit(@AuthenticationPrincipal UserPrincipal principal,
                                @Valid @RequestBody ScoreSubmitRequest request) {
        return scoreService.submitScore(principal.getId(), request);
    }
}
