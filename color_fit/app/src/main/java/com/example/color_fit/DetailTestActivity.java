package com.example.color_fit;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DetailTestActivity extends AppCompatActivity {

    private ImageButton btnYes, btnNo;
    String pccode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //check.py 실행, 메모장 읽어오기
        readpc task = new readpc();
        try {
            pccode = task.execute("http://211.247.98.249/exepy.php").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] pcc = pccode.split("\\s");
        int[] pcc2 = new int[4];
        for(int i = 0; i < pcc2.length; i++){
            pcc2[i] = Integer.parseInt(pcc[i]);
        }


        btnYes = findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTestActivity.this, QuestionActivity.class);
                intent.putExtra("data", pcc2);
                startActivity(intent);
            }
        });

        btnNo = findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTestActivity.this, PCResultActivity.class);
                intent.putExtra("data", pcc2);
                startActivity(intent);
            }
        });

    }

    private class readpc extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String output = "";
            try {
                //연결 url 설정
                URL url = new URL(params[0]);

                //커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //연결되었으면
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    //연결된 코드가 리턴되면
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        for(int i=0; i<=1; i++){
                            String line = br.readLine();//웹상에 보이는 텍스트를 라인단위로 읽어 저장
                            if(line == null) {
                                System.out.println("그만! -> " + i);
                                break;
                            }
                            System.out.println("성공ㅇㅇ -> "+line);
                            i++;
                            output += line;
                        }
                        br.close();
                    }
                    conn.disconnect();
                }else{
                    System.out.println("실패ㅡㅡ");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return output;
        }
    }

}
