package com.example.android.careandshare;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import models.ImageAdapter;

public class ListActivity extends AppCompatActivity {
    String[] titles;
    Bitmap[] images;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("CareAndShare_Problem");
        titles = new String[] {
                "first", "second", "third","first", "second", "third","second", "third",
        };
        counter = -1;
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> problems, ParseException e) {
                    if (e == null) {
                        for (ParseObject problem : problems) {
                            counter++;
                            titles[counter] = problem.getString("Title");
                        }
                    } else {
                        // handle Parse Exception here
                    }
                }
            });

        int b = 5;
        GridView grid;
        int[] imageId = {
                R.mipmap.sample_0,
                R.mipmap.ic_launcher,
                R.mipmap.sample_0,
                R.mipmap.sample_0,
                R.mipmap.ic_launcher,
                R.mipmap.sample_0,
                R.mipmap.ic_launcher,
                R.mipmap.sample_0,
        };

            ImageAdapter adapter = new ImageAdapter(ListActivity.this, titles, imageId);
            grid = (GridView) findViewById(R.id.grid);
            grid.setAdapter(adapter);

        /*
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ListActivity.this, "You Clicked at " + titles.get(+position), Toast.LENGTH_SHORT).show();

            }
        });*/
    }
}
