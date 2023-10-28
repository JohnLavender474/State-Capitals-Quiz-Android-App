package edu.uga.cs.csci4830_project4.frontend.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.uga.cs.csci4830_project4.R;

public class old_VictoryActivity extends Activity implements View.OnClickListener {

    private Button mRestartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        mRestartButton = findViewById(R.id.bPlayAgain);
        mRestartButton.setOnClickListener(this);
    }

    /**
     * Handles button click events.
     */
    @Override
    public void onClick(View v) {
        if (v == mRestartButton) {
            restartGame();
        }
    }

    /**
     * Restarts the game by launching the GameActivity.
     */
    private void restartGame() {
        /*
        Intent intent = new Intent(this, old_GameActivity.class);
        startActivity(intent);
        finish();
         */
    }
}
