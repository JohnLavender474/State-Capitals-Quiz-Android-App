package edu.uga.cs.csci4830_project4.frontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.uga.cs.csci4830_project4.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(view -> startActivity(new Intent(this,
                GameActivity.class)));
    }
}
