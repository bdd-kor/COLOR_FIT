package com.example.color_fit;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        getSupportActionBar().setElevation(0);


        ImageButton btnSTART = findViewById(R.id.rear_camera);
        ImageButton btnPASS = findViewById(R.id.btnPASS);

        String season = "";

        btnSTART.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                Intent intent = new Intent(StartActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        btnPASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
