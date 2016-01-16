package com.example.android.careandshare;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity {

    private TextView aboutCareAndShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aboutCareAndShare = (TextView) findViewById(R.id.tv_aboutCareAndShare);
        initParse();
       // Parse.initialize(this, "MvyH4EW3DSIkEsu8iLHaY6EN86M9oLpN1FbX40g9", "FshCrKVonUSYYFL9FYYqT0arVomLg1bhc2btbFeE");
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToSubmitionActivity(View view) {
        Intent i = new Intent(getApplicationContext(),SubmitionActivity.class);
        startActivity(i);
    }

    public void bt_aboutCareAndShare(View view) {
        if(aboutCareAndShare.getVisibility() == View.INVISIBLE){
            aboutCareAndShare.setVisibility(View.VISIBLE);
        } else {
            aboutCareAndShare.setVisibility(View.INVISIBLE);
        }
    }

    public void goToListActivity(View view) {
        Intent i = new Intent(getApplicationContext(),ListActivity.class);
        startActivity(i);
    }
}
