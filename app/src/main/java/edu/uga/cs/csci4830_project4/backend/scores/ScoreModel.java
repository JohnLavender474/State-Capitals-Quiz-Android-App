package edu.uga.cs.csci4830_project4.backend.scores;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import edu.uga.cs.csci4830_project4.backend.contracts.IModel;
import edu.uga.cs.csci4830_project4.common.QuizType;

/**
 * This class represents a row in the scores table.
 */
public class ScoreModel implements IModel {

    private long id;
    private String score;
    private QuizType quizType;
    private LocalDateTime timeCompleted;

    /**
     * Constructs a new ScoreModel with an id of -1.
     */
    public ScoreModel() {
        id = -1;
    }

    /**
     * Constructs a new ScoreModel with the given id and score.
     *
     * @param id    The id.
     * @param score The score.
     */
    public ScoreModel(long id, String score, QuizType quizType, LocalDateTime timeCompleted) {
        this.id = id;
        this.score = score;
        this.quizType = quizType;
        this.timeCompleted = timeCompleted;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    public LocalDateTime getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(LocalDateTime timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ScoreModel s && s.id == id && s.score.equals(score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }

    @NonNull
    @Override
    public String toString() {
        return "ScoreModel{" + "id=" + id + ", quizType = " + quizType + "score='" + score + '\'' + '}';
    }

}
