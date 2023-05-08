package com.myworldbox.xinarow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//this class is for welcome screen
public class StartActivity extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //move to new activity when timer is ended
        new Handler().postDelayed(() -> {

            //move to next activity
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();

        }, 4000);
    }
}
