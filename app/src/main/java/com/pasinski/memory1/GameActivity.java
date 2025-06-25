package com.pasinski.memory1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {
    Button[] Buttons = new Button[8];
    Integer[] CardValues = {0, 1, 2, 3, 0, 1, 2, 3};
    int FirstCardId = -1;
    boolean Reveal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Collections.shuffle(Arrays.asList(CardValues));

        for(int i=0;i<Buttons.length;i++)
        {
            String ButtonId = "button" +i;
            int ButId = getResources().getIdentifier(ButtonId, "id", getPackageName());
            Buttons[i] = findViewById(ButId);
            final int index = i;

            Buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Reveal|| Buttons[index].getText()!= "") return;

                    Buttons[index].setText(String.valueOf(CardValues[index]));

                    if(FirstCardId==-1)
                    {
                        FirstCardId = index;

                    }
                    else
                    {
                        Reveal=true;
                        new Handler().postDelayed(()->Check(index),1000);


                    }
                }

            });
        }



    }
    private void Check(int SecondCardId)
    {
        if(CardValues[FirstCardId].equals(CardValues[SecondCardId]))
        {
            Buttons[FirstCardId].setEnabled(false);
            Buttons[SecondCardId].setEnabled(false);
        }
        else
        {
            Buttons[FirstCardId].setText("");
            Buttons[SecondCardId].setText("");
        }
        FirstCardId =-1;
        Reveal = false;
        if(Win())
        {
            Intent intent1 =  new Intent(GameActivity.this, WinActivity.class);
            startActivity(intent1);
            finish();
        }
    }
    private Boolean Win() {
        for (Button b : Buttons) {
            if (b.isEnabled()) {
                Log.d("Game", "Button still enabled");
                return false;
            }
        }
        Log.d("Game", "All buttons disabled - starting WinActivity");
        return true;
    }
}