package edu.uga.cs.csci4830_project4.backend.quizzes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;

/**
 * Factory for creating new quiz models.
 */
public class QuizModelFactory {

    private final QuizzesAccess quizzesAccess;
    private final StatesAccess statesAccess;

    public QuizModelFactory(QuizzesAccess quizzesAccess, StatesAccess statesAccess) {
        this.quizzesAccess = quizzesAccess;
        this.statesAccess = statesAccess;
    }

    /**
     * Creates a new quiz model, stores it in the database, and then returns the model.
     *
     * @param quizType          the type of quiz to create.
     * @param numberOfQuestions the number of questions to include in the quiz.
     * @return the quiz model.
     */
    public QuizModel createAndStore(QuizType quizType, int numberOfQuestions) {
        QuizModel quiz = new QuizModel();

        quiz.setQuizType(quizType);

        List<StateModel> randomStates = statesAccess.getRandomStates(numberOfQuestions);
        List<String> stateNames = new ArrayList<>();
        randomStates.forEach(state -> stateNames.add(state.getStateName()));
        quiz.setStateNames(stateNames);

        List<String> questions = new ArrayList<>();
        stateNames.forEach(stateName -> {
            switch (quizType) {
                case CAPITALS_QUIZ -> {
                    String question = "What is the capital of " + stateName + "?";
                    questions.add(question);
                }
                default -> throw new IllegalArgumentException("Invalid quiz type: " + quizType);
            }
        });
        quiz.setQuestions(questions);

        List<List<String>> choices = new ArrayList<>();
        randomStates.forEach(state -> {
            List<String> stateChoices = new ArrayList<>();
            stateChoices.add(state.getCapitalCity());
            stateChoices.add(state.getSecondCity());
            stateChoices.add(state.getThirdCity());
            Collections.shuffle(stateChoices);
            choices.add(stateChoices);
        });
        quiz.setChoices(choices);

        List<String> responses = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            responses.add("");
        }
        quiz.setResponses(responses);

        List<String> answers = new ArrayList<>();
        randomStates.forEach(state -> {
            switch (quizType) {
                case CAPITALS_QUIZ -> answers.add(state.getCapitalCity());
                default -> throw new IllegalArgumentException("Invalid quiz type: " + quizType);
            }
        });
        quiz.setAnswers(answers);

        return quizzesAccess.store(quiz);
    }
}
