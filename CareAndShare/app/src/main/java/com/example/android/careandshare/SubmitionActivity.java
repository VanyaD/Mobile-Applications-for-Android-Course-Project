package com.example.android.careandshare;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class SubmitionActivity extends AppCompatActivity/* implements OnMapReadyCallback*/ {
    private ViewPager viewPager;
    private Button btnTakePhoto;
    private ImageView imgTakenPhoto;
    private static final int CAM_REQUEST = 1313;
    private ParseObject myParseObject;
    private Bitmap myPhoto;
    private Button getMyLocation;
    private GoogleMap mMap;
    private MapView mMapView;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submition);

//        mMapView = (MapView) findViewById(R.id.mapView);

        myParseObject = new ParseObject("CareAndShare_Problem");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new NavigationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        mMapView = (MapView) findViewById(R.id.mapView);
      //  mMapView.getMapAsync(this);

        //locationManager = (LocationManager)
        //        getSystemService(Context.LOCATION_SERVICE);


    }

  /*  @Override
   public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }*/
/*
    public void findMyLocation(View view) {
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    10);
        }

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Toast.makeText(this, (int)latitude, Toast.LENGTH_SHORT).show();
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
/*
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        myPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();
        String imageName = generateUniqueImageName();
        ParseFile file = new ParseFile(imageName, image);
        file.saveInBackground();

        myParseObject.put("Image", file);  */

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

    public void bt_submitIdea(View view) {

    }

    private String generateUniqueImageName() {
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("MMddyyyyHHmmssSSS");
        String imageName = df.format(d);
        return imageName + ".png";
    }
/*
    private void setUpMap() {
      //  mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
         mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           requestPermissions(new String[]{
                           Manifest.permission.ACCESS_FINE_LOCATION,
                           Manifest.permission.ACCESS_COARSE_LOCATION},
                   );
        }
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions().position(new LatLng(100, 1000)).title("You are here!").snippet("Consider yourself located"));
        LatLng myCoordinates = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(myCoordinates, 12);
        mMap.animateCamera(yourLocation);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myCoordinates)      // Sets the center of the map to LatLng (refer to previous snippet)
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }*/
}
