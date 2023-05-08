package com.myworldbox.xinarow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.droidsonroids.relinker.ApkLibraryInstaller;

@SuppressLint("Registered")
abstract class Function extends AppCompatActivity implements View.OnClickListener {

    //declare variables for global use

    static int systemNumber = 8;

    int buttonNumber = 3;
    int random;
    int sound;
    int currentApiVersion;
    int[] color = new int[]{Color.parseColor("#000000"), Color.parseColor("#99ccff")};

    static String message = "";
    static String[] system = new String[systemNumber];

    static String PREFS_NAME = "";
    String activity;
    String music = "";

    MediaPlayer mediaPlayer;
    View view;
    TextView textView;
    Button[] button = new Button[buttonNumber];

    public void Mode() {

        if (system[1].equals("Mode: dark")) {

            if (view != null) {

                view.setBackgroundColor(color[0]);
            }

            if (textView != null) {

                textView.setTextColor(color[1]);
            }

        } else {

            if (view != null) {

                view.setBackgroundColor(color[1]);
            }

            if (textView != null) {

                textView.setTextColor(color[0]);
            }
        }
    }

    public void SetMusic() {

        //play music randomly
        random = (int) (Math.random() * 10 + 1);
        music = "music" + random;

        Resources resources = getResources();
        sound = resources.getIdentifier(music, "raw", getPackageName());

        //play music with loop
        mediaPlayer = MediaPlayer.create(getApplicationContext(), sound);
    }

    public void Music() {

        if (system[2].equals("Music: on")) {

            Resources resources = getResources();
            sound = resources.getIdentifier(music, "raw", getPackageName());

            //play music with loop
            mediaPlayer = MediaPlayer.create(getApplicationContext(), sound);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        } else {

            StopMusic();
        }
    }

    public void StopMusic() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

            //stop music
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void Toast() {

        //prompt reminder
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast));

        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void System(int i, String string) {

        //save preferences
        SharedPreferences setting = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("key" + i, string);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
    }
}
