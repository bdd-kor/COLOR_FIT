package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


public class MainActivity extends AppCompatActivity {
    AutoScrollViewPager autoViewPager;
    private Button btn_cloth;
    private SearchView sv;
    static public String SearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> data = new ArrayList<>();
        data.add("http://3.37.62.27/aa.jpg");
        data.add("http://3.37.62.27/bb.jpg");
        data.add("http://3.37.62.27/cc.gif");
        data.add("http://3.37.62.27/dd.png");

        autoViewPager = (AutoScrollViewPager)findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(this, data);
        autoViewPager.setAdapter(scrollAdapter);
        autoViewPager.setInterval(5000);
        autoViewPager.startAutoScroll();

        sv = findViewById(R.id.sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {    //검색버튼 눌렸을때
                Toast.makeText(MainActivity.this, "의류, 화장품 선택으로 검색", Toast.LENGTH_SHORT).show();
                SearchQuery = query;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {      // 검색어 변경됐을때
                return false;
            }
        });

        btn_cloth = findViewById(R.id.btn_cloth);
        btn_cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });
    }
}
