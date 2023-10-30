package edu.uga.cs.csci4830_project4.frontend.quizzes;

import android.util.Log;

import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

public class Quiz implements IQuiz {

    private static final String TAG = "Quiz";

    private final QuizDTO quizDTO;

    public Quiz(QuizDTO quizDTO) {
        this.quizDTO = quizDTO;
        Log.d(TAG, "Create quiz with quiz dto = " + quizDTO);
    }

    @Override
    public QuizDTO getQuizDTO() {
        return quizDTO;
    }

    @Override
    public String getQuestionAt(int index) {
        return quizDTO.getQuestions().get(index);
    }

    @Override
    public List<String> getChoicesAt(int index) {
        return quizDTO.getChoices().get(index);
    }

    @Override
    public String getResponseAt(int index) {
        return quizDTO.getResponses().get(index);
    }

    @Override
    public void setResponse(int index, String response) {
        quizDTO.setResponse(index, response);
    }

    @Override
    public int getSizeOfQuiz() {
        return quizDTO.getQuestions().size();
    }
}
