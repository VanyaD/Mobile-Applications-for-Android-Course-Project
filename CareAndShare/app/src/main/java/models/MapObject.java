package models;

import com.google.android.gms.maps.GoogleMap;

public class MapObject {
    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    private GoogleMap mMap;
}
