package edu.uga.cs.csci4830_project4.frontend.dto;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

import edu.uga.cs.csci4830_project4.backend.scores.ScoreModel;

/**
 * DTO for a score model.
 */
public class ScoreDTO implements Serializable {

    private long id;
    private String score;

    public ScoreDTO() {
        id = -1;
    }

    public ScoreDTO(long id, String score) {
        this.id = id;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ScoreDTO s && s.id == id && s.score.equals(score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }

    @NonNull
    @Override
    public String toString() {
        return "ScoreModelDTO{" + "id=" + id + ", score='" + score + '\'' + '}';
    }

    /**
     * Converts a score model to a score model DTO.
     *
     * @param model the score model to convert.
     * @return the score model DTO.
     */
    public static ScoreDTO fromModel(ScoreModel model) {
        return new ScoreDTO(model.getId(), model.getScore());
    }

    /**
     * Converts a score model DTO to a score model.
     *
     * @return the score model.
     */
    public ScoreModel toModel() {
        ScoreModel model = new ScoreModel();
        model.setId(id);
        model.setScore(score);
        return model;
    }
}

