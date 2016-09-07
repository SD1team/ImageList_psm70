package org.psm.imagelistpsm70;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by PSM on 2016. 9. 7..
 */
public class GlideFragment extends Fragment {

    ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_glide, container, false);

        mListView = (ListView)v.findViewById(R.id.glideListView);

        requestJsonString();

        return v;
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
        MyVolleyRequest.getInstance(getActivity()).addToRequestQueue(new StringRequest(StringRequest.Method.GET, apiUrl,
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
                        Toast.makeText(getActivity(), "통신 실패!", Toast.LENGTH_SHORT).show();
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
        TMDbListAdapter listAdapter = new TMDbListAdapter(getActivity(), imageUrlList);
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
}
