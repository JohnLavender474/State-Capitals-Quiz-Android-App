package edu.uga.cs.csci4830_project4.frontend.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizTableValues;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;

public class ViewScoresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scores);

        // Access the completed quizzes and their scores from the database
        QuizzesAccess quizzesAccess = new QuizzesAccess(this);
        quizzesAccess.open();
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
    }
}

