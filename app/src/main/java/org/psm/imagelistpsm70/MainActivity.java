package org.psm.imagelistpsm70;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
public class MainActivity extends AppCompatActivity{

    FrameLayout mGlideContainer;
    FrameLayout mPicassoContainer;

    private GlideFragment mGlideFragment;
    private PicassoFragment mPicassoFragment;

    private SharedPreferences mSharedPreference;
    private int mState = STATE_GLIDE;
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

        initFragment();
    }

    /**
     * 설정에 따라 Fragment add
     */
    public void initFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mGlideFragment = new GlideFragment();
        mPicassoFragment = new PicassoFragment();

        switch (mState) {
            case STATE_GLIDE :
                fragmentTransaction.add(mGlideContainer.getId(), mGlideFragment);
                fragmentTransaction.commit();
                mPicassoContainer.setVisibility(View.GONE);
                return;
            case STATE_PICASSO :
                fragmentTransaction.add(mPicassoContainer.getId(), mPicassoFragment);
                fragmentTransaction.commit();
                mGlideContainer.setVisibility(View.GONE);
                return;
            case STATE_BOTH :
                fragmentTransaction.add(mGlideContainer.getId(), mGlideFragment);
                fragmentTransaction.add(mPicassoContainer.getId(), mPicassoFragment);
                fragmentTransaction.commit();
        }
    }

    /**
     * 상태값에 따라 Fragment replace
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
