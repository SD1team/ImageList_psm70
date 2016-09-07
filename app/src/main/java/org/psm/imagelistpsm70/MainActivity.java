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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set ToolBar
        View cardToolbar = (View)findViewById(R.id.mainToolBar);
        Toolbar toolbar = (Toolbar)cardToolbar.findViewById(R.id.customToolBar);
        setSupportActionBar(toolbar);

        mListView = (ListView)findViewById(R.id.mainListView);

        // request json string
        requestJsonString();
    }

    /**
     * JsonString 요청 통신
     *
     * Volley 통신 순서
     * 1. RequestQueue 생성
     * 2. Request 생성
     * 3. RequestQueue에 Request 추가
     */
    private void requestJsonString(){
        // TMDb NowPlaying 요청 Url
        String apiUrl = TMDbDefine.URL_HEAD + TMDbDefine.URL_PARAM_MOVIE_NOWPLAYING + TMDbDefine.URL_PARAM_API_KEY;

        // 1. RequestQueue 생성 > 싱글톤으로 된 MyVolleyRequest에는 RequestQueue가 존재
        // 2. Request 생성 > addToRequestQueue의 파라미터에서 new StringRequest
        // 3. RequestQueue에 Request 추가 > 통신 결과 리스너에서 이후 로직 시작
        MyVolleyRequest.getInstance(this).addToRequestQueue(new StringRequest(StringRequest.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 수신한 json string parsing
                        parseJsonFromGson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "통신 실패!", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    /**
     * 1. json parsing
     * 2. image url list 생성
     * 3. listView 세팅
     * @param json 통신 결과 json
     */
    private void parseJsonFromGson(String json){
        Gson gson = new Gson();
        // TMDbNowPlayingObj - 모든 데이터를 담고있는 객체
        TMDbNowPlayingObj nowPlayingObj = gson.fromJson(json, TMDbNowPlayingObj.class);

        // ResultsObj - 영화 하나의 정보를 담고있는 객체
        ArrayList<TMDbNowPlayingObj.ResultsObj> resultsObjs = nowPlayingObj.getResults();

        // image url list 생성
        ArrayList<String> imageUrlList = makeImageList(resultsObjs);

        // 이미지 보여줄 리스트뷰 세팅
        TMDbListAdapter listAdapter = new TMDbListAdapter(MainActivity.this, imageUrlList);
        mListView.setAdapter(listAdapter);
    }

    /**
     * 요청 결과 데이터 중에서, image url만 추출하여 리스트로 생성
     * @param resultsList 영화 정보들이 담긴 obj list
     * @return image url만 담긴 list
     */
    private ArrayList<String> makeImageList(ArrayList<TMDbNowPlayingObj.ResultsObj> resultsList) {

        // 이미지 url을 담을 ArrayList
        ArrayList<String> imagelist = new ArrayList<>();

        String posterPath = "";
        // 리스트 사이즈만큼 이미지 url을 담아낸다
        for(int i = 0; i < resultsList.size(); i++){
            posterPath = resultsList.get(i).poster_path;
            if(TextUtils.isEmpty(posterPath)){
                continue;
            }
            imagelist.add(TMDbDefine.IMAGE_LOAD_URL_HEAD + posterPath);
        }

        return imagelist;
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
