package edu.uga.cs.csci4830_project4.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.frontend.dto.ScoreDTO;

/**
 * This activity is responsible for displaying the user's score after they have completed a quiz.
 * The user can click the "Go Back Home" button to return to the main activity.
 */
public class ScoreActivity extends AppCompatActivity {

    private static final String TAG = "ScoreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Log.d(TAG, "Creating ScoreActivity");

        // Get the score from the intent
        ScoreDTO scoreDTO = (ScoreDTO) getIntent().getSerializableExtra("scoreDTO");
        Log.d(TAG, "Score DTO = " + scoreDTO);
        if (scoreDTO == null) {
            throw new IllegalStateException("ScoreDTO is null");
        }

        // Display the user's score
        TextView tvScore = findViewById(R.id.tvScore);
        String scoreText = "Your Score: " + scoreDTO.getScore();
        tvScore.setText(scoreText);

        Button btnGoBackHome = findViewById(R.id.btnGoBackHome);
        btnGoBackHome.setOnClickListener(v -> {
            Log.d(TAG, "Go back home button clicked. Navigating back to MainActivity and closing "
                    + "the ScoreActivity");

            // Navigate back to the main activity
            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            startActivity(intent);

            // Close the ScoreActivity
            finish();
        });
    }
}

