package edu.uga.cs.csci4830_project4.frontend.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;

public class ViewScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Access the completed quizzes and their scores from the database
        // TODO: Use async task to fetch the completed quizzes
        QuizzesAccess quizzesAccess = new QuizzesAccess(this);
        quizzesAccess.open();

        /*
        List<QuizModel> completedQuizzes = quizzesAccess.retrieve(
                null, QuizTableValues.COLUMN_FINISHED + " = 1", null,
                null, null, null, null
        );
        quizzesAccess.close();

        // Get the TableLayout to display the quiz scores
        TableLayout tableLayout = findViewById(R.id.tableLayoutScores);

        // Iterate through completed quizzes and add them to the table
        for (QuizModel quiz : completedQuizzes) {
            TableRow row = new TableRow(this);

            TextView quizIdTextView = new TextView(this);
            quizIdTextView.setText(String.valueOf(quiz.getId()));
            row.addView(quizIdTextView);

            TextView scoreTextView = new TextView(this);
            scoreTextView.setText(quiz.getScore());
            row.addView(scoreTextView);

            tableLayout.addView(row);
        }
         */
    }
}

