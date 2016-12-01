package ca.ualberta.cs.linkai.beep;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchByLocationActivity extends FragmentActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = SearchByLocationActivity.class.getSimpleName();
    private GoogleMap mMap;
    /**
     * Implement Google API Client
     */
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double lat = 0;
    double lng = 0;
    private static int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;
    private com.google.android.gms.maps.model.Marker OriginMarker;
    private Marker Marker;
    ArrayList<Double> list = new ArrayList<>();
    public static ArrayList<Request> requestList = new ArrayList<>();
    private RequestsAdapter adapter;
    ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_search_by_location);

        resultList = (ListView) findViewById(R.id.resultList);
        adapter = new RequestsAdapter(this, requestList);
        resultList.setAdapter(adapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if(Marker != null) {
                    Marker.remove();
                }
                final LatLng LatLng = new LatLng(latLng.latitude, latLng.longitude);
                DecimalFormat numberFormat = new DecimalFormat("#.00");
                String temp1 = numberFormat.format(LatLng.latitude);
                String temp2 = numberFormat.format(LatLng.longitude);
                Marker = mMap.addMarker(new MarkerOptions().position(latLng).title(
                        "Lat:" + temp1 + " , " + "Lng:" + temp2));
                /*Marker = mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(latLng.latitude) +
                        " , " + String.valueOf(latLng.longitude)).draggable(true));*/

/*
                // Set Camera position
                //reference: http://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers
                //first calculate the bounds of both two markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(Marker.getPosition());
                LatLngBounds bounds = builder.build();
                int padding = 200; // offset from edges of the map in pixels
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cameraUpdate);*/


                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        list.add(LatLng.latitude);
                        list.add(LatLng.longitude);
                        ElasticsearchRequestController.GetRequestByNearbyAddressTask getRequestByLocationTask = new ElasticsearchRequestController.GetRequestByNearbyAddressTask();
                        getRequestByLocationTask.execute(list);
                        try {
                            requestList = getRequestByLocationTask.get();
                        } catch (Exception e) {
                            Log.i("Error", "Failed to get the Accounts out of the async object.");
                        }

                        if (requestList.isEmpty()) {
                            Toast.makeText(SearchByLocationActivity.this, "No request find", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SearchByLocationActivity.this, "Request found", Toast.LENGTH_SHORT).show();
                            adapter.clear();
                            adapter.addAll(requestList);
                            adapter.notifyDataSetChanged();

                        }
                    }
                });

            }
        });

        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(SearchByLocationActivity.this, RequestDetailAndAcceptActivity.class);
                intent.putExtra("request_Detail",i);

                startActivity(intent);

            }
        });

        // Set a listener for info window events.
        //mMap.setOnInfoWindowClickListener(this);
    }
/*
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show();

        Geocoder geocoder = new Geocoder(SearchByLocationActivity.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(LatLng.latitude, LatLng.longitude, 5);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }*/


    /**
     * When the map is showed, as we connect to the google map API server
     * we get our current location
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION  }, MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();

            LatLng loc = new LatLng(53.523219, -113.526354);
            OriginMarker = mMap.addMarker(new MarkerOptions().position(loc).title("My Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13));

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_main, menu);

        return true;
    }

    /**
     * deal with the hamburger button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.view_request) {
            Intent intent = new Intent(this, RequestsListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

        // Search Requests by initiator
        ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                new ElasticsearchRequestController.GetRequestByInitiatorTask();
        getRequestByInitiatorTask.execute(RuntimeAccount.getInstance().myAccount);

        try {
            RuntimeRequestList.getInstance().myRequestList = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to get the Requests out of the async object.");
            Toast.makeText(SearchByLocationActivity.this, "Unable to find Requests by elastic search", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


}
