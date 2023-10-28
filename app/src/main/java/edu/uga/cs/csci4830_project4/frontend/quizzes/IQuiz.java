package edu.uga.cs.csci4830_project4.frontend.quizzes;

import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.activities.QuizActivity;

/**
 * This interface represents the logic for a quiz. It is used by the QuizActivity to interact with
 * the quiz. The {@link QuizActivity} should not know the details of how the quiz works, only how
 * to interact with it.
 */
public interface IQuiz {

    /**
     * Get the question at the index.
     *
     * @param index The index of the question to get.
     * @return The question at the index.
     */
    String getQuestionAt(int index);

    /**
     * Get the choices for the question at the index.
     *
     * @param index The index of the question to get the choices for.
     * @return The choices for the question at the index.
     */
    List<String> getChoicesAt(int index);

    /**
     * Get the answer for the question at the index.
     *
     * @param index The index of the question to get the answer for.
     * @return The answer for the question at the index.
     */
    String getAnswerAt(int index);

    /**
     * Get the size of the quiz.
     *
     * @return The size of the quiz.
     */
    int getSizeOfQuiz();
}
