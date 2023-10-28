package edu.uga.cs.csci4830_project4.frontend.quizzes.impl;

import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuiz;

public class CapitalsQuiz implements IQuiz {

    private final QuizDTO quiz;

    public CapitalsQuiz(QuizDTO quiz) {
        this.quiz = quiz;
    }

    @Override
    public String getQuestionAt(int index) {
        return quiz.questions().get(index).question();
    }

    @Override
    public List<String> getChoicesAt(int index) {
        return quiz.questions().get(index).choices();
    }

    @Override
    public String getAnswerAt(int index) {
        return quiz.questions().get(index).answer();
    }

    @Override
    public int getSizeOfQuiz() {
        return quiz.questions().size();
    }
}
