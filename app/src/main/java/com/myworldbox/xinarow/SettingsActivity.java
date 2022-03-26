package com.myworldbox.xinarow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

//this class is for settings
public class SettingsActivity extends MainActivity {

    TextView textView1;
    TextView textView2;

    EditText editText0;
    EditText editText1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        editText0 = findViewById(R.id.editText0);
        editText1 = findViewById(R.id.editText1);

        updateEditText();

        Button button = findViewById(R.id.button_apply);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(editText0.getText().toString()) <= Integer.parseInt(editText1.getText().toString()) && Integer.parseInt(editText0.getText().toString()) > 0 && Integer.parseInt(editText1.getText().toString()) > 0) {

                    system[3] = editText0.getText().toString();
                    system[4] = editText1.getText().toString();

                    System(3, system[3]);
                    System(4, system[4]);

                    message = "X: " + system[3] + "\nGrids: " + system[4];

                    updateEditText();

                } else {

                    message = "X should be smaller than value of grids, and both of them should be greater than 0.";
                }

                Toast();
            }
        });

        Switch switch0 = findViewById(R.id.Switch0);
        switch0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (system[5].equals("Gravity: off")) {

                    message = "Gravity: on";

                } else {

                    message = "Gravity: off";
                }

                system[5] = message;
                Toast();
                System(5, message);
            }
        });

        Switch switch1 = findViewById(R.id.Switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (system[1].equals("Mode: dark")) {

                    message = "Mode: light";

                } else {

                    message = "Mode: dark";
                }

                system[1] = message;
                Toast();
                Mode();
                System(1, message);
            }
        });

        Switch switch2 = findViewById(R.id.Switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (system[2].equals("Music: on")) {

                    message = "Music: off";

                } else {

                    message = "Music: on";
                }

                system[2] = message;

                Toast();
                Mode();
                Music();
                System(2, message);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void updateEditText() {

        textView1.setText("X: " + system[3]);
        textView2.setText("Grids: " + system[4]);

        editText0.setText(system[3]);
        editText1.setText(system[4]);
    }
}
