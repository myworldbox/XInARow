package com.myworldbox.xinarow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;

import static com.myworldbox.xinarow.Function.*;

public class ChessView extends View {

    static boolean blackTerm = true;//turn for players

    boolean over = false;

    float strokeWidth = 3;
    float gridSize;//place of each grid
    float grid = Float.parseFloat(system[4]);
    float[] apple;

    int edge = 15;
    int win = Integer.parseInt(system[3]);
    static int blackScore = Integer.parseInt(system[6]);
    static int whiteScore = Integer.parseInt(system[7]);
    int boardColor = Color.parseColor("#669900");
    int gridColor = Color.parseColor("#000000");
    int whiteChess = Color.parseColor("#FFFFFF");
    int blackChess = Color.parseColor("#000000");
    int[][] flag;

    Paint paint;

    public ChessView(Context context) {
        super(context);

        init(null);
    }

    public ChessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public ChessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public void init(@Nullable AttributeSet set) {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(gridSize);
        over = false;
        flag = new int[(int) grid][(int) grid];
        apple = new float[(int) grid];

        Arrays.fill(apple, grid + 1);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(boardColor);//background color

        int windowWidth = getWidth();//width of window

        paint.setStrokeWidth(strokeWidth);
        gridSize = (windowWidth - 2 * edge) / grid;//top left position of first grid

        //draw n * n grids
        for (int i = 0; i <= grid; i++) {

            float newPlace = i * gridSize;

            paint.setColor(gridColor);
            canvas.drawLine(edge, edge + newPlace, windowWidth - edge, edge + newPlace, paint);//rows
            canvas.drawLine(edge + newPlace, edge, edge + newPlace, windowWidth - edge, paint);//columns
        }

        //place chess while touched
        for (int i = 0; i < flag.length; i++) {

            for (int j = 0; j < flag[i].length; j++) {

                switch (flag[i][j]) {

                    case 0:

                        continue;

                    case 1:

                        paint.setColor(blackChess);

                        break;

                    case 2:

                        paint.setColor(whiteChess);

                        break;
                }

                canvas.drawCircle(edge + gridSize / 2 + gridSize * i, edge + gridSize / 2 + gridSize * j, gridSize / 2 - strokeWidth / 2, paint);
                CheckWinner(i, j);

                if (!over && CheckWinner(i, j)) {

                    over = true;

                    if (blackTerm) {

                        whiteScore ++;

                    } else {

                        blackScore ++;
                    }

                    MainActivity.message = MainActivity.message + " wins";

                    new AlertDialog.Builder(getContext())
                            .setTitle("Game over")
                            .setMessage(MainActivity.message)
                            .setPositiveButton("ok", null)
                            .show();
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //check if game is over
        if (!over && event.getAction() == MotionEvent.ACTION_UP) {

            float x, y;

            x = event.getX() - edge;
            y = event.getY() - edge;

            int gridX, gridY;

            //gridX and gridY <= grid
            gridX = (int) (x / gridSize);
            gridY = (int) (y / gridSize);

            if (system[5] == "Gravity: on") {

                //logic for Apple Chess
                for (int i = 0; i < grid; i++) {

                    if (gridX == i && apple[i] - 1 >= 0) {


                        apple[i] = apple[i] - 1;
                        gridY = (int) apple[i];
                    }
                }
            }

            if (x > 0 && y > 0 && gridX < grid && gridY < grid) {

                if (flag[gridX][gridY] == 0) {

                    if (blackTerm) {

                        flag[gridX][gridY] = 1;

                    } else {

                        flag[gridX][gridY] = 2;
                    }

                    blackTerm = !blackTerm;

                    invalidate();
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public boolean CheckWinner(int x, int y) {

        boolean[] eightPlace = new boolean[8];//8 different clockwise position away from center
        int[] fourRow = new int[4];//4 different opposite direction

        for (int i = 0; i < win; i++) {

            //up
            if (!eightPlace[0] && (y - i) >= 0 && flag[x][y - i] == flag[x][y]) {

                fourRow[0]++;

            } else {

                eightPlace[0] = true;
            }

            //right-up
            if (!eightPlace[1] && (y - i) >= 0 && (x + i) < grid && flag[x + i][y - i] == flag[x][y]) {

                fourRow[1]++;

            } else {

                eightPlace[1] = true;
            }

            //right
            if (!eightPlace[2] && (x + i) < grid && flag[x + i][y] == flag[x][y]) {

                fourRow[2]++;

            } else {

                eightPlace[2] = true;
            }

            //right-down
            if (!eightPlace[3] && (x + i) < grid && (y + i) < grid && flag[x + i][y + i] == flag[x][y]) {

                fourRow[3]++;

            } else {

                eightPlace[3] = true;
            }

            //down
            if (!eightPlace[4] && (y + i) < grid && flag[x][y + i] == flag[x][y]) {

                fourRow[0]++;

            } else {
                eightPlace[4] = true;
            }

            //left-down
            if (!eightPlace[5] && (x - i) >= 0 && (y + i) < grid && flag[x - i][y + i] == flag[x][y]) {

                fourRow[1]++;

            } else {

                eightPlace[5] = true;
            }

            //left
            if (!eightPlace[6] && (x - i) >= 0 && flag[x - i][y] == flag[x][y]) {

                fourRow[2]++;

            } else {

                eightPlace[6] = true;
            }

            //left-up
            if (!eightPlace[7] && (y - i) >= 0 && (x - i) >= 0 && flag[x - i][y - i] == flag[x][y]) {

                fourRow[3]++;

            } else {

                eightPlace[7] = true;
            }
        }

        for (int value : fourRow) {

            if (value > win) {

                return true;
            }
        }

        return false;
    }
}
