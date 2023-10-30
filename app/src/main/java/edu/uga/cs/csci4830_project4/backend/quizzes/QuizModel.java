package edu.uga.cs.csci4830_project4.backend.quizzes;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

/**
 * This class represents a row in the incomplete_quizzes table. It is used to store the state
 * capital quiz in progress. The stateNames and responses fields are comma-separated lists of
 * longs and strings respectively.
 */
public class QuizModel implements IModel {

    private long id;
    private QuizType quizType;
    private List<String> stateNames;
    private List<String> questions;
    private List<List<String>> choices;
    private List<String> responses;
    private List<String> answers;

    public QuizModel() {
        id = -1;
        questions = null;
        choices = null;
        responses = null;
        answers = null;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    public List<List<String>> getChoices() {
        return choices;
    }

    public void setChoices(List<List<String>> choices) {
        this.choices = choices;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getStateNames() {
        return stateNames;
    }

    public void setStateNames(List<String> stateNames) {
        this.stateNames = stateNames;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof QuizModel q && q.id == id && Objects.equals(q.questions, questions) &&
                Objects.equals(q.responses, responses) && Objects.equals(q.choices, choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questions, responses, choices);
    }

    @NonNull
    @Override
    public String toString() {
        return "QuizModel{" +
                "id=" + id +
                ", quizType=" + quizType +
                ", stateNames=" + stateNames +
                ", questions=" + questions +
                ", choices=" + choices +
                ", responses=" + responses +
                ", answers=" + answers +
                '}';
    }
}
