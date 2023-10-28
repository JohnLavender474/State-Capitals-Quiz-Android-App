package edu.uga.cs.csci4830_project4.backend.quizzes;

import androidx.annotation.Nullable;

import java.util.ArrayList;
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

    private int numberOfQuestions;
    private int currentQuestion;
    @Nullable
    private QuizType quizType;
    @Nullable
    private List<Long> stateIds;
    @Nullable
    private List<String> choices;
    @Nullable
    private List<String> responses;
    @Nullable
    private List<Boolean> answeredCorrectly;
    private boolean finished;
    private int score;

    /**
     * Constructs a new {@link QuizModel} instance with the provided number of questions.
     *
     * @param numberOfQuestions The number of questions in the quiz.
     */
    public QuizModel(int numberOfQuestions) {
        id = -1;
        score = 0;
        finished = false;
        currentQuestion = 0;
        stateIds = new ArrayList<>();
        responses = new ArrayList<>();
        choices = new ArrayList<>();
        answeredCorrectly = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            stateIds.add(null);
            choices.add(null);
            responses.add(null);
            answeredCorrectly.add(false);
        }
        this.numberOfQuestions = numberOfQuestions;
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
     * Gets the number of questions in the quiz.
     *
     * @return The number of questions in the quiz.
     */
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    /**
     * Gets the comma-separated list of choices for the current question in the quiz.
     *
     * @return The choices for the current question as a comma-separated string.
     */
    @Nullable
    public List<String> getChoices() {
        return choices;
    }

    /**
     * Sets the comma-separated list of choices for the current question in the quiz.
     *
     * @param choices The new choices for the current question as a comma-separated string to set.
     */
    public void setChoices(@Nullable List<String> choices) {
        this.choices = choices;
    }

    /**
     * Gets the comma-separated list of question IDs related to the quiz in progress. Each ID
     * is a long.
     *
     * @return The question IDs as a comma-separated string.
     */
    @Nullable
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
        if (stateIds.size() != numberOfQuestions) {
            throw new IllegalArgumentException("stateIds must be the same size as the number of " +
                    "questions in the quiz");
        }
        this.stateIds = stateIds;
    }

    /**
     * Gets the comma-separated list of question answers associated with the quiz in progress.
     *
     * @return The question answers as a comma-separated string.
     */
    @Nullable
    public List<String> getResponses() {
        return responses;
    }

    /**
     * Sets the comma-separated list of question answers for the quiz in progress.
     *
     * @param responses The new question answers as a comma-separated string to set.
     */
    public void setResponses(List<String> responses) {
        if (responses.size() != numberOfQuestions) {
            throw new IllegalArgumentException("responses must be the same size as the number of " +
                    "questions in the quiz");
        }
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
    @Nullable
    public QuizType getQuizType() {
        return quizType;
    }

    /**
     * Sets the type of quiz.
     *
     * @param quizType The type of quiz.
     */
    public void setQuizType(@Nullable QuizType quizType) {
        this.quizType = quizType;
    }

    /**
     * Gets the list of whether the user answered each question correctly.
     *
     * @return The list of whether the user answered each question correctly.
     */
    @Nullable
    public List<Boolean> getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    /**
     * Sets the list of whether the user answered each question correctly.
     *
     * @param answeredCorrectly The list of whether the user answered each question correctly.
     */
    public void setAnsweredCorrectly(List<Boolean> answeredCorrectly) {
        if (answeredCorrectly.size() != numberOfQuestions) {
            throw new IllegalArgumentException("answeredCorrectly must be the same size as the " +
                    "number of questions in the quiz");
        }
        this.answeredCorrectly = answeredCorrectly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizModel quizModel = (QuizModel) o;
        return id == quizModel.id && finished == quizModel.finished && score == quizModel.score
                && quizType == quizModel.quizType && Objects.equals(stateIds, quizModel.stateIds) &&
                Objects.equals(responses, quizModel.responses) &&
                Objects.equals(answeredCorrectly, quizModel.answeredCorrectly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quizType, stateIds, responses, answeredCorrectly, finished, score);
    }

    /**
     * Gets the index of the current question in the quiz.
     *
     * @return The index of the current question.
     */
    public int getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Set the index of the current question in the quiz.
     *
     * @param currentQuestion The index of the current question.
     */
    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
}
