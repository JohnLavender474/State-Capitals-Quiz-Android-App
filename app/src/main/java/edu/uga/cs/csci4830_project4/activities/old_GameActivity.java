package edu.uga.cs.csci4830_project4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import edu.uga.cs.csci4830_project4.R;

// TODO: This isn't meant to be a complete solution, just a starting point
// TODO: initialize, fetch, and update data using database instead of in code
public class old_GameActivity extends Activity implements View.OnClickListener {

    private static final HashMap<String, Integer> stateImageMap = new HashMap<>();

    static {
        stateImageMap.put(States.ALABAMA, R.drawable.state_alabama);
        stateImageMap.put(States.ALASKA, R.drawable.state_alaska);
        stateImageMap.put(States.ARIZONA, R.drawable.state_arizona);
        stateImageMap.put(States.ARKANSAS, R.drawable.state_arkansas);
        stateImageMap.put(States.CALIFORNIA, R.drawable.state_california);
        stateImageMap.put(States.COLORADO, R.drawable.state_colorado);
        stateImageMap.put(States.CONNECTICUT, R.drawable.state_connecticut);
        stateImageMap.put(States.DELAWARE, R.drawable.state_delaware);
        stateImageMap.put(States.FLORIDA, R.drawable.state_florida);
        stateImageMap.put(States.GEORGIA, R.drawable.state_georgia);
        stateImageMap.put(States.HAWAII, R.drawable.state_hawaii);
        stateImageMap.put(States.IDAHO, R.drawable.state_idaho);
        stateImageMap.put(States.ILLINOIS, R.drawable.state_illinois);
        stateImageMap.put(States.INDIANA, R.drawable.state_indiana);
        stateImageMap.put(States.IOWA, R.drawable.state_iowa);
        stateImageMap.put(States.KANSAS, R.drawable.state_kansas);
        stateImageMap.put(States.KENTUCKY, R.drawable.state_kentucky);
        stateImageMap.put(States.LOUISIANA, R.drawable.state_louisiana);
        stateImageMap.put(States.MAINE, R.drawable.state_maine);
        stateImageMap.put(States.MARYLAND, R.drawable.state_maryland);
        stateImageMap.put(States.MASSACHUSETTS, R.drawable.state_massachussets);
        stateImageMap.put(States.MICHIGAN, R.drawable.state_michigan);
        stateImageMap.put(States.MINNESOTA, R.drawable.state_minnesota);
        stateImageMap.put(States.MISSISSIPPI, R.drawable.state_mississippi);
        stateImageMap.put(States.MISSOURI, R.drawable.state_missouri);
        stateImageMap.put(States.MONTANA, R.drawable.state_montana);
        stateImageMap.put(States.NEBRASKA, R.drawable.state_nebraska);
        stateImageMap.put(States.NEVADA, R.drawable.state_nevada);
        stateImageMap.put(States.NEW_HAMPSHIRE, R.drawable.state_new_hampshire);
        stateImageMap.put(States.NEW_JERSEY, R.drawable.state_new_jersey);
        stateImageMap.put(States.NEW_MEXICO, R.drawable.state_new_mexico);
        stateImageMap.put(States.NEW_YORK, R.drawable.state_new_york);
        stateImageMap.put(States.NORTH_CAROLINA, R.drawable.state_north_carolina);
        stateImageMap.put(States.NORTH_DAKOTA, R.drawable.state_north_dakota);
        stateImageMap.put(States.OHIO, R.drawable.state_ohio);
        stateImageMap.put(States.OKLAHOMA, R.drawable.state_oklahoma);
        stateImageMap.put(States.OREGON, R.drawable.state_oregon);
        stateImageMap.put(States.PENNSYLVANIA, R.drawable.state_pennsylvania);
        stateImageMap.put(States.RHODE_ISLAND, R.drawable.state_rhode_island);
        stateImageMap.put(States.SOUTH_CAROLINA, R.drawable.state_south_carolina);
        stateImageMap.put(States.SOUTH_DAKOTA, R.drawable.state_south_dakota);
        stateImageMap.put(States.TENNESSEE, R.drawable.state_tennessee);
        stateImageMap.put(States.TEXAS, R.drawable.state_texas);
        stateImageMap.put(States.UTAH, R.drawable.state_utah);
        stateImageMap.put(States.VERMONT, R.drawable.state_vermont);
        stateImageMap.put(States.VIRGINIA, R.drawable.state_virginia);
        stateImageMap.put(States.WASHINGTON, R.drawable.state_washington);
        stateImageMap.put(States.WEST_VIRGINIA, R.drawable.state_west_virginia);
        stateImageMap.put(States.WISCONSIN, R.drawable.state_wisconsin);
        stateImageMap.put(States.WYOMING, R.drawable.state_wyoming);
    }

    public static final int NUMBER_OF_STATES = 50;

    private final Button[] mAnswerButtons = new Button[4];
    private TextView mStateName, mScore;
    private ImageView mStateImage;

    private final HashMap<String, String> mStateCapitalMap = new HashMap<>();
    private String mCorrectCapital = null;
    private boolean mWaitingForAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setUpComponents();
        restartGame();
    }

    @Override
    public void onClick(View v) {
        if (mWaitingForAnswer) {
            mWaitingForAnswer = false;
            Button button = (Button) v;

            if (button.getText().equals(mCorrectCapital)) respondToCorrectAnswer();
            else respondToWrongAnswer();
        }
    }

    private void setUpComponents() {
        mAnswerButtons[0] = findViewById(R.id.bChoice1);
        mAnswerButtons[1] = findViewById(R.id.bChoice2);
        mAnswerButtons[2] = findViewById(R.id.bChoice3);
        mAnswerButtons[3] = findViewById(R.id.bChoice4);

        for (Button button : mAnswerButtons) {
            button.setOnClickListener(this);
        }

        mStateName = findViewById(R.id.tvStateName);
        mStateImage = findViewById(R.id.ivStateImage);
        mScore = findViewById(R.id.tvScoreData);
    }

    public void respondToCorrectAnswer() {
        int newScore = Integer.parseInt(mScore.getText().toString()) + 1;
        setScore(newScore);

        if (newScore < NUMBER_OF_STATES) presentNextState();
        else respondToVictory();
    }

    public void respondToVictory() {
        Intent intent = new Intent(this, VictoryActivity.class);
        startActivity(intent);
        finish();
    }


    public void respondToWrongAnswer() {
        Toast.makeText(getApplicationContext(), "Wrong! The game has restarted.",
                Toast.LENGTH_SHORT).show();
        restartGame();
    }

    private void restartGame() {
        mStateCapitalMap.clear();
        mStateCapitalMap.putAll(getNewStateCapitalMap());
        mCorrectCapital = null;
        mWaitingForAnswer = false;
        setScore(0);

        presentNextState();
    }

    public void setScore(int value) {
        if (value < 0 || value > NUMBER_OF_STATES) {
            throw new IllegalArgumentException("Value isn't in the correct range");
        }

        mScore.setText(String.valueOf(value));
    }

    public static HashMap<String, String> getNewStateCapitalMap() {
        HashMap<String, String> stateCapitalMap = new HashMap<>();

        stateCapitalMap.put(States.ALABAMA, "Montgomery");
        stateCapitalMap.put(States.ALASKA, "Juneau");
        stateCapitalMap.put(States.ARIZONA, "Phoenix");
        stateCapitalMap.put(States.ARKANSAS, "Little Rock");
        stateCapitalMap.put(States.CALIFORNIA, "Sacramento");
        stateCapitalMap.put(States.COLORADO, "Denver");
        stateCapitalMap.put(States.CONNECTICUT, "Hartford");
        stateCapitalMap.put(States.DELAWARE, "Dover");
        stateCapitalMap.put(States.FLORIDA, "Tallahassee");
        stateCapitalMap.put(States.GEORGIA, "Atlanta");
        stateCapitalMap.put(States.HAWAII, "Honolulu");
        stateCapitalMap.put(States.IDAHO, "Boise");
        stateCapitalMap.put(States.ILLINOIS, "Springfield");
        stateCapitalMap.put(States.INDIANA, "Indianapolis");
        stateCapitalMap.put(States.IOWA, "Des Moines");
        stateCapitalMap.put(States.KANSAS, "Topeka");
        stateCapitalMap.put(States.KENTUCKY, "Frankfort");
        stateCapitalMap.put(States.LOUISIANA, "Baton Rouge");
        stateCapitalMap.put(States.MAINE, "Augusta");
        stateCapitalMap.put(States.MARYLAND, "Annapolis");
        stateCapitalMap.put(States.MASSACHUSETTS, "Boston");
        stateCapitalMap.put(States.MICHIGAN, "Lansing");
        stateCapitalMap.put(States.MINNESOTA, "St. Paul");
        stateCapitalMap.put(States.MISSISSIPPI, "Jackson");
        stateCapitalMap.put(States.MISSOURI, "Jefferson City");
        stateCapitalMap.put(States.MONTANA, "Helena");
        stateCapitalMap.put(States.NEBRASKA, "Lincoln");
        stateCapitalMap.put(States.NEVADA, "Carson City");
        stateCapitalMap.put(States.NEW_HAMPSHIRE, "Concord");
        stateCapitalMap.put(States.NEW_JERSEY, "Trenton");
        stateCapitalMap.put(States.NEW_MEXICO, "Santa Fe");
        stateCapitalMap.put(States.NEW_YORK, "Albany");
        stateCapitalMap.put(States.NORTH_CAROLINA, "Raleigh");
        stateCapitalMap.put(States.NORTH_DAKOTA, "Bismarck");
        stateCapitalMap.put(States.OHIO, "Columbus");
        stateCapitalMap.put(States.OKLAHOMA, "Oklahoma City");
        stateCapitalMap.put(States.OREGON, "Salem");
        stateCapitalMap.put(States.PENNSYLVANIA, "Harrisburg");
        stateCapitalMap.put(States.RHODE_ISLAND, "Providence");
        stateCapitalMap.put(States.SOUTH_CAROLINA, "Columbia");
        stateCapitalMap.put(States.SOUTH_DAKOTA, "Pierre");
        stateCapitalMap.put(States.TENNESSEE, "Nashville");
        stateCapitalMap.put(States.TEXAS, "Austin");
        stateCapitalMap.put(States.UTAH, "Salt Lake City");
        stateCapitalMap.put(States.VERMONT, "Montpelier");
        stateCapitalMap.put(States.VIRGINIA, "Richmond");
        stateCapitalMap.put(States.WASHINGTON, "Olympia");
        stateCapitalMap.put(States.WEST_VIRGINIA, "Charleston");
        stateCapitalMap.put(States.WISCONSIN, "Madison");
        stateCapitalMap.put(States.WYOMING, "Cheyenne");

        return stateCapitalMap;
    }


    public static Integer getStateImageResId(String stateName) {
        if (stateImageMap.containsKey(stateName)) {
            return stateImageMap.get(stateName);
        } else {
            throw new IllegalArgumentException("Invalid state given");
        }
    }


    private void presentNextState() {
        String stateName = updateStateCapitalPair();
        updateShownState(stateName);
        updateFourAnswers();
        mWaitingForAnswer = true;
    }

    public String updateStateCapitalPair() {
        String state = getRandomKey(mStateCapitalMap);
        mCorrectCapital = mStateCapitalMap.remove(state);
        return state;
    }

    public static String getRandomKey(HashMap<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        Random random = new Random();
        return keys.get(random.nextInt(keys.size()));
    }

    public void updateShownState(String stateName) {
        mStateName.setText(stateName);
        mStateImage.setImageResource(getStateImageResId(stateName));
    }

    public void updateFourAnswers() {
        List<String> possibleAnswers = new ArrayList<>(mStateCapitalMap.values());
        Random random = new Random();
        int correctAnswerIndex = random.nextInt(mAnswerButtons.length);
        mAnswerButtons[correctAnswerIndex].setText(mCorrectCapital);

        for (int i = 0; i < mAnswerButtons.length; i++) {
            if (i != correctAnswerIndex) {
                int randomIndex = random.nextInt(possibleAnswers.size());
                mAnswerButtons[i].setText(possibleAnswers.get(randomIndex));
                possibleAnswers.remove(randomIndex);
            }
        }
    }

    /**
     * Created by Aaron on 12/7/2015.
     */
    public static class States {
        public static final String ALABAMA = "Alabama";
        public static final String ALASKA = "Alaska";
        public static final String ARIZONA = "Arizona";
        public static final String ARKANSAS = "Arkansas";
        public static final String CALIFORNIA = "California";
        public static final String COLORADO = "Colorado";
        public static final String CONNECTICUT = "Connecticut";
        public static final String DELAWARE = "Delaware";
        public static final String FLORIDA = "Florida";
        public static final String GEORGIA = "Georgia";
        public static final String HAWAII = "Hawaii";
        public static final String IDAHO = "Idaho";
        public static final String ILLINOIS = "Illinois";
        public static final String INDIANA = "Indiana";
        public static final String IOWA = "Iowa";
        public static final String KANSAS = "Kansas";
        public static final String KENTUCKY = "Kentucky";
        public static final String LOUISIANA = "Louisiana";
        public static final String MAINE = "Maine";
        public static final String MARYLAND = "Maryland";
        public static final String MASSACHUSETTS = "Massachusetts";
        public static final String MICHIGAN = "Michigan";
        public static final String MINNESOTA = "Minnesota";
        public static final String MISSISSIPPI = "Mississippi";
        public static final String MISSOURI = "Missouri";
        public static final String MONTANA = "Montana";
        public static final String NEBRASKA = "Nebraska";
        public static final String NEVADA = "Nevada";
        public static final String NEW_HAMPSHIRE = "New Hampshire";
        public static final String NEW_JERSEY = "New Jersey";
        public static final String NEW_MEXICO = "New Mexico";
        public static final String NEW_YORK = "New York";
        public static final String NORTH_CAROLINA = "North Carolina";
        public static final String NORTH_DAKOTA = "North Dakota";
        public static final String OHIO = "Ohio";
        public static final String OKLAHOMA = "Oklahoma";
        public static final String OREGON = "Oregon";
        public static final String PENNSYLVANIA = "Pennsylvania";
        public static final String RHODE_ISLAND = "Rhode Island";
        public static final String SOUTH_CAROLINA = "South Carolina";
        public static final String SOUTH_DAKOTA = "South Dakota";
        public static final String TENNESSEE = "Tennessee";
        public static final String TEXAS = "Texas";
        public static final String UTAH = "Utah";
        public static final String VERMONT = "Vermont";
        public static final String VIRGINIA = "Virginia";
        public static final String WASHINGTON = "Washington";
        public static final String WEST_VIRGINIA = "West Virginia";
        public static final String WISCONSIN = "Wisconsin";
        public static final String WYOMING = "Wyoming";
    }
}
