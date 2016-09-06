package org.psm.imagelistpsm70;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    String mTMDbJson;

    private static final String TITLE = "Volley/Glide/Card";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        // set ToolBar
        View cardToolbar = (View)findViewById(R.id.cardToolBar);
        Toolbar toolbar = (Toolbar)cardToolbar.findViewById(R.id.customToolBar);
        toolbar.setTitle(TITLE);
        setSupportActionBar(toolbar);

        setRecycleView();

        createRequestQueue();
    }

    private void setRecycleView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.cardRecycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void createRequestQueue(){
        // 요청 큐 초기화
        RequestQueue queue = Volley.newRequestQueue(this);
        // 요청 Url
        String apiUrl = TMDbDefine.URL_HEAD + TMDbDefine.URL_PARAM_MOVIE_NOWPLAYING + TMDbDefine.URL_PARAM_API_KEY;
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
        // 이미지 url을 담을 ArrayList
        ArrayList<String> imageUrlList = new ArrayList<>();

        String posterPath = "";
        // 리스트 사이즈만큼 이미지 url을 담아낸다
        for(int i = 0; i < resultsObjs.size(); i++){
            posterPath = resultsObjs.get(i).poster_path;
            if(TextUtils.isEmpty(posterPath)){
                continue;
            }
            imageUrlList.add(TMDbDefine.IMAGE_LOAD_URL_HEAD + posterPath);
        }

        TMDbRecycleAdapter mAdapter = new TMDbRecycleAdapter(imageUrlList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
