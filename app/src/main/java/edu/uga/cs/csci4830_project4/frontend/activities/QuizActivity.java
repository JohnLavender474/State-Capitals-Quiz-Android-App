package edu.uga.cs.csci4830_project4.frontend.activities;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizType;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuizLogic;
import edu.uga.cs.csci4830_project4.frontend.quizzes.impl.CapitalsQuizLogic;

public class QuizActivity extends Activity {

    private QuizzesAccess quizzesAccess;
    private StatesAccess statesAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        quizzesAccess = new QuizzesAccess(this);
        statesAccess = new StatesAccess(this);

        // Get the QuizModel from the intent
        QuizModel quiz = (QuizModel) getIntent().getSerializableExtra("quiz");
        if (quiz == null) {
            throw new IllegalStateException("Quiz not found in intent");
        }

        // Determine the quiz type and fetch the states
        List<StateModel> states = fetchStatesForQuiz(quiz);

        // Initialize the quiz logic based on the type
        IQuizLogic quizLogic = createQuizLogic(quiz, states);
        if (quizLogic == null) {
            throw new IllegalStateException("Logic not found for quiz type: " + quiz.getQuizType());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (quizzesAccess != null) {
            quizzesAccess.open();
        }
        if (statesAccess != null) {
            statesAccess.open();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (quizzesAccess != null) {
            quizzesAccess.close();
        }
        if (statesAccess != null) {
            statesAccess.close();
        }
    }

    // TODO: use async task for this
    private List<StateModel> fetchStatesForQuiz(QuizModel quiz) {
        List<StateModel> states = new ArrayList<>();

        // Fetch the states for the quiz
        quiz.getStateIds().forEach(id -> {
            StateModel state = statesAccess.getById(id);
            if (state != null) {
                states.add(state);
            }
        });

        return states;
    }

    private IQuizLogic createQuizLogic(QuizModel quizModel, List<StateModel> states) {
        QuizType quizType = quizModel.getQuizType();
        // Initialize the quiz logic based on the quiz type
        if (quizType == QuizType.CAPITAL_QUIZ) {
            return new CapitalsQuizLogic(quizModel, states, quizzesAccess);
        }
        // Implement similar logic for other quiz types
        return null;
    }
}

