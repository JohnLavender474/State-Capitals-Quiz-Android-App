package edu.uga.cs.csci4830_project4.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.uga.cs.csci4830_project4.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonNewStartGame = findViewById(R.id.buttonStartNewGame);
        Button buttonContinueGame = findViewById(R.id.buttonContinueGame);
        Button buttonViewScores = findViewById(R.id.buttonViewPastResults);

        buttonNewStartGame.setOnClickListener(view -> {
            /*
            TODO:
            Start a new quiz (transition to the new quiz screen),
            generate a new game, and attach it to the intent to be passed
            to the PlayGameActivity. Generating a game should do the following:
              - Create a new Quiz model object
              - Populate the new Quiz model object with 6 questions where each question
                should have a different state and the 3 choices should be randomly shuffled.
              - Probably a good idea to have a static method called "generateNewGame" which
                does this by calling the backend.
             */
            startActivity(new Intent(this, QuizActivity.class));
        });

        buttonContinueGame.setOnClickListener(view -> {
            /*
            TODO:
            Transition to the select game in-progress activity. This activity will fetch
            all games in progress where Quiz.isFinished() returns false.
             */
            startActivity(new Intent(this, SelectQuizActivity.class));
        });

        buttonViewScores.setOnClickListener(view -> {
            /*
            TODO:
            Transition to the view scores activity. This activity should fetch all games
            where Quiz.isFinished() returns true and display the score of each game.
             */
            startActivity(new Intent(this, ViewScoresActivity.class));
        });
    }
}

