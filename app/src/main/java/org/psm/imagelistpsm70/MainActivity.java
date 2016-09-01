package org.psm.imagelistpsm70;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    ImageView mImageView;
    String mTMDbJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView)findViewById(R.id.textView);
        mImageView = (ImageView)findViewById(R.id.imageView);

        createRequestQueue();
/*
        if(TextUtils.isEmpty(mTMDbJson) == false){
            mTextView.setText(mTMDbJson);
        } else {
            mTextView.setText("empty");
        }*/

    }

    private void createRequestQueue(){
        // 요청 큐 초기화
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "https://api.themoviedb.org/3/movie/550?api_key=79735f49c034458cf449afabd0e893a3";

        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTMDbJson = response;
                        jsonParsingFromGson();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "통신 실패!", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }

    private void jsonParsingFromGson(){
        Gson gson = new Gson();
        TMDbObj obj = gson.fromJson(mTMDbJson, TMDbObj.class);

        String imageUrl = "http://image.tmdb.org/t/p/w500";
        imageUrl = imageUrl + obj.getBackdrop_path();
        mTextView.setText(imageUrl);
        Glide.with(this).load(imageUrl).into(mImageView);
    }

}
