package edu.uga.cs.csci4830_project4.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModelFactory;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;
import edu.uga.cs.csci4830_project4.common.ConstVals;
import edu.uga.cs.csci4830_project4.common.QuizType;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

/**
 * This activity is responsible for starting a new quiz. It allows the user to select the type of
 * quiz they want to take. The user can select the "Capitals Quiz" button to start a capitals quiz.
 */
public class StartNewQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_quiz);

        // up button enabled on start new quiz activity
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }

        Button capitalsQuizButton = findViewById(R.id.btnCapitalsQuiz);
        capitalsQuizButton.setOnClickListener(v -> startQuiz(QuizType.CAPITALS_QUIZ));
    }

    private void startQuiz(QuizType quizType) {
        // create and open accesses
        QuizzesAccess quizzesAccess = new QuizzesAccess(this);
        StatesAccess statesAccess = new StatesAccess(this);
        quizzesAccess.open();
        statesAccess.open();

        // Create and store a new quiz based on the selected QuizType
        QuizModelFactory quizModelFactory = new QuizModelFactory(quizzesAccess, statesAccess);
        int numberOfQuestions = ConstVals.NUMBER_QUESTIONS;
        QuizModelFactory.QuizModelFactoryParams params =
                new QuizModelFactory.QuizModelFactoryParams(quizType, numberOfQuestions);
        QuizModel quizModel = quizModelFactory.createAndStore(params);

        // Convert the quiz model to a QuizDTO
        QuizDTO quizDTO = QuizDTO.fromModel(quizModel);

        // close the accesses
        quizzesAccess.close();
        statesAccess.close();

        // Start the QuizActivity with the selected quiz
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("quizDTO", quizDTO);
        startActivity(intent);

        // finish this activity
        finish();
    }
}

