package com.example.android.careandshare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

public class SubmitionActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks{
    private ViewPager viewPager;
    private Button btnTakePhoto;
    private ImageView imgTakenPhoto;
    private static final int CAM_REQUEST = 1313;
    private ParseObject myParseObject;
    private Bitmap myPhoto;
    private MapView mMapView;
    Location myLocation;
    GoogleApiClient mGoogleApiClient;
    TextView longitude;
    TextView latitude;
    private double lon;
    private double lat;
    Button goToDetailsPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submition);
        myParseObject = new ParseObject("CareAndShare_Problem");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new NavigationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        mMapView = (MapView) findViewById(R.id.mapView);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        longitude = (TextView) findViewById(R.id.tv_longitude);
        latitude = (TextView) findViewById(R.id.tv_latitude);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        lat = myLocation.getLatitude();
        lon = myLocation.getLongitude();

        latitude.setText("Latitude: " + String.valueOf(lat));
        longitude.setText("Longitude: " + String.valueOf(lon));

   /*     try {
            addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String zip = addresses.get(0).getPostalCode();
        String country = addresses.get(0).getCountryName();*/
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

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
            goToDetailsPage.setVisibility(View.VISIBLE);
       //     goToDetailsPage.setVisibility(View.VISIBLE);
        }
    }

    public void GoToCameraPage(View view) {
        myParseObject.put("Latitude", String.valueOf(lat));
        myParseObject.put("Longitude", String.valueOf(lon));

        int current = viewPager.getCurrentItem();
        viewPager.setCurrentItem(current + 1);

        btnTakePhoto = (Button) findViewById(R.id.button_camera);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageView_photoTaken);
        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());
        goToDetailsPage = (Button) findViewById(R.id.bt_goToDetailsPage);
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

    public void bt_submitIdea(View view) throws ParseException {

        EditText editTextTitle = (EditText) (findViewById(R.id.et_title));
        EditText editTextDescription = (EditText) (findViewById(R.id.et_description));
        EditText editTextPriority = (EditText) (findViewById(R.id.et_priority));

        String title = String.valueOf(editTextTitle.getText());
        String descr = String.valueOf(editTextDescription.getText());
        String priority = String.valueOf(editTextPriority.getText());

        Toast finalToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        View toastView = finalToast.getView();
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setCompoundDrawablePadding(10);
        toastMessage.setTextSize(25);
        toastMessage.setTextColor(Color.BLACK);
        Spinner spinner = (Spinner) findViewById(R.id.sp_category);
        String category = (String) spinner.getSelectedItem();

        if(title.isEmpty() || descr.isEmpty() || priority.isEmpty()) {
            finalToast.setText("Please fill all fields!");
            toastView.setBackgroundColor(Color.rgb(255, 99, 71));
            toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dangerous_sign, 0, 0, 0);
            finalToast.show();
        }

        else{
            toastView.setBackgroundColor(Color.rgb(255, 228, 181));
            finalToast.setText("Thanks for your idea!  Redirect to HomePage");
            finalToast.setDuration(Toast.LENGTH_LONG);
            toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.submit_final_idea, 0, 0, 0);
            finalToast.show();
            myParseObject.put("Title", title);
            myParseObject.put("Description", descr);
            myParseObject.put("Priority", priority);
            myParseObject.put("Category", category);
            myParseObject.save();

            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
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
    protected void onStart() {
    mGoogleApiClient.connect();
    super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
