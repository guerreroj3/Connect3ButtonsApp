package com.aslisevil.connect3game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 1 -- For active player 0 = yellow, 1 = red

    // 2 -- For winning we need to store all 9 places in an array and show the spots with predefined integers
    //Like
    // if the spot has value 0 that means there is a yellow coin on it
    // if its 1 that means there is a red coin on it
    // if its 2 then there is no coin on it

    // 3 -- We need to store winning positions so that we can check if someone has won

    // 4 -- //We want to know if the game is over or not
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, // horizontal
            {0,3,6}, {1,4,7}, {2,5,8}, // vertical
            {0,4,8}, {2,4,6}            // cross
    };
    boolean isGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view)
    {

        ImageView counter = (ImageView) view;
        int position = Integer.parseInt(counter.getTag().toString());

        //If activePlayer is 0 we will drop a yellow coin
        //else if its 1 we will drop a red coin

            if(gameState[position] == 2 && !isGameOver)
            {
                counter.setTranslationY(-1000f);
                gameState[position] = activePlayer;
                if(activePlayer == 0)
                {
                    counter.setImageResource(R.drawable.yellow);
                    activePlayer = 1;
                }
                else if(activePlayer == 1)
                {
                    counter.setImageResource(R.drawable.red);
                    activePlayer = 0;
                }

                counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
                System.out.println(gameState[position]);
                String winner = "Red";

                for(int winningPosition[] : winningPositions)
                {
                    if(gameState[winningPosition[0]] == gameState[winningPosition[1]]
                            && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                            && gameState[winningPosition[0]] != 2)
                    {
                        if(gameState[winningPosition[0]] == 0)
                            winner = "Yellow";

                        TextView winMessage = (TextView)findViewById(R.id.displayMessage);
                        winMessage.setText(winner + " has won!");

                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.messageLayout);
                        linearLayout.setVisibility(View.VISIBLE);

                        isGameOver = true;
                    }
                    else
                    {
                        boolean emptySpot = false;
                        for(int i = 0; i< gameState.length; i++)
                        {
                            if(gameState[i] == 2)
                                emptySpot = true;
                        }

                        if(!emptySpot)
                        {
                            TextView textView = (TextView)findViewById(R.id.displayMessage);
                            textView.setText("It's A Draw!");

                            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.messageLayout);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }


    }

    public void playAgain(View view)
    {
        isGameOver = false;
        activePlayer = 0;
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.messageLayout);
        linearLayout.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        for(int i = 0; i<gameState.length; i++)
        {
            gameState[i] = 2;
        }

        //FOR IMAGES :
            // We are going to loop through all of the grid elements -which is only images in this case
            // And set all of their resources as null
        for(int i = 0; i<gridLayout.getChildCount(); i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}
