package edu.uga.cs.csci4830_project4.frontend.quizzes;

import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

public class Quiz implements IQuiz {

    private final QuizDTO quizDTO;

    public Quiz(QuizDTO quizDTO) {
        this.quizDTO = quizDTO;
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
    public boolean submitResponse(int index, String response) {
        return quizDTO.setResponse(index, response);
    }

    @Override
    public int getSizeOfQuiz() {
        return quizDTO.getQuestions().size();
    }
}
