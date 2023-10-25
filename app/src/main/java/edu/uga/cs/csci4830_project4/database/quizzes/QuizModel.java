package edu.uga.cs.csci4830_project4.database.quizzes;

/**
 * This class represents a row in the incomplete_quizzes table. It is used to store the state
 * capital quiz in progress. The stateIds and responses fields are comma-separated lists of
 * longs and strings respectively.
 */
public class QuizModel {

    private long id;
    private String[] stateIds;
    private String[] responses;
    private boolean finished;
    private String score;

    public QuizModel() {
        id = -1;
        stateIds = null;
        responses = null;
        finished = false;
        score = null;
    }

    /**
     * Gets the unique identifier (ID) associated with the quiz in progress.
     *
     * @return The ID of the quiz in progress.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier (ID) for the quiz in progress.
     *
     * @param id The new ID to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the comma-separated list of question IDs related to the quiz in progress. Each ID
     * is a long.
     *
     * @return The question IDs as a comma-separated string.
     */
    public String[] getStateIds() {
        return stateIds;
    }

    /**
     * Sets the comma-separated list of question IDs for the quiz in progress. Each ID should
     * be a long.
     *
     * @param stateIds The new question IDs as a comma-separated string to set.
     */
    public void setStateIds(String[] stateIds) {
        this.stateIds = stateIds;
    }

    /**
     * Gets the comma-separated list of question answers associated with the quiz in progress.
     *
     * @return The question answers as a comma-separated string.
     */
    public String[] getResponses() {
        return responses;
    }

    /**
     * Sets the comma-separated list of question answers for the quiz in progress.
     *
     * @param responses The new question answers as a comma-separated string to set.
     */
    public void setResponses(String[] responses) {
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
    public String getScore() {
        return score;
    }

    /**
     * Sets the score of the quiz.
     *
     * @param score The score of the quiz.
     */
    public void setScore(String score) {
        this.score = score;
    }
}