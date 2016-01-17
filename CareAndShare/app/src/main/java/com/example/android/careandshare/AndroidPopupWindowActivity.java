package com.example.android.careandshare;

/**
 * Created by vanya on 1/16/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class AndroidPopupWindowActivity extends Activity{
    /** Called when the activity is first created. */

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        textView = (TextView) findViewById(R.id.textview);

        textView.setText(Html.fromHtml(getString(R.string.help_instructions)).toString());
    }
}
