package br.com.mvp.educagames.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String title;

    @Column(nullable = false, unique = true, length = 90)
    private String slug;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 80)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Column(length = 500)
    private String coverImageUrl;

    @Column(nullable = false, length = 500)
    private String pathUrl;

    @Column(nullable = false)
    private Integer maxScore;

    @Column(nullable = false)
    private Boolean active = true;

    protected Game() {
    }

    public Game(String title, String slug, String description, String category, Difficulty difficulty,
                String coverImageUrl, String pathUrl, Integer maxScore, Boolean active) {
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.coverImageUrl = coverImageUrl;
        this.pathUrl = pathUrl;
        this.maxScore = maxScore;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public Boolean getActive() {
        return active;
    }
}
