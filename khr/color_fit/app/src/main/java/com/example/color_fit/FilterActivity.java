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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity{

    private RadioGroup PcGroup, CgGroup;
    private RadioButton rdSpring, rdSummer, rdAutumn, rdWinter, rdMyPC;
    private RadioButton rdOuter, rdTop, rdShirts, rdPants, rdSkirt, rdDress;
    private ImageButton btnSelect, btnReset;
    private int season, cloth = 0;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        PcGroup=findViewById(R.id.PcGroup);
        rdSpring=findViewById(R.id.rdSpring);
        rdSummer=findViewById(R.id.rdSummer);
        rdAutumn=findViewById(R.id.rdAutumn);
        rdWinter=findViewById(R.id.rdWinter);
        rdMyPC=findViewById(R.id.rdMyPC);

        CgGroup=findViewById(R.id.ClothGroup);
        rdOuter=findViewById(R.id.rdOuter);
        rdTop=findViewById(R.id.rdTop);
        rdShirts=findViewById(R.id.rdShirts);
        rdPants=findViewById(R.id.rdPants);
        rdSkirt=findViewById(R.id.rdSkirt);
        rdDress=findViewById(R.id.rdDress);

        switch(PcGroup.getCheckedRadioButtonId()) {
            case R.id.rdSpring :
                season = 1;
                break;
            case R.id.rdSummer:
                season = 2;
                break;
            case R.id.rdAutumn:
                season = 3;
                break;
            case R.id.rdWinter:
                season = 4;
                break;
            case R.id.rdMyPC:
                season = 5;
                break;
            default:
                Toast.makeText(getApplicationContext(), "퍼스널 컬러를 선택해 주세요", Toast.LENGTH_SHORT).show();
        }

        switch(CgGroup.getCheckedRadioButtonId()) {
            case R.id.rdOuter :
                cloth = 1;
                break;
            case R.id.rdTop:
                cloth = 2;
                break;
            case R.id.rdShirts:
                cloth = 3;
                break;
            case R.id.rdPants:
                cloth = 4;
                break;
            case R.id.rdSkirt:
                cloth = 5;
                break;
            case R.id.rdDress:
                cloth = 5;
                break;
            default:
                Toast.makeText(getApplicationContext(), "퍼스널 컬러를 선택해 주세요", Toast.LENGTH_SHORT).show();
        }

        btnSelect=findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, com.example.color_fit.SearchResultActivity.class);
                ArrayList<String> cateArr = new ArrayList<>();

                intent.putExtra("season", season);
                startActivity(intent);

            }
        });
    }


}
