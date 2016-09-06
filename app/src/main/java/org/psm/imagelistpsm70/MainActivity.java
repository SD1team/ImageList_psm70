package org.psm.imagelistpsm70;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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

    ListView mListView;
    String mTMDbJson;

    private static final String TITLE = "Volley/Glide/ListView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set ToolBar
        View cardToolbar = (View)findViewById(R.id.mainToolBar);
        Toolbar toolbar = (Toolbar)cardToolbar.findViewById(R.id.customToolBar);
        toolbar.setTitle(TITLE);
        setSupportActionBar(toolbar);

        mListView = (ListView)findViewById(R.id.mainListView);

        createRequestQueue();
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

        TMDbListAdapter listAdapter = new TMDbListAdapter(MainActivity.this, imageUrlList);
        mListView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, CardActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
