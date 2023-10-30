package edu.uga.cs.csci4830_project4.frontend.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuiz;
import edu.uga.cs.csci4830_project4.frontend.quizzes.Quiz;
import edu.uga.cs.csci4830_project4.utils.ConstVals;

/**
 * This activity is used to play a quiz. In the {@link #onCreate(Bundle)} method, the quiz model
 * is retrieved from the intent and the quiz logic is initialized. The quiz logic is then run
 * while the quiz model is used to display the quiz information to the user. When the quiz is
 * finished, the quiz model is updated to be finished and saved to the database. If this
 * activity is exited while the quiz logic is underway, then the user can continue the quiz
 * at a later time since it is automatically saved to the database every time a change is made
 * to the quiz model. The quiz model should saved and updated to the database from within the
 * {@link IQuiz}. See the implementations of quiz logic in the frontend quizzes package.
 */
public final class QuizActivity extends AppCompatActivity {

    private QuizzesAccess quizzesAccess;
    private StatesAccess statesAccess;
    private IQuiz quiz;

    /**
     * {@inheritDoc}
     * <p>
     * Gets the quiz model from the intent. The quiz model is passed in the intent from the
     * {@link SelectQuizActivity} when the user selects a quiz to play. If there is no quiz
     * model in the intent, then a new one will be created and stored in the database. The
     * new quiz will use {@link ConstVals#NUMBER_QUESTIONS} in its constructor, though if a
     * custom number of questions is desired, then the developer should create and store a
     * new quiz model and pass it into this activity's intent.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // initialize the database access objects
        quizzesAccess = new QuizzesAccess(this);
        statesAccess = new StatesAccess(this);

        QuizModel quizModel = (QuizModel) getIntent().getSerializableExtra("quizModel");
        if (quizModel == null) {
            throw new IllegalStateException("Quiz model not found in intent");
        }
        QuizDTO quizDTO = QuizDTO.fromModel(quizModel);
        quiz = new Quiz(quizDTO);

        //

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        // TODO: QuizPagerAdapter adapter = new QuizPagerAdapter(quiz, );

        // TODO:
        // - Display the quizModel information to the user
        // - Run the quizModel logic
        // - When user swipes right and there is a next question, go to next question
        // - When user swipes left and there is a previous question, go to previous question
        // - When user swipes right on the final question, then present "Submit" button that
        //   finalizes the quizModel, saves it to the database as a completed quizModel, and
        //   transitions
        //   to the view score activity which shows the user's score and presents a button for
        //   the user to return to the main menu
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (quizzesAccess != null) {
            quizzesAccess.open();
        }
        if (statesAccess != null) {
            statesAccess.open();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (quizzesAccess != null) {
            quizzesAccess.close();
        }
        if (statesAccess != null) {
            statesAccess.close();
        }
    }
}

