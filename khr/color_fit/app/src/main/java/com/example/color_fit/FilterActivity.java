package com.example.color_fit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity implements UserAdapter.onItemListener{

    private ImageButton btnSpring,btnSummer, btnAutumn, btnWinter;
    private ImageButton btnOuter, btnTop, btnShirts, btnPants, btnSkirt, btnDress;
    private boolean sspring, ssummer, sautumn, swinter = true;
    private boolean souter, s_top, sshirts,spants, sskirt, sdress = true;
    private ImageButton btnReset, btnSelect;


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

        btnPants = findViewById(R.id.btnPants);
        btnPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spants == true){
                    btnPants.setImageResource(R.drawable.b_ccat_pants);
                    spants = false;
                }
                else {
                    btnPants.setImageResource(R.drawable.a_ccat_pants);
                    spants = true;
                }

            }
        });

        btnSkirt = findViewById(R.id.btnSkirt);
        btnSkirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sskirt == true){
                    btnSkirt.setImageResource(R.drawable.b_ccat_skirt);
                    sskirt = false;
                }
                else {
                    btnSkirt.setImageResource(R.drawable.a_ccat_skirt);
                    sskirt = true;
                }

            }
        });

        btnDress = findViewById(R.id.btnDress);
        btnDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sdress == true){
                    btnDress.setImageResource(R.drawable.b_ccat_dress);
                    sdress = false;
                }
                else {
                    btnDress.setImageResource(R.drawable.a_ccat_dress);
                    sdress = true;
                }

            }
        });

        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sspring = ssummer = sautumn = swinter = false;
                btnSpring.setImageResource(R.drawable.a_scat_spring);
                btnSummer.setImageResource(R.drawable.a_scat_summer);
                btnAutumn.setImageResource(R.drawable.a_scat_autumn);
                btnWinter.setImageResource(R.drawable.a_scat_winter);
                souter = s_top = sshirts = spants = sskirt = sdress = false;
                btnOuter.setImageResource(R.drawable.a_ccat_outer);
                btnTop.setImageResource(R.drawable.a_ccat_top);
                btnShirts.setImageResource(R.drawable.a_ccat_shirts);
                btnPants.setImageResource(R.drawable.a_ccat_pants);
                btnSkirt.setImageResource(R.drawable.a_ccat_skirt);
                btnDress.setImageResource(R.drawable.a_ccat_dress);
            }
        });

        btnSelect = findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, com.example.color_fit.SearchResultActivity.class);
                ArrayList<String> cateArr = new ArrayList<>();
                if(sspring==true){cateArr.add("봄");}
                if(ssummer==true){cateArr.add("여름");}
                if(sautumn==true){cateArr.add("가을");}
                if(swinter==true){cateArr.add("겨울");}
                if(souter==true){cateArr.add("아우터");}
                if(s_top==true){cateArr.add("상의");}
                if(sshirts==true){cateArr.add("셔츠/블라우스");}
                if(spants==true){cateArr.add("팬츠/데님");}
                if(sskirt==true){cateArr.add("스커트");}
                if(sdress==true){cateArr.add("원피스");}


                intent.putExtra("category", cateArr);
                startActivity(intent);

            }
        });
    }




    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
