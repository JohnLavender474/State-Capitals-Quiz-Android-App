package edu.uga.cs.csci4830_project4.frontend.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.states.StatesAccess;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;
import edu.uga.cs.csci4830_project4.frontend.fragments.QuizPagerAdapter;
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

    private static Map<String, Integer> stateImagesMap;
    private QuizzesAccess quizzesAccess;
    private StatesAccess statesAccess;
    private QuizDTO quizDTO;

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
        quizDTO = QuizDTO.fromModel(quizModel);
        IQuiz quiz = new Quiz(quizDTO);

        if (stateImagesMap == null) {
            stateImagesMap = createStateImagesMap();
        }
        List<Integer> stateImages = new ArrayList<>();
        for (String stateName : quizDTO.getStateNames()) {
            stateImages.add(stateImagesMap.get(stateName));
        }

        QuizPagerAdapter adapter = new QuizPagerAdapter(quiz, stateImages,
                getSupportFragmentManager(), getLifecycle());
        ViewPager2 pager = findViewById(R.id.viewPager);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(adapter);

        Button saveAndQuitButton = findViewById(R.id.btnQuitAndSave);
        saveAndQuitButton.setOnClickListener(view -> {

        });
    }

    private Map<String, Integer> createStateImagesMap() {
        Map<String, Integer> stateImages = new HashMap<>();
        stateImages.put("Alabama", R.drawable.state_alabama);
        stateImages.put("Alaska", R.drawable.state_alaska);
        stateImages.put("Arizona", R.drawable.state_arizona);
        stateImages.put("Arkansas", R.drawable.state_arkansas);
        stateImages.put("California", R.drawable.state_california);
        stateImages.put("Colorado", R.drawable.state_colorado);
        stateImages.put("Connecticut", R.drawable.state_connecticut);
        stateImages.put("Delaware", R.drawable.state_delaware);
        stateImages.put("Florida", R.drawable.state_florida);
        stateImages.put("Georgia", R.drawable.state_georgia);
        stateImages.put("Hawaii", R.drawable.state_hawaii);
        stateImages.put("Idaho", R.drawable.state_idaho);
        stateImages.put("Illinois", R.drawable.state_illinois);
        stateImages.put("Indiana", R.drawable.state_indiana);
        stateImages.put("Iowa", R.drawable.state_iowa);
        stateImages.put("Kansas", R.drawable.state_kansas);
        stateImages.put("Kentucky", R.drawable.state_kentucky);
        stateImages.put("Louisiana", R.drawable.state_louisiana);
        stateImages.put("Maine", R.drawable.state_maine);
        stateImages.put("Maryland", R.drawable.state_maryland);
        stateImages.put("Massachusetts", R.drawable.state_massachussets);
        stateImages.put("Michigan", R.drawable.state_michigan);
        stateImages.put("Minnesota", R.drawable.state_minnesota);
        stateImages.put("Mississippi", R.drawable.state_mississippi);
        stateImages.put("Missouri", R.drawable.state_missouri);
        stateImages.put("Montana", R.drawable.state_montana);
        stateImages.put("Nebraska", R.drawable.state_nebraska);
        stateImages.put("Nevada", R.drawable.state_nevada);
        stateImages.put("New Hampshire", R.drawable.state_new_hampshire);
        stateImages.put("New Jersey", R.drawable.state_new_jersey);
        stateImages.put("New Mexico", R.drawable.state_new_mexico);
        stateImages.put("New York", R.drawable.state_new_york);
        stateImages.put("North Carolina", R.drawable.state_north_carolina);
        stateImages.put("North Dakota", R.drawable.state_north_dakota);
        stateImages.put("Ohio", R.drawable.state_ohio);
        stateImages.put("Oklahoma", R.drawable.state_oklahoma);
        stateImages.put("Oregon", R.drawable.state_oregon);
        stateImages.put("Pennsylvania", R.drawable.state_pennsylvania);
        stateImages.put("Rhode Island", R.drawable.state_rhode_island);
        stateImages.put("South Carolina", R.drawable.state_south_carolina);
        stateImages.put("South Dakota", R.drawable.state_south_dakota);
        stateImages.put("Tennessee", R.drawable.state_tennessee);
        stateImages.put("Texas", R.drawable.state_texas);
        stateImages.put("Utah", R.drawable.state_utah);
        stateImages.put("Vermont", R.drawable.state_vermont);
        stateImages.put("Virginia", R.drawable.state_virginia);
        stateImages.put("Washington", R.drawable.state_washington);
        stateImages.put("West Virginia", R.drawable.state_west_virginia);
        stateImages.put("Wisconsin", R.drawable.state_wisconsin);
        stateImages.put("Wyoming", R.drawable.state_wyoming);
        return stateImages;
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

