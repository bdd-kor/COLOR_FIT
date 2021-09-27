package com.example.color_fit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


public class MainActivity extends AppCompatActivity implements UserAdapter.onItemListener{
    AutoScrollViewPager autoViewPager;
    private SearchView sv;
    static public String SearchQuery;
    private static String TAG = "phptest_MainActivity";
    private TextView tvPC;
    private LinearLayout llout;
    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ImageButton btnFilter;
    static public ArrayList<com.example.color_fit.ClothData> mArrayList;
    private UserAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    static public String mJsonString;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> data = new ArrayList<>();
        data.add("http://211.247.98.249/a.jpg");
        data.add("http://211.247.98.249/b.jpg");
        data.add("http://211.247.98.249/c.jpg");
        data.add("http://211.247.98.249/d.jpg");

        autoViewPager = (AutoScrollViewPager)findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(this, data);
        autoViewPager.setAdapter(scrollAdapter);
        autoViewPager.setInterval(5000);
        autoViewPager.startAutoScroll();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_result);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

//        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new UserAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //바로되도록..
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();


        GetData task = new GetData();
        task.execute("http://211.247.98.249/cloth.php");

        sv = findViewById(R.id.sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, com.example.color_fit.FilterActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onItemClicked(int position){
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }

        //에러가 있는 경우 에러메시지를 보여주고 아니면 JSON을 파싱하여 화면에 보여주는 showResult 메소드를 호출
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
//            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
//                mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        //doInBackground 메소드에서 서버에 있는 PHP 파일을 실행시키고 응답을 저장하고 스트링으로 변환하여 리턴
        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
//                String selectData = "xxxx=" + MainActivity.SearchQuery ;
                String selectData = "xxxx=" + "반팔";


                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(selectData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {
                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();
                return null;
            }
        }
    }


    private void showResult(){

        String TAG_JSON  = "Cloth";
        String TAG_IMAGE = "imgurl";
        String TAG_NAME  = "g_name";
        String TAG_PRICE = "price";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String imgurl = item.getString(TAG_IMAGE);
//                Drawable imgurl = Drawable.createFromPath(item.getString(TAG_IMAGE));
                String goods_name = item.getString(TAG_NAME);
                String price = item.getString(TAG_PRICE);

                com.example.color_fit.ClothData clothData = new com.example.color_fit.ClothData();

                clothData.setGoods_image(imgurl);
                clothData.setGoods_name(goods_name);
                clothData.setGoods_price(price + " 원");

                mArrayList.add(clothData);
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
