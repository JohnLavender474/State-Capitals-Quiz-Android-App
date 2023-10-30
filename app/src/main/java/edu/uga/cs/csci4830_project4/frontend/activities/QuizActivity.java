package edu.uga.cs.csci4830_project4.frontend.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.states.StateModel;
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

    private static final String TAG = "QuizActivity";
    private static final int swipeThreshold = 100;
    private static final int swipeVelocityThreshold = 100;

    private Map<String, String> stateNameToImageSource;

    private QuizzesAccess quizzesAccess;
    private StatesAccess statesAccess;

    private IQuiz quiz;

    private TextView questionTextView;
    private ImageView stateImageView;
    private RadioButton choice1RadioButton;
    private RadioButton choice2RadioButton;
    private RadioButton choice3RadioButton;
    private RadioButton choice4RadioButton;

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
        setContentView(R.layout.fragment_activity_quiz);

        stateNameToImageSource = new HashMap<>();
        // TODO: fill name-image map here

        questionTextView = findViewById(R.id.tvQuestion);
        stateImageView = findViewById(R.id.ivStateImage);
        choice1RadioButton = findViewById(R.id.rbChoice1);
        choice2RadioButton = findViewById(R.id.rbChoice2);
        choice3RadioButton = findViewById(R.id.rbChoice3);

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

        // Initialize the quizModel logic based on the type
        /*
        quizLogic = createQuizLogic(quizModel, states);
        if (quizLogic == null) {
            throw new IllegalStateException("Logic not found for quizModel type: " + quizModel
            .getQuizType());
        }
        */

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

    private void displayQuizInfo() {
        /*
        // questionTextView.setText(quizLogic.getCurrentQuestion());
        // String stateName = quizLogic.getCurrentStateName();
        String stateImageSource = stateNameToImageSource.get(stateName);
        // TODO: set image here
        // stateImageView.setImageResource(stateImageSource);

        // List<String> choices = quizLogic.getCurrentChoices();
        // String currentResponse = quizLogic.getCurrentResponse();

        String choice1 = choices.get(0);
        choice1RadioButton.setChecked(choice1.equals(currentResponse));
        choice1RadioButton.setText(choice1);

        String choice2 = choices.get(1);
        choice2RadioButton.setChecked(choice2.equals(currentResponse));
        choice2RadioButton.setText(choice2);

        String choice3 = choices.get(2);
        choice3RadioButton.setChecked(choice3.equals(currentResponse));
        choice3RadioButton.setText(choice3);
         */
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

    // TODO: use async task for this
    private List<StateModel> fetchStatesForQuiz(QuizModel quiz) {
        List<StateModel> states = new ArrayList<>();

        // TODO: fix
        /*
        // Fetch the states for the quiz
        List<Long> stateIds = quiz.getStateIds();
        if (stateIds == null) {
            throw new IllegalStateException("Quiz state ids are null");
        }
        stateIds.forEach(id -> {
            StateModel state = statesAccess.getById(id);
            if (state != null) {
                states.add(state);
            }
        });
         */

        return states;
    }

    /*
    private IQuizLogic createQuizLogic(QuizModel quizModel, List<StateModel> states) {
        QuizType quizType = quizModel.getQuizType();
        // Initialize the quiz logic based on the quiz type
        if (quizType == QuizType.CAPITAL_QUIZ) {
            // TODO: return new CapitalsQuizLogic(quizModel, states, quizzesAccess);
        }
        // Implement similar logic for other quiz types
        return null;
    }
     */
}

