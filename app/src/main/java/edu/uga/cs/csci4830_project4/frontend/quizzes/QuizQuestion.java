package edu.uga.cs.csci4830_project4.frontend.quizzes;

import java.io.Serializable;
import java.util.List;

public record QuizQuestion(String question, String answer,
                           List<String> choices) implements Serializable {
}
