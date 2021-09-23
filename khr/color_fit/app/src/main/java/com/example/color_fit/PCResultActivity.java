package com.example.color_fit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PCResultActivity extends AppCompatActivity {
    ImageButton btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcresult);

        TextView tv = findViewById(R.id.autumnex);
        tv.setText("가을 웜은 황색 빛을 디면서 차분하고 부드러운 느낌을 줍니다!\n 어울리는 색상은 명도와 채도가 낮은 진하고 탁한 톤입니다.\n 불투명하거나 파스텔 톤의 옅은색, 찬개열의 색, 선명한 원색 등은 피해야 합니다.");

        btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PCResultActivity.this, com.example.color_fit.MainActivity.class);
                //intent.putExtra("mode", 0);
                startActivity(intent);
            }
        });
    }
}
