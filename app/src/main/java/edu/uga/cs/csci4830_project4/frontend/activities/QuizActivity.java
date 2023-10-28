package edu.uga.cs.csci4830_project4.frontend.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizType;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuizLogic;
import edu.uga.cs.csci4830_project4.frontend.quizzes.impl.CapitalsQuizLogic;
import edu.uga.cs.csci4830_project4.frontend.utils.OnGestureAdapter;
import edu.uga.cs.csci4830_project4.utils.ConstVals;

/**
 * This activity is used to play a quiz. In the {@link #onCreate(Bundle)} method, the quiz model
 * is retrieved from the intent and the quiz logic is initialized. The quiz logic is then run
 * while the quiz model is used to display the quiz information to the user. When the quiz is
 * finished, the quiz model is updated to be finished and saved to the database. If this
 * activity is exited while the quiz logic is underway, then the user can continue the quiz
 * at a later time since it is automatically saved to the database every time a change is made
 * to the quiz model. The quiz model should saved and updated to the database from within the
 * {@link IQuizLogic}. See the implementations of quiz logic in the frontend quizzes package.
 */
public final class QuizActivity extends AppCompatActivity implements OnGestureAdapter {

    private static final String TAG = "QuizActivity";
    private static final int swipeThreshold = 100;
    private static final int swipeVelocityThreshold = 100;

    private Map<String, String> stateNameToImageSource;

    private GestureDetector gestureDetector;
    private QuizzesAccess quizzesAccess;
    private StatesAccess statesAccess;

    private IQuizLogic quizLogic;

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

        gestureDetector = new GestureDetector(this);

        // initialize the database access objects
        quizzesAccess = new QuizzesAccess(this);
        statesAccess = new StatesAccess(this);

        QuizModel quiz = (QuizModel) getIntent().getSerializableExtra("quiz");
        if (quiz == null) {
            // TODO: should do all of this in async task
            List<Long> stateIds = statesAccess.getRandomStateIds(ConstVals.NUMBER_QUESTIONS);
            QuizModel temp = new QuizModel(ConstVals.NUMBER_QUESTIONS);
            temp.setStateIds(stateIds);
            quiz = quizzesAccess.store(temp);
        }

        // Determine the quiz type and fetch the states
        List<StateModel> states = fetchStatesForQuiz(quiz);

        // Initialize the quiz logic based on the type
        quizLogic = createQuizLogic(quiz, states);
        if (quizLogic == null) {
            throw new IllegalStateException("Logic not found for quiz type: " + quiz.getQuizType());
        }

        // TODO:
        // - Display the quiz information to the user
        // - Run the quiz logic
        // - When user swipes right and there is a next question, go to next question
        // - When user swipes left and there is a previous question, go to previous question
        // - When user swipes right on the final question, then present "Submit" button that
        //   finalizes the quiz, saves it to the database as a completed quiz, and transitions
        //   to the view score activity which shows the user's score and presents a button for
        //   the user to return to the main menu
    }

    private void displayQuizInfo() {
        questionTextView.setText(quizLogic.getCurrentQuestion());
        String stateName = quizLogic.getCurrentStateName();
        String stateImageSource = stateNameToImageSource.get(stateName);
        // TODO: set image here
        // stateImageView.setImageResource(stateImageSource);

        List<String> choices = quizLogic.getCurrentChoices();
        String currentResponse = quizLogic.getCurrentResponse();

        String choice1 = choices.get(0);
        choice1RadioButton.setChecked(choice1.equals(currentResponse));
        choice1RadioButton.setText(choice1);

        String choice2 = choices.get(1);
        choice2RadioButton.setChecked(choice2.equals(currentResponse));
        choice2RadioButton.setText(choice2);

        String choice3 = choices.get(2);
        choice3RadioButton.setChecked(choice3.equals(currentResponse));
        choice3RadioButton.setText(choice3);
    }

    /**
     * {@inheritDoc}
     * <p>
     * In {@link QuizActivity}, this method is used to detect if the user swipes left.
     *
     * @param event The touch screen event being processed.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    /**
     * {@inheritDoc}
     * <p>
     * In {@link QuizActivity}, this method is used to detect if the user swipes left.
     *
     * @param e1        The first down motion event that started the fling. A {@code null} event
     *                  indicates an incomplete event stream or error state.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return True if the event is consumed, false otherwise.
     */
    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX,
                           float velocityY) {
        if (e1 == null) {
            return true;
        }
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > swipeThreshold && Math.abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        Log.d(TAG, "onFling: Right to Left swipe gesture");
                    } else {
                        Log.d(TAG, "onFling: Left to Right swipe gesture");
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;

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

        return states;
    }

    private IQuizLogic createQuizLogic(QuizModel quizModel, List<StateModel> states) {
        QuizType quizType = quizModel.getQuizType();
        // Initialize the quiz logic based on the quiz type
        if (quizType == QuizType.CAPITAL_QUIZ) {
            return new CapitalsQuizLogic(quizModel, states, quizzesAccess);
        }
        // Implement similar logic for other quiz types
        return null;
    }
}

