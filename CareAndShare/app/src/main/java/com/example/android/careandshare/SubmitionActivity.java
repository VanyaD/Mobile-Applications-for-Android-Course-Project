package com.example.android.careandshare;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

public class SubmitionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ViewPager viewPager;
    private Button btnTakePhoto;
    private ImageView imgTakenPhoto;
    private static final int CAM_REQUEST = 1313;
    private ParseObject myParseObject;
    private Bitmap myPhoto;
    private GoogleMap mMap;
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submition);

        mMapView = (MapView) findViewById(R.id.mapView);

        myParseObject = new ParseObject("CareAndShare_Problem");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new NavigationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(100, 100);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/

    public class btnTakePhotoClicker implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
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
                case 0:
                    fragment = new SubmitionActivityFragment();
                    break;
                case 1:
                    fragment = new CameraFragment();
                    break;
                case 2:
                    fragment = new DetailsFragment();
                    break;
            }
            return fragment;
        }
    }

    private String generateUniqueImageName() {
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("MMddyyyyHHmmssSSS");
        String imageName = df.format(d);
        return imageName + ".png";
    }
/*
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        //  mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
      //  double latitude = myLocation.getLatitude();

        // Get longitude of the current location
      //  double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!").snippet("Consider yourself located"));
    }*/
}
