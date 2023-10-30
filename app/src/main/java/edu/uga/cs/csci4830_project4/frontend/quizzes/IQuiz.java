package edu.uga.cs.csci4830_project4.frontend.quizzes;

import java.io.Serializable;
import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.activities.QuizActivity;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

/**
 * This interface represents the logic for a quiz. It is used by the QuizActivity to interact with
 * the quiz. The {@link QuizActivity} should not know the details of how the quiz works, only how
 * to interact with it.
 */
public interface IQuiz extends Serializable {

    /**
     * Get the quiz DTO used for this quiz.
     *
     * @return The quiz DTO used for this quiz.
     */
    QuizDTO getQuizDTO();

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
     * Submit the answer for the question at the index.
     *
     * @param index    The index of the question to submit the answer for.
     * @param response The answer to the question.
     */
    void setResponse(int index, String response);

    /**
     * Get the size of the quiz.
     *
     * @return The size of the quiz.
     */
    int getSizeOfQuiz();
}
