package com.myworldbox.xinarow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import static com.myworldbox.xinarow.ChessView.blackTerm;

public class XInARowActivity extends MainActivity {

    int textNumber = 3;

    TextView[] textView = new TextView[textNumber];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_in_a_row);

        for (int i = 0; i < textNumber; i++) {

            String textID = "textView" + i;
            int resourceID = getResources().getIdentifier(textID, "id", getPackageName());
            textView[i] = findViewById(resourceID);
        }

        ChangeText(null);

        final ChessView chessView = findViewById(R.id.chessBoard);

        Button button0 = findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                ChessView.blackScore = 0;
                ChessView.whiteScore = 0;

                ChangeText(chessView);

                chessView.init(null);

                message = "Restart";

                Toast();
            }
        });


        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                System(6, String.valueOf(ChessView.blackScore));
                System(7, String.valueOf(ChessView.whiteScore));

                message = "Save";

                Toast();
            }
        });

        new ChessView(this);
    }

    @SuppressLint("SetTextI18n")
    public void ChangeText(View view) {

        ChangeTerm();

        textView[0].setText(message + " term");
        textView[1].setText("Black: " + ChessView.blackScore);
        textView[2].setText("White: " + ChessView.whiteScore);
    }

    public static void ChangeTerm() {

        if (blackTerm) {

            message = "Black";

        } else {

            message = "White";
        }
    }
}
