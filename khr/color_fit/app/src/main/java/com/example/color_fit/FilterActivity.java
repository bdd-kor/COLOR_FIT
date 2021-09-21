package com.example.color_fit;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FilterActivity extends AppCompatActivity implements UserAdapter.onItemListener{

    private ImageButton btnSpring,btnSummer, btnAutumn, btnWinter;
    private ImageButton btnAuter, btnTop, btnShirts, btnPants, btnSkirst, btnDess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }



    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
