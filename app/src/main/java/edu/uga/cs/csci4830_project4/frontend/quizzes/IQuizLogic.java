package edu.uga.cs.csci4830_project4.frontend.quizzes;

import java.io.Serializable;
import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.activities.QuizActivity;

/**
 * This interface represents the logic for a quiz. It is used by the QuizActivity to interact with
 * the quiz. The {@link QuizActivity} should not know the details of how the quiz works, only how
 * to interact with it.
 */
public interface IQuizLogic extends Serializable {

    /**
     * Get the name of the current state in the quiz.
     *
     * @return The name of the current state.
     */
    String getCurrentStateName();

    /**
     * Get the current question and options for the quiz.
     *
     * @return A QuizQuestion object representing the current question.
     */
    String getCurrentQuestion();

    /**
     * Get the current choices for the quiz.
     *
     * @return A list of the current choices.
     */
    List<String> getCurrentChoices();

    /**
     * Get the index of the current question in the quiz.
     *
     * @return The index of the current question.
     */
    int getCurrentQuestionIndex();

    /**
     * Get the current response to the question.
     *
     * @return The current response.
     */
    String getCurrentResponse();

    /**
     * Set the index of the current question in the quiz.
     *
     * @param currentQuestionIndex The index of the current question.
     * @return True if the index was set, false otherwise.
     */
    boolean setCurrentQuestionIndex(int currentQuestionIndex);

    /**
     * Get the size of the quiz.
     *
     * @return The size of the quiz.
     */
    int getSizeOfQuiz();

    /**
     * Check if the quiz is at the end.
     *
     * @return True if the quiz is at the end, false otherwise.
     */
    boolean atEndOfQuiz();

    /**
     * Check if the quiz is at the start.
     *
     * @return True if the quiz is at the start, false otherwise.
     */
    boolean atStartOfQuiz();

    /**
     * Go to the next question in the quiz.
     *
     * @return True if the next question was found, false otherwise.
     */
    boolean goToNextQuestion();

    /**
     * Go to the previous question in the quiz.
     *
     * @return True if the previous question was found, false otherwise.
     */
    boolean goToPreviousQuestion();

    /**
     * Submit a user response to the current question.
     *
     * @param userResponse The user's response to the current question.
     */
    void submitResponse(String userResponse);

    /**
     * Get the user's score in the quiz.
     *
     * @return The user's score.
     */
    int getScore();

    /**
     * Finish the quiz and calculate the final score.
     */
    void finishQuiz();
}
