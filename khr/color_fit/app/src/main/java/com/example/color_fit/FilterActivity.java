package com.example.color_fit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity{

    private RadioGroup PcGroup, CgGroup;
    private RadioButton rdSpring, rdSummer, rdAutumn, rdWinter;
    private RadioButton rdOuter, rdTop, rdShirts, rdPants, rdSkirt, rdDress;
    private ImageButton btnSelect, btnReset;
    private String season, cloth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        PcGroup=findViewById(R.id.PcGroup);
        rdSpring=findViewById(R.id.rdSpring);
        rdSummer=findViewById(R.id.rdSummer);
        rdAutumn=findViewById(R.id.rdAutumn);
        rdWinter=findViewById(R.id.rdWinter);


        CgGroup=findViewById(R.id.ClothGroup);
        rdOuter=findViewById(R.id.rdOuter);
        rdTop=findViewById(R.id.rdTop);
        rdShirts=findViewById(R.id.rdShirts);
        rdPants=findViewById(R.id.rdPants);
        rdSkirt=findViewById(R.id.rdSkirt);
        rdDress=findViewById(R.id.rdDress);


        PcGroup.setVisibility(android.view.View.VISIBLE);
        PcGroup.clearCheck();

        CgGroup.setVisibility(android.view.View.VISIBLE);
        CgGroup.clearCheck();


        PcGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.rdSpring) {
                    season = "봄";
                } else if (i == R.id.rdSummer) {
                    season = "여름";
                } else if (i == R.id.rdAutumn) {
                    season = "가을";
                } else if (i == R.id.rdWinter) {
                    season = "겨울";
                }

            }
        });


        CgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.rdOuter) {
                    cloth = "아우터";
                } else if (i == R.id.rdSummer) {
                    cloth = "상의";
                } else if (i == R.id.rdAutumn) {
                    cloth = "셔츠/블라우스";
                } else if (i == R.id.rdWinter) {
                    cloth = "팬츠/데님";
                }else if (i == R.id.rdWinter) {
                    cloth = "스커트";
                }else if (i == R.id.rdWinter) {
                    cloth = "원피스";
                }
            }
        });

        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PcGroup.clearCheck();
                CgGroup.clearCheck();
            }
        });


        btnSelect=findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, com.example.color_fit.SearchResultActivity.class);

                intent.putExtra("season", season);
                intent.putExtra("cloth", cloth);

                startActivity(intent);

            }
        });
    }


}
