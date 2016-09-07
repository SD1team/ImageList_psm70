package org.psm.imagelistpsm70;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    ListView mListView;

    Button mGlideBtn, mPicassoBtn, mBothBtn;

    FrameLayout mGlideContainer;
    FrameLayout mPicassoContainer;

    private GlideFragment mGlideFragment;
    private PicassoFragment mPicassoFragment;

    private String mTMDbJson;

    static final int REQUEST_SUCCESS = 0;

    private int mState = STATE_GLIDE;
    static final int STATE_GLIDE = 0;
    static final int STATE_PICASSO = 1;
    static final int STATE_BOTH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();

        setFragment();
    }

    public void setLayout(){
        mGlideContainer = (FrameLayout)findViewById(R.id.glideFragmentContainer);
        mPicassoContainer = (FrameLayout)findViewById(R.id.picassoFragmentContainer);
        mPicassoContainer.setVisibility(View.GONE);

        mGlideBtn = (Button)findViewById(R.id.glideButton);
        mPicassoBtn = (Button)findViewById(R.id.picassoButton);
        mBothBtn = (Button)findViewById(R.id.bothButton);

        mGlideBtn.setOnClickListener(this);
        mPicassoBtn.setOnClickListener(this);
        mBothBtn.setOnClickListener(this);
    }

    public void setFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mGlideFragment = new GlideFragment();
        mPicassoFragment = new PicassoFragment();

        fragmentTransaction.add(mGlideContainer.getId(), mGlideFragment);
        //fragmentTransaction.add(mPicassoContainer.getId(), mPicassoFragment);

        fragmentTransaction.commit();
    }

    public void changeFragment(int state){
        mState = state;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (mState){
            case STATE_GLIDE :
                fragmentTransaction.replace(mGlideContainer.getId(), mGlideFragment);
                fragmentTransaction.remove(mPicassoFragment).commit();
                mGlideContainer.setVisibility(View.VISIBLE);
                mPicassoContainer.setVisibility(View.GONE);
                return;
            case STATE_PICASSO :
                fragmentTransaction.replace(mPicassoContainer.getId(), mPicassoFragment);
                fragmentTransaction.remove(mGlideFragment).commit();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.glideButton :
                changeFragment(STATE_GLIDE);
                return;
            case R.id.picassoButton :
                changeFragment(STATE_PICASSO);
                return;
            case R.id.bothButton :
                changeFragment(STATE_BOTH);
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
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
