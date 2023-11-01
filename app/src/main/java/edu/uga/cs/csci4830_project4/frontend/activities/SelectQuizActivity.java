package edu.uga.cs.csci4830_project4.frontend.activities;

import static edu.uga.cs.csci4830_project4.common.CommonUtilMethods.dateToString;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.frontend.async.RetrieveAllModelsTask;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

/**
 * This activity is responsible for displaying the list of quizzes that the user can take. The user
 * can select a quiz from the list and continue the quiz.
 */
public class SelectQuizActivity extends AppCompatActivity {

    private static final String TAG = "SelectQuizActivity";

    private List<QuizModel> quizModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initialize the ListView
        ListView lvQuizList = findViewById(R.id.lvQuizList);

        // Create an ArrayAdapter for the quiz types
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        // Set the adapter for the ListView
        lvQuizList.setAdapter(adapter);

        // Set an item click listener for the ListView
        lvQuizList.setOnItemClickListener((parent, view, position, id) -> {
            // Handle the item click
            // Get the selected QuizModel from the adapter
            QuizModel selectedQuizModel = getSelectedQuizModel(position);

            if (selectedQuizModel != null) {
                // Convert the selected QuizModel to QuizDTO
                QuizDTO quizDTO = QuizDTO.fromModel(selectedQuizModel);

                Log.d(TAG, "onCreate(): Selected quiz = " + quizDTO);

                // Start the QuizActivity with the selected quiz
                Intent intent = new Intent(SelectQuizActivity.this, QuizActivity.class);
                intent.putExtra("quizDTO", quizDTO);
                startActivity(intent);

                finish();
            }
        });

        // Fetch quiz models from the database asynchronously
        fetchQuizModelsAsync(adapter);
    }

    private void fetchQuizModelsAsync(ArrayAdapter<String> adapter) {
        RetrieveAllModelsTask<QuizModel> fetchQuizModelsTask =
                new RetrieveAllModelsTask<>(new QuizzesAccess(this), quizModels -> {
            // Update the adapter with quiz types
            for (QuizModel quizModel : quizModels) {
                String quizType = quizModel.getQuizType().name().replace("_", " ");
                String timeUpdated = dateToString(quizModel.getTimeUpdated());
                String timeCreated = dateToString(quizModel.getTimeCreated());
                adapter.add(String.format(Locale.getDefault(), """
                        %s - %d.
                        Created: %s.
                        Updated: %s.""", quizType, quizModel.getId(), timeCreated, timeUpdated));

                this.quizModels = quizModels;
            }
        });
        fetchQuizModelsTask.execute();
    }

    private QuizModel getSelectedQuizModel(int position) {
        return quizModels != null ? quizModels.get(position) : null;
    }

}

