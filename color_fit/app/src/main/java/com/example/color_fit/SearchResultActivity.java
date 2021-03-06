package com.example.color_fit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Arrays;

public class SearchResultActivity extends AppCompatActivity {

    private static String TAG = "phptest_SearchResultActivity";
    static public ArrayList<ClothData> mArrayList;
    private UserAdapter mAdapter;
    private RecyclerView mRecyclerView;
    static public String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        String cg = intent.getStringExtra("cloth");
        String pc = intent.getStringExtra("season");


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_result2);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));

        mArrayList = new ArrayList<>();
        mAdapter = new UserAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        //바로되도록..
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();



        GetData task = new GetData();
        task.execute("http://211.247.98.249/test365.php", cg, pc);


    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SearchResultActivity.this,
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
//                String selectData1 = "g_pccode="+params[1];
                String selectData1 =  (String)params[1];
                String selectData2 =  (String)params[2];
                String postParameters = "category=" + selectData1 + "&g_pccode=" + selectData2;

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();

                outputStream.write(postParameters.getBytes("UTF-8"));
//                outputStream.write(selectData1.getBytes("UTF-8"));
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

                ClothData clothData = new ClothData();

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
