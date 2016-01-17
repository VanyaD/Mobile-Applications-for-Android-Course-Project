package com.example.android.careandshare;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    private TextView aboutCareAndShare;
    private Animation slideDown;
    private Animation slideUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideDown.setAnimationListener(this);
        slideUp.setAnimationListener(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        aboutCareAndShare = (TextView) findViewById(R.id.tv_aboutCareAndShare);
        initParse();
    }

    private void initParse() {
        try {
            Parse.initialize(getApplication(), "MvyH4EW3DSIkEsu8iLHaY6EN86M9oLpN1FbX40g9", "FshCrKVonUSYYFL9FYYqT0arVomLg1bhc2btbFeE");
            ParseInstallation.getCurrentInstallation().saveInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        LayoutInflater inflater = (LayoutInflater)
//                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        DisplayMetrics dm = new DisplayMetrics();
//        AndroidPopupWindowActivity and = new AndroidPopupWindowActivity();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;

//        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_help) {
            goToAndroidPopupWindowActivity(item.getActionView());
            return true;
        }

        if(id == R.id.action_add){
            goToSubmitionActivity(item.getActionView());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToAndroidPopupWindowActivity(View actionView) {
        Intent i = new Intent(getApplicationContext(),AndroidPopupWindowActivity.class);
        startActivity(i);
    }

    public void goToSubmitionActivity(View view) {
        Intent i = new Intent(getApplicationContext(),SubmitionActivity.class);
        startActivity(i);
    }

    public void bt_aboutCareAndShare(View view) {
        if(aboutCareAndShare.getVisibility() == View.INVISIBLE){
            aboutCareAndShare.setVisibility(View.VISIBLE);
            aboutCareAndShare.startAnimation(slideDown);
        } else {
            aboutCareAndShare.setVisibility(View.INVISIBLE);
            aboutCareAndShare.startAnimation(slideUp);
        }
    }

    public void goToListActivity(View view) {
        Intent i = new Intent(getApplicationContext(),ListActivity.class);
        startActivity(i);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
