package org.psm.imagelistpsm70;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 1. MainActivity 에서는 Fragment add,remove,replace만 담당
 * 2. 통신(Volley), json파싱(Gson), 이미지로드(Glide/Picasso)
 *    작업은 각각의 Fragment에서 담당
 */
public class MainActivity extends AppCompatActivity{

    FrameLayout mGlideContainer;
    FrameLayout mPicassoContainer;

    private GlideFragment mGlideFragment;
    private PicassoFragment mPicassoFragment;

    private SharedPreferences mSharedPreference; // 사용자가 선택한 설정 유지

    // 현재 보여지고 있는 프래그먼트의 상태 및 상태값
    private int mState;
    static final int STATE_GLIDE = 0;
    static final int STATE_PICASSO = 1;
    static final int STATE_BOTH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View v = findViewById(R.id.mainToolBar);
        // Toolbar
        Toolbar toolbar = (Toolbar)v.findViewById(R.id.customToolBar);
        setSupportActionBar(toolbar);

        // 사용자가 선택한 list setting 유지
        mSharedPreference = getSharedPreferences("pref", MODE_PRIVATE);
        if(mSharedPreference != null){
            mState = mSharedPreference.getInt("STATE_SETTING", STATE_BOTH);
        }

        // init fragment container layout
        mGlideContainer = (FrameLayout)findViewById(R.id.glideFragmentContainer);
        mPicassoContainer = (FrameLayout)findViewById(R.id.picassoFragmentContainer);

        if(savedInstanceState == null){
            // 처음 add
            initFragment(true);
        } else {
            // 가로/세로 변경 시 replace
            initFragment(false);
        }
    }

    /**
     * 1. 초기 init - fragment add
     * 2. 가로/세로 변경 시 - fragment replace
     * @param isFirst
     */
    public void initFragment(boolean isFirst){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mGlideFragment = new GlideFragment();
        mPicassoFragment = new PicassoFragment();

        switch (mState) {
            case STATE_GLIDE :
                if(isFirst) {
                    fragmentTransaction.add(mGlideContainer.getId(), mGlideFragment);
                } else {
                    fragmentTransaction.replace(mGlideContainer.getId(), mGlideFragment);
                }
                fragmentTransaction.commit();
                mPicassoContainer.setVisibility(View.GONE);
                return;
            case STATE_PICASSO :
                if(isFirst) {
                    fragmentTransaction.add(mPicassoContainer.getId(), mPicassoFragment);
                } else {
                    fragmentTransaction.replace(mPicassoContainer.getId(), mPicassoFragment);
                }
                fragmentTransaction.commit();
                mGlideContainer.setVisibility(View.GONE);
                return;
            case STATE_BOTH :
                if(isFirst) {
                    fragmentTransaction.add(mGlideContainer.getId(), mGlideFragment);
                    fragmentTransaction.add(mPicassoContainer.getId(), mPicassoFragment);
                } else {
                    fragmentTransaction.replace(mGlideContainer.getId(), mGlideFragment);
                    fragmentTransaction.replace(mPicassoContainer.getId(), mPicassoFragment);
                }
                fragmentTransaction.commit();
        }
    }

    /**
     * 상태값에 따라 Fragment replace를 담당
     * @param state
     */
    public void changeFragment(int state){
        mState = state; // 앱 구동중 사용될 상태값 변경
        mSharedPreference.edit().putInt("STATE_SETTING", state).commit(); // 앱 종료 후 다음 실행에 사용될 설정값 변경

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (mState){
            case STATE_GLIDE :
                fragmentTransaction.replace(mGlideContainer.getId(), mGlideFragment);
                fragmentTransaction.remove(mPicassoFragment);
                fragmentTransaction.commit();
                mGlideContainer.setVisibility(View.VISIBLE);
                mPicassoContainer.setVisibility(View.GONE);
                return;
            case STATE_PICASSO :
                fragmentTransaction.replace(mPicassoContainer.getId(), mPicassoFragment);
                fragmentTransaction.remove(mGlideFragment);
                fragmentTransaction.commit();
                mPicassoContainer.setVisibility(View.VISIBLE);
                mGlideContainer.setVisibility(View.GONE);
                return;
            case STATE_BOTH :
                fragmentTransaction.replace(mGlideContainer.getId(), mGlideFragment);
                fragmentTransaction.replace(mPicassoContainer.getId(), mPicassoFragment);
                fragmentTransaction.commit();
                mGlideContainer.setVisibility(View.VISIBLE);
                mPicassoContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * 선택한 메뉴에 따라서 프래그먼트 변경하도록
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.glide_settings:
                changeFragment(STATE_GLIDE);
                return true;
            case R.id.picasso_settings:
                changeFragment(STATE_PICASSO);
                return true;
            case R.id.both_settings:
                changeFragment(STATE_BOTH);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
