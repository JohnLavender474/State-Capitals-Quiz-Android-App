package edu.uga.cs.csci4830_project4.frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

public class SelectQuizActivity extends AppCompatActivity {

    private static final String TAG = "SelectQuizActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz);

        // Initialize the ListView
        ListView lvQuizList = findViewById(R.id.lvQuizList);

        // Fetch the list of quiz models from the database
        List<QuizModel> quizModels = getQuizModelsFromDatabase();

        // Create an array of quiz types
        List<String> quizTypes = new ArrayList<>();
        for (QuizModel quizModel : quizModels) {
            String quizType = quizModel.getQuizType().name().replace("_", " ");
            quizTypes.add(quizType);
        }

        // Create an ArrayAdapter to populate the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, quizTypes);

        // Set the adapter for the ListView
        lvQuizList.setAdapter(adapter);

        // Set an item click listener for the ListView
        lvQuizList.setOnItemClickListener((parent, view, position, id) -> {
            // Handle the item click
            QuizModel selectedQuizModel = quizModels.get(position);
            // Convert the selected QuizModel to QuizDTO
            QuizDTO quizDTO = QuizDTO.fromModel(selectedQuizModel);

            Log.d(TAG, "onCreate(): Selected quiz = " + quizDTO);

            // Start the QuizActivity with the selected quiz
            Intent intent = new Intent(SelectQuizActivity.this, QuizActivity.class);
            intent.putExtra("quizDTO", quizDTO);
            startActivity(intent);

            finish();
        });
    }

    private List<QuizModel> getQuizModelsFromDatabase() {
        QuizzesAccess quizzesAccess = new QuizzesAccess(this);
        quizzesAccess.open();
        List<QuizModel> quizModels = quizzesAccess.retrieveAll();
        quizzesAccess.close();
        return quizModels;
    }
}

