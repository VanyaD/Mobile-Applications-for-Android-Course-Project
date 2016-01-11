package com.example.android.careandshare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

public class SubmitionActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button btnTakePhoto;
    private ImageView imgTakenPhoto;
    private static final int CAM_REQUEST = 1313;
    private ParseObject myParseObject;
    private Bitmap myPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submition);

        myParseObject = new ParseObject("CareAndShare_Problem");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new NavigationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public class btnTakePhotoClicker implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST)
        {
            myPhoto = (Bitmap) data.getExtras().get("data");

            imgTakenPhoto.setImageBitmap(myPhoto);
        }
    }

    public void GoToCameraPage(View view) {
        int current = viewPager.getCurrentItem();
        viewPager.setCurrentItem(current + 1);

        btnTakePhoto = (Button) findViewById(R.id.button_camera);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageView_photoTaken);
        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());
    }

    public void GoToDetailsPage(View view) {
        int current = viewPager.getCurrentItem();
        viewPager.setCurrentItem(current + 1);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        myPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();
        String imageName = generateUniqueImageName();
        ParseFile file = new ParseFile(imageName, image);
        file.saveInBackground();

        myParseObject.put("Image", file);
        /*
        try {
            myParseObject.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
    }
    private class NavigationPagerAdapter extends FragmentPagerAdapter {
        public NavigationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0: fragment = new SubmitionActivityFragment(); break;
                case 1: fragment = new CameraFragment(); break;
                case 2: fragment = new DetailsFragment(); break;
            }
            return fragment;
        }
    }

    private String generateUniqueImageName(){
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("MMddyyyyHHmmssSSS");
        String imageName = df.format(d);
        return imageName + ".png";
    }
}
