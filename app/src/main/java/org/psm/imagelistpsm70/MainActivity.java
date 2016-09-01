package org.psm.imagelistpsm70;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

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
    }

    private void createRequestQueue(){
        // 요청 큐 초기화
        RequestQueue queue = Volley.newRequestQueue(this);
        // 요청 Url
        String apiUrl = TMDbDefine.URL_HEAD + TMDbDefine.URL_MOVIE_NOWPLAYING + TMDbDefine.API_KEY;
        // Json 요청 통신
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 수신한 Json String
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
        TMDbNowPlayingObj nowPlayingObj = gson.fromJson(mTMDbJson, TMDbNowPlayingObj.class);

        ArrayList<TMDbNowPlayingObj.ResultsObj> resultsObjs = nowPlayingObj.getResults();

/*        for(int i = 0; i < resultsObjs.size(); i++){

        }*/
        String imageUrlTail = resultsObjs.get(0).backdrop_path;
        String imageUrl = TMDbDefine.IMAGE_LOAD_URL_HEAD + imageUrlTail;
        mTextView.setText(imageUrl);
        Glide.with(this).load(imageUrl).into(mImageView);
    }

}
