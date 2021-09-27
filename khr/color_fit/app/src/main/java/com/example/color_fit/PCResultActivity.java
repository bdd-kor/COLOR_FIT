package com.example.color_fit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PCResultActivity extends AppCompatActivity {
    ImageButton btnGo;
    TextView tvRtAutumn, tv;
    private String season = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcresult);

        Intent intent = getIntent();
        int pc = intent.getIntExtra("season",0);

        tv = findViewById(R.id.autumnex);
        tvRtAutumn = findViewById(R.id.tvRtAutumn);

        if(pc == 0){
            tvRtAutumn.setText("봄 웜톤 ");
            tv.setText("봄 웜\n" +
                    "봄 웜은 온화하고 부드러운 것이 특징이며, 따뜻한 톤을 지니고 있습니다!\n\n" +
                    "어울리는 색상은 명도는 높고 채도는 낮은 밝고 생기있는 톤입니다.\n\n" +
                    "흰빛과 푸른 빛이 감도는 색상과 찬 느낌의 무겁고 칙칙한 색은 피해야 합니다.");
            season = "봄";
        }
        else if(pc==1){
            tvRtAutumn.setText("여름 쿨톤 ");
            tv.setText("여름 쿨\n" +
                    "여름 쿨은 차가우면서도 부드러운 느낌을 겸비한 이지적인 분위기로 친근감을 줍니다!\n\n" +
                    "어울리는 색상은 명도는 높고 채도는 낮은 톤으로 흰색과 파랑 톤에 기운이 느껴지는 색입니다.\n\n" +
                    "검은색과 너무 어두운 색, 금속성의 반사적인 색, 노란 기미가 있는 색은 피해야 합니다.");
            season = "여름";
        }
        else if(pc==2){
            tvRtAutumn.setText("가을 웜톤 ");
            tv.setText("가을 웜\n" +
                    "가을 웜은 황색 빛을 디면서 차분하고 부드러운 느낌을 줍니다!\n\n" +
                "어울리는 색상은 명도와 채도가 낮은 진하고 탁한 톤입니다.\n\n" +
                "불투명하거나 파스텔 톤의 옅은색, 찬개열의 색, 선명한 원색 등은 피해야 합니다.");
            season = "가을";
        }
        else{
            tvRtAutumn.setText("겨울 쿨톤 ");
            tv.setText("겨울 쿨\n" +
                    "겨울 쿨은 강하면서 선명하고 다소 날카로운 느낌을 줍니다!\n\n" +
                    "어울리는 삭상은 명도는 낮고 채도는 높은 푸르면서 흰색이 기본이 되는 색상입니다.\n\n" +
                    "황급색 계열의 색은 피해야 합니다.");
            season = "겨울";
        }





        btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PCResultActivity.this, com.example.color_fit.MainActivity.class);
                intent.putExtra("season", season);
                startActivity(intent);
            }
        });
    }
}
