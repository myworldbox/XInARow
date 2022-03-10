package com.myworldbox.xinarow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//this class is for menu
public class MainActivity extends Function {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        //this work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            //code below is to handle presses of Volume up or Volume down
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                @Override
                public void onSystemUiVisibilityChange(int visibility) {

                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {

                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }

        SetMusic();

        for (int i = 0; i < buttonNumber; i++) {

            String buttonID = "button" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            button[i] = findViewById(resourceID);
            button[i].setOnClickListener(this);
        }

        Button button = findViewById(R.id.button_intro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message = "XInARow allows you to customise the value of X and grids and create your own game. You are now playing " + system[3] + " in a row; it is a game which player will succeed, if he/she places " + system[4] + " of the chess in a horizontal, vertical, or diagonal row.";

                Toast();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        String string;

        for (int i = 0; i < systemNumber; i++) {

            switch (i) {

                case 0:

                    string = "https://myworldbox.github.io";

                    break;

                case 1:

                    string = "Mode: dark";

                    break;

                case 2:

                    string = "Music: on";

                    break;

                case 3:

                    string = "5";

                    break;

                case 4:

                    string = "15";

                    break;

                case 5:

                    string = "Gravity: off";

                    break;

                case 6:
                case 7:

                    string = "0";

                    break;

                default:

                    throw new IllegalStateException("Unexpected value: " + i);
            }

            SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
            system[i] = settings.getString("key" + i, string);
        }

        view = findViewById(R.id.background_all);
        textView = findViewById(R.id.textView0);

        button[2].setText(system[3] + " in a row");

        Mode();
        Music();
    }

    @Override
    protected void onPause() {
        super.onPause();

        StopMusic();
    }

    @Override
    public void onClick(View v) {

        //change variables' contents
        switch (v.getId()) {

            case R.id.button0:

                message = "About me";
                activity = "HtmlActivity";

                break;

            case R.id.button1:

                message = "Settings";
                activity = "SettingsActivity";

                break;

            case R.id.button2:

                message = system[3] + " in a row";
                activity = "XInARowActivity";

                break;
        }

        activity = "com.myworldbox.xinarow." + activity;

        if (v.getId() == R.id.button0) {

            message = "https://myworldbox.github.io";

            System(0, message);
        }

        Toast();

        try {

            //start new activity
            startActivity(new Intent(this, Class.forName(activity)));

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    }
}