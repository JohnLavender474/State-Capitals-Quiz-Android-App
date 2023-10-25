package edu.uga.cs.csci4830_project4.frontend;

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

        Button buttonStartGame = findViewById(R.id.buttonStartNewGame);
        Button buttonViewScores = findViewById(R.id.buttonViewPastResults);

        buttonStartGame.setOnClickListener(view -> {
            // Start a new quiz (transition to the new quiz screen)
            startActivity(new Intent(this, GameActivity.class));
        });

        buttonViewScores.setOnClickListener(view -> {
            // View past results (transition to the view results screen)
            startActivity(new Intent(this, ViewScoresActivity.class));
        });
    }
}

