package br.com.mvp.educagames.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "score_records")
public class ScoreRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false, updatable = false)
    private Instant playedAt;

    protected ScoreRecord() {
    }

    public ScoreRecord(User user, Game game, Integer score) {
        this.user = user;
        this.game = game;
        this.score = score;
    }

    @PrePersist
    void prePersist() {
        if (playedAt == null) {
            playedAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }

    public Integer getScore() {
        return score;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }
}
