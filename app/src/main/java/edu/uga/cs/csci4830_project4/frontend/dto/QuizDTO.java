package edu.uga.cs.csci4830_project4.frontend.dto;

import java.io.Serializable;
import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.quizzes.QuizQuestion;

public record QuizDTO(long quizId, List<QuizQuestion> questions,
                      List<String> userResponses) implements Serializable {
}

