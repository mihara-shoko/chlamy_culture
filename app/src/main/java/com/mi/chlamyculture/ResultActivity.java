package com.mi.chlamyculture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView elementScoreLabel = findViewById(R.id.element_score);
        TextView cells = findViewById(R.id.cells);
        TextView highScoreLabel = findViewById(R.id.high_score);

        int carbonScore = getIntent().getIntExtra("C_SCORE", 0);
        int nitrogenScore = getIntent().getIntExtra("N_SCORE", 0);
        int phosphorusScore = getIntent().getIntExtra("P_SCORE", 0);
        int calciumScore = getIntent().getIntExtra("Ca_SCORE", 0);
        elementScoreLabel.setText("C = " + carbonScore + ",  N = " + nitrogenScore +
                " , P = " + phosphorusScore + " , Ca = " + calciumScore);

        int[] elements = {carbonScore / 3, nitrogenScore / 2, phosphorusScore, calciumScore};
        Arrays.sort(elements);
        int cellNumber = elements[0];
        cells.setText("Cell Number = " + cellNumber);

        SharedPreferences sp = getSharedPreferences("GAME_SCORE", MODE_PRIVATE);
        int highScore = sp.getInt("HIGH_SCORE", 0);

        if (cellNumber > highScore){
            highScoreLabel.setText("High Score = " + cellNumber);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("HIGH_SCORE", cellNumber);
            editor.apply();
        }else{
            highScoreLabel.setText("High Score = " + highScore);
        }

        Button restartButton = findViewById(R.id.btn_restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed(){}




}
