package edu.uga.cs.csci4830_project4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.database.access.StateCapitalsAccess;

public class MainActivity extends Activity {

    // TODO: example usage of StateCapitalsAccess, not actually needed to main activity
    private StateCapitalsAccess stateCapitalsAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stateCapitalsAccess = new StateCapitalsAccess(this);

        Button buttonPlay = findViewById(R.id.buttonPlay);

        // TODO: replace this with a call to the GameActivity
        buttonPlay.setOnClickListener(view -> startActivity(new Intent(this,
                old_GameActivity.class)));
        // buttonPlay.setOnClickListener(view -> startActivity(new Intent(this, GameActivity
        // .class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stateCapitalsAccess.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stateCapitalsAccess.close();
    }
}
