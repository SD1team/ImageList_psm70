package org.psm.imagelistpsm70;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by PSM on 2016. 9. 7..
 *
 * Volley RequestQueue를 싱글턴으로 관리
 * 여러곳에서 사용될 때 유용
 */
public class MyVolleyRequest {

    private static MyVolleyRequest mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    /**
     * 생성자에서 RequestQueue가 없다면 생성
     * @param context
     */
    private MyVolleyRequest(Context context){
        mCtx = context;

        mRequestQueue = getRequestQueue();
    }

    /**
     * Singleton Pattern
     */
    public static synchronized MyVolleyRequest getInstance(Context context){
        if (mInstance == null) {
            mInstance = new MyVolleyRequest(context);
        }
        return mInstance;
    }

    /**
     * RequestQueue가 없다면 생성, 있으면 리턴
     * @return RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * RequestQueue에 Request 추가
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
