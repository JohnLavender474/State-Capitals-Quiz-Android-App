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

        Button buttonStartNewGame = findViewById(R.id.buttonStartNewGame);
        Button buttonContinueGame = findViewById(R.id.buttonContinueGame);
        Button buttonViewScores = findViewById(R.id.buttonViewPastResults);

        buttonStartNewGame.setOnClickListener(view -> startActivity(new Intent(this,
                StartNewQuizActivity.class)));
        buttonContinueGame.setOnClickListener(view -> startActivity(new Intent(this,
                SelectQuizActivity.class)));
        buttonViewScores.setOnClickListener(view -> startActivity(new Intent(this,
                ViewScoresActivity.class)));
    }

}

