package edu.uga.cs.csci4830_project4.frontend.quizzes;

import edu.uga.cs.csci4830_project4.frontend.activities.QuizActivity;

/**
 * This interface represents the logic for a quiz. It is used by the QuizActivity to interact with
 * the quiz. The {@link QuizActivity} should not know the details of how the quiz works, only how
 * to interact with it.
 */
public interface IQuizLogic {

    /**
     * Get the current question and options for the quiz.
     *
     * @return A QuizQuestion object representing the current question.
     */
    String getCurrentQuestion();

    /**
     * Go to the next question in the quiz.
     */
    void goToNextQuestion();

    /**
     * Check if the quiz has more questions.
     *
     * @return True if there are more questions, false otherwise.
     */
    boolean hasMoreQuestions();

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
