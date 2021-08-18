package com.example.color_fit;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        Button btnSTART = findViewById(R.id.rear_camera);
        TextView tvPASS = findViewById(R.id.tvPASS);

        btnSTART.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(StartActivity.this, com.example.color_fit.CameraActivity.class);
                intent.putExtra("mode", 0);
                startActivity(intent);
            }
        });

        tvPASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.color_fit.MainActivity.class);
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
