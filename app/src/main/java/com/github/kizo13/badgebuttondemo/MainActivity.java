package com.github.kizo13.badgebuttondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.kizo13.badgebutton.BadgeButton;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "BadgeButtonDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BadgeButton badgeButton = (BadgeButton) findViewById(R.id.badgebutton);
        if (badgeButton == null) return;
        badgeButton.setValue(15);

    }
}
