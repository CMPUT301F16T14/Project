package ca.ualberta.cs.linkai.beep;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class RiderMainActivity extends FragmentActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double lat =0, lng=0;
    private static int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;
    private EditText sourceInput;
    private EditText destinationInput;
    private Button searchButton;
    private Button PlaceRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_rider_main);
        //sourceInput = (EditText) findViewById(R.id.source);
        //destinationInput = (EditText) findViewById(R.id.destination);


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
 /*   @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }
        // Add a marker in Sydney and move the camera
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //buildGoogleApiClient();

        //LatLng loc = new LatLng(lat, lng);
        //mMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }


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

            LatLng loc = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(loc).title("My Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            //mMap.getUiSettings().setZoomControlsEnabled(true);
            //mMap.getUiSettings().setCompassEnabled(true);
        }
    }

    public void onPlaceRequest(View view) {
        Request myRequest;
        List<Address> startAddress = null;
        List<Address> endAddress = null;
        int width = 100;
        int height = 100;
        sourceInput = (EditText) findViewById(R.id.source);
        destinationInput = (EditText) findViewById(R.id.destination);
        String sourceLocation = sourceInput.getText().toString();
        String destinationLocation = destinationInput.getText().toString();
        if(!sourceLocation.isEmpty() && !destinationLocation.isEmpty()) {
            Geocoder geocoder = new Geocoder(this);
            try {
                startAddress = geocoder.getFromLocationName(sourceLocation,1);
                endAddress = geocoder.getFromLocationName(destinationLocation,1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address start = startAddress.get(0);
            Address end = endAddress.get(0);
            LatLng startLatLng = new LatLng(start.getLatitude(),start.getLongitude());
            LatLng endLatLng = new LatLng(end.getLatitude(),end.getLongitude());
            LatLng avgLatLng = new LatLng((start.getLatitude() + end.getLatitude())/2,
                    (start.getLongitude() + end.getLongitude())/2);
            //MarkerOptions marker = new MarkerOptions().position(startLatLng).title("start");
            //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.src));
            //mMap.addMarker(marker);
            //mMap.addMarker(new MarkerOptions().position(startLatLng).title("start"));

            //http://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
            // add start marker
            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.src);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            mMap.addMarker(new MarkerOptions()
                    .position(startLatLng)
                    .title("start")
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            );
            //mMap.animateCamera(CameraUpdateFactory.newLatLng(startLatLng));

            //mMap.addMarker(new MarkerOptions().position(endLatLng).title("end"));
            // add end marker
            BitmapDrawable bitmapdraw1 = (BitmapDrawable)getResources().getDrawable(R.drawable.des);
            Bitmap b1 = bitmapdraw1.getBitmap();
            Bitmap smallMarker1 = Bitmap.createScaledBitmap(b1, width, height, false);
            mMap.addMarker(new MarkerOptions()
                    .position(endLatLng)
                    .title("end")
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker1))
            );

            // Set Camera position
            mMap.animateCamera(CameraUpdateFactory.newLatLng(avgLatLng));

            myRequest = new Request(RuntimeAccount.getInstance().myAccount, startLatLng, endLatLng);
            ElasticsearchRequestController.AddRequestTask addRequestTask = new ElasticsearchRequestController.AddRequestTask();
            addRequestTask.execute(myRequest);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_main, menu);

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

    }
}