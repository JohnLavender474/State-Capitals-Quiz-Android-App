package edu.uga.cs.csci4830_project4.frontend.dto;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import edu.uga.cs.csci4830_project4.backend.scores.ScoreModel;
import edu.uga.cs.csci4830_project4.common.QuizType;

/**
 * DTO for a score model.
 */
public class ScoreDTO implements Serializable {

    private long id;
    private String score;
    private QuizType quizType;
    private LocalDateTime timeCompleted;

    public ScoreDTO() {
        id = -1;
    }

    public ScoreDTO(long id, String score, QuizType quizType, LocalDateTime timeCompleted) {
        this.id = id;
        this.score = score;
        this.quizType = quizType;
        this.timeCompleted = timeCompleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public LocalDateTime getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(LocalDateTime timeCompleted) {
        this.timeCompleted = timeCompleted;
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
        return new ScoreDTO(model.getId(), model.getScore(), model.getQuizType(),
                model.getTimeCompleted());
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
        model.setTimeCompleted(timeCompleted);
        return model;
    }

}

