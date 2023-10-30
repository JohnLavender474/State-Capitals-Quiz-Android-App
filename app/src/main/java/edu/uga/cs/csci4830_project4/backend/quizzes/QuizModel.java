package edu.uga.cs.csci4830_project4.backend.quizzes;

import java.util.List;
import java.util.Objects;

import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

/**
 * This class represents a row in the incomplete_quizzes table. It is used to store the state
 * capital quiz in progress. The stateIds and responses fields are comma-separated lists of
 * longs and strings respectively.
 */
public class QuizModel implements IModel {

    private long id;
    private List<String> questions;
    private List<String> choices;
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

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
