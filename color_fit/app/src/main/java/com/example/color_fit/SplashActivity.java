package com.example.color_fit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(3500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }
}
