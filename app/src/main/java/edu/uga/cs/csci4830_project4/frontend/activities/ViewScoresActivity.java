package edu.uga.cs.csci4830_project4.frontend.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.scores.ScoreModel;
import edu.uga.cs.csci4830_project4.backend.scores.ScoresAccess;

public class ViewScoresActivity extends AppCompatActivity {

    private static final String TAG = "ViewScoresActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Log.d(TAG, "onCreate(): Creating ViewScoresActivity");

        // TODO: Use async task to fetch the completed scores
        IAccess<ScoreModel> scoresAccess = new ScoresAccess(this);
        scoresAccess.open();
        List<ScoreModel> allScoreModels = scoresAccess.retrieveAll();
        scoresAccess.close();

        Log.d(TAG, "onCreate(): Retrieved all score models = " + allScoreModels);

        // Get the TableLayout to display the quiz scores
        TableLayout tableLayout = findViewById(R.id.tableLayoutScores);
        allScoreModels.forEach(scoreModel -> {
            TableRow row = new TableRow(this);

            TextView scoreView = new TextView(this);
            String score = scoreModel.getScore();

            Log.d(TAG, "onCreate(): Adding row to table with score = " + score);

            scoreView.setText(score);
            row.addView(scoreView);

            tableLayout.addView(row);
        });
    }
}

