package edu.uga.cs.csci4830_project4.backend.quizzes;

import java.util.List;

import edu.uga.cs.csci4830_project4.backend.contracts.IModel;

/**
 * This class represents a row in the incomplete_quizzes table. It is used to store the state
 * capital quiz in progress. The stateIds and responses fields are comma-separated lists of
 * longs and strings respectively.
 */
public class QuizModel implements IModel {

    private long id;
    private QuizType quizType;
    private List<Long> stateIds;
    private List<String> responses;
    private boolean finished;
    private int score;

    public QuizModel() {
        id = -1;
        stateIds = null;
        responses = null;
        finished = false;
        score = 0;
    }

    /**
     * Gets the unique identifier (ID) associated with the quiz in progress.
     *
     * @return The ID of the quiz in progress.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier (ID) for the quiz in progress.
     *
     * @param id The new ID to set.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the comma-separated list of question IDs related to the quiz in progress. Each ID
     * is a long.
     *
     * @return The question IDs as a comma-separated string.
     */
    public List<Long> getStateIds() {
        return stateIds;
    }

    /**
     * Sets the comma-separated list of question IDs for the quiz in progress. Each ID should
     * be a long.
     *
     * @param stateIds The new question IDs as a comma-separated string to set.
     */
    public void setStateIds(List<Long> stateIds) {
        this.stateIds = stateIds;
    }

    /**
     * Gets the comma-separated list of question answers associated with the quiz in progress.
     *
     * @return The question answers as a comma-separated string.
     */
    public List<String> getResponses() {
        return responses;
    }

    /**
     * Sets the comma-separated list of question answers for the quiz in progress.
     *
     * @param responses The new question answers as a comma-separated string to set.
     */
    public void setResponses(List<String> responses) {
        this.responses = responses;
    }

    /**
     * Gets whether the quiz has been completed.
     *
     * @return True if the quiz has been completed, false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets whether the quiz has been completed.
     *
     * @param finished True if the quiz has been completed, false otherwise.
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Gets the score of the quiz.
     *
     * @return The score of the quiz.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the quiz.
     *
     * @param score The score of the quiz.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the type of quiz.
     *
     * @return The type of quiz.
     */
    public QuizType getQuizType() {
        return quizType;
    }

    /**
     * Sets the type of quiz.
     *
     * @param quizType The type of quiz.
     */
    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }
}
