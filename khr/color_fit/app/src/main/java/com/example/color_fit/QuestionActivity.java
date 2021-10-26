package com.example.color_fit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {

    private Button btnResult;
    private RadioGroup rdgQ1, rdgQ2;
    private RadioButton rdBlack, rdRed, rdSilver, rdGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, PCResultActivity.class);
                startActivity(intent);
            }
        });

        rdgQ1 = findViewById(R.id.rdgQ1);
        rdBlack = findViewById(R.id.rdBlack);
        rdRed = findViewById(R.id.rdRed);

        rdgQ2 = findViewById(R.id.rdgQ2);
        rdSilver = findViewById(R.id.rdSilver);
        rdGold = findViewById(R.id.rdGold);

        rdgQ1.setVisibility(android.view.View.VISIBLE);
        rdgQ1.clearCheck();
        rdgQ2.setVisibility(android.view.View.VISIBLE);
        rdgQ2.clearCheck();

        rdgQ1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.rdBlack) {

                } else if (i == R.id.rdRed) {

                }
            }
        });

        rdgQ2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.rdSilver) {

                } else if (i == R.id.rdGold) {

                }
            }
        });

    }
}
