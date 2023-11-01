package edu.uga.cs.csci4830_project4.backend.quizzes;

import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.uga.cs.csci4830_project4.backend.contracts.IModelFactory;
import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;
import edu.uga.cs.csci4830_project4.common.QuizType;

/**
 * Factory for creating new quiz models.
 */
public class QuizModelFactory implements IModelFactory<QuizModel,
        QuizModelFactory.QuizModelFactoryParams> {

    /**
     * Parameters for creating a quiz model.
     *
     * @param quizType          the quiz type.
     * @param numberOfQuestions the number of questions.
     */
    public record QuizModelFactoryParams(QuizType quizType, int numberOfQuestions) {
    }

    private static final String TAG = "QuizModelFactory";

    private final QuizzesAccess quizzesAccess;
    private final StatesAccess statesAccess;

    /**
     * The factory's constructor. The {@link edu.uga.cs.csci4830_project4.backend.contracts.IAccess}
     * objects passed in are NOT opened or closed by this factory instance.
     *
     * @param quizzesAccess the quizzes access object to use for storing the quiz model.
     * @param statesAccess  the states access object to use for retrieving random states.
     */
    public QuizModelFactory(QuizzesAccess quizzesAccess, StatesAccess statesAccess) {
        this.quizzesAccess = quizzesAccess;
        this.statesAccess = statesAccess;
    }

    /**
     * Creates a new quiz model, stores it in the database, and then returns the model. The
     * questions are generated randomly using random U.S. states.
     *
     * @param params the parameters to use for creating the quiz model.
     * @return the quiz model.
     */
    public QuizModel createAndStore(QuizModelFactoryParams params) {
        QuizType quizType = params.quizType();
        int numberOfQuestions = params.numberOfQuestions();

        QuizModel quiz = new QuizModel();
        // set quiz type
        quiz.setQuizType(quizType);

        // fetch random states
        List<StateModel> randomStates = statesAccess.getRandomStates(numberOfQuestions);

        // set state names, questions, answers, choices, and responses
        List<String> stateNames = new ArrayList<>();
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        List<List<String>> choices = new ArrayList<>();
        List<String> responses = new ArrayList<>();
        randomStates.forEach(state -> {
            // set state name
            String stateName = state.getStateName();
            stateNames.add(stateName);

            // add null response
            responses.add("NULL");

            switch (quizType) {
                case CAPITALS_QUIZ -> {
                    // create question
                    String question = "What is the capital of " + stateName + "?";
                    questions.add(question);

                    // set answer
                    String answer = state.getCapitalCity();
                    answers.add(answer);

                    // generate shuffled list of choices
                    List<String> stateChoices = new ArrayList<>();
                    stateChoices.add(state.getCapitalCity());
                    stateChoices.add(state.getSecondCity());
                    stateChoices.add(state.getThirdCity());
                    Collections.shuffle(stateChoices);
                    choices.add(stateChoices);
                }
                default -> throw new IllegalArgumentException("Invalid quiz type: " + quizType);
            }
        });
        quiz.setStateNames(stateNames);
        quiz.setQuestions(questions);
        quiz.setAnswers(answers);
        quiz.setChoices(choices);
        quiz.setResponses(responses);

        // set time created and updated
        LocalDateTime timeCreated = LocalDateTime.now();
        quiz.setTimeCreated(timeCreated);
        quiz.setTimeUpdated(timeCreated);

        Log.d(TAG, "createAndStore(): quiz before storing = " + quiz);

        // store and return the model
        QuizModel quizModelAfterStore = quizzesAccess.store(quiz);
        Log.d(TAG, "createAndStore(): quiz after storing = " + quizModelAfterStore);
        return quizModelAfterStore;
    }
}
