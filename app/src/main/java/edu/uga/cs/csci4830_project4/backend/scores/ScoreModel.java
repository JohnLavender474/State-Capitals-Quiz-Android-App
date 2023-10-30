package edu.uga.cs.csci4830_project4.backend.scores;

import java.util.Objects;

import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

public class ScoreModel implements IModel {

    private long id;
    private String score;

    public ScoreModel() {
        id = -1;
    }

    public ScoreModel(long id, String score) {
        this.id = id;
        this.score = score;
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

    @Override
    public boolean equals(Object o) {
        return o instanceof ScoreModel s && s.id == id && s.score.equals(score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }

    @Override
    public String toString() {
        return "ScoreModel{" + "id=" + id + ", score='" + score + '\'' + '}';
    }
}
