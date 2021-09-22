package com.example.color_fit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FilterActivity extends AppCompatActivity implements UserAdapter.onItemListener{

    private ImageButton btnSpring,btnSummer, btnAutumn, btnWinter;
    private ImageButton btnOuter, btnTop, btnShirts, btnPants, btnSkirst, btnDress;
    private boolean sspring, ssummer, sautumn, swinter = true;
    private boolean souter, s_top, sshirts,spants, sskirts, sdress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        btnSpring = findViewById(R.id.btnSpring);
        btnSpring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (sspring == true){
                        btnSpring.setImageResource(R.drawable.b_scat_spring);
                        sspring = false;
                    }
                    else {
                        btnSpring.setImageResource(R.drawable.a_scat_spring);
                        sspring = true;
                    }

            }
        });
        btnSummer = findViewById(R.id.btnSummer);
        btnSummer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssummer == true){
                    btnSummer.setImageResource(R.drawable.b_scat_summer);
                    ssummer = false;
                }
                else {
                    btnSummer.setImageResource(R.drawable.a_scat_summer);
                    ssummer = true;
                }

            }
        });
        btnAutumn = findViewById(R.id.btnAutumn);
        btnAutumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sautumn == true){
                    btnAutumn.setImageResource(R.drawable.b_scat_autumn);
                    sautumn = false;
                }
                else {
                    btnAutumn.setImageResource(R.drawable.a_scat_autumn);
                    sautumn = true;
                }

            }
        });
        btnWinter = findViewById(R.id.btnWinter);
        btnWinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swinter == true){
                    btnWinter.setImageResource(R.drawable.b_scat_winter);
                    swinter = false;
                }
                else {
                    btnWinter.setImageResource(R.drawable.a_scat_winter);
                    swinter = true;
                }

            }
        });

        btnSpring = findViewById(R.id.btnSpring);
        btnSpring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sspring == true){
                    btnSpring.setImageResource(R.drawable.b_scat_spring);
                    sspring = false;
                }
                else {
                    btnSpring.setImageResource(R.drawable.a_scat_spring);
                    sspring = true;
                }

            }
        });

        btnOuter = findViewById(R.id.btnOuter);
        btnOuter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (souter == true){
                    btnOuter.setImageResource(R.drawable.b_ccat_outer);
                    souter = false;
                }
                else {
                    btnOuter.setImageResource(R.drawable.a_ccat_outer);
                    souter = true;
                }

            }
        });

        btnTop = findViewById(R.id.btnTop);
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_top == true){
                    btnTop.setImageResource(R.drawable.b_ccat_top);
                    s_top = false;
                }
                else {
                    btnTop.setImageResource(R.drawable.a_ccat_top);
                    s_top = true;
                }

            }
        });

        btnShirts = findViewById(R.id.btnShirts);
        btnShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sshirts == true){
                    btnShirts.setImageResource(R.drawable.b_ccat_shirts);
                    sshirts = false;
                }
                else {
                    btnShirts.setImageResource(R.drawable.a_ccat_shirts);
                    sshirts = true;
                }

            }
        });
    }




    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
