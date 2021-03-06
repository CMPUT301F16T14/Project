/**
 * Copyright 2016 Jinzhu Li, Linkai Qi, Ting Wang, Xizi Liu, Xingqi Guo
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

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
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;

/**
 * <p>
 *     Rider main activity to view map and place requests between two locations
 *      use google map, you can enter the address or click on the map
 * </p>
 *
 *
 * @author Jinzhu, Linkai
 * @see EditProfileActivity
 * @see RequestsListActivity
 *
 */

public class RiderMainActivity extends FragmentActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    // set a requestList which created by the logInAccount
    public static ArrayList<Request> myRequestList = new ArrayList<Request>();

    //public static final int DIVIDE_BY_TWO = 2;
    public static final int BITMAP_SIZE = 100;
    int width = BITMAP_SIZE;
    int height = BITMAP_SIZE;
    private static final String TAG = RiderMainActivity.class.getSimpleName();
    private GoogleMap mMap;
    /**
     * Implement Google API Client
     */
    protected GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double lat = 0;
    double lng = 0;
    LatLng startLatLng;
    LatLng endLatLng;
    private static int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;
    private CharSequence sourceLocation;
    private CharSequence destLocation;
    public static String SourceAddress;
    public static String DestAddress;
    public static Request myRequest;
    List<Address> startAddress = null;
    List<Address> endAddress = null;
    Address start;
    Address end;
    private Marker OriginMarker;
    private Marker StartMarker;
    private Marker EndMarker;
    private Marker Marker;

    List<Address> from;
    List<Address> to;
    String startString;
    String endString;

    private Marker longClickMarker;
    public static Account currentAccount = RuntimeAccount.getInstance().myAccount;

    private PlaceAutocompleteFragment SourceAutocompleteFragment;
    private PlaceAutocompleteFragment DestinationAutocompleteFragment;

    // set popUp windows status constant
    private static final Integer NOSELECTION = 0;
    private static final Integer STARTSELECTION = 1;
    private static final Integer ENDSELECTION = 2;

    public static Integer popSelection = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        setContentView(R.layout.activity_rider_main);

        /**
         * Retrieve the PlaceAutocompleteFragment.
         *
         * Reference from:
         * https://github.com/googlesamples/android-play-places/tree/master/PlaceCompleteActivity
         */
        SourceAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_source);
        DestinationAutocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_destination);
        /**
         * Sets the hint text to display in the search input field when there is no text entered.
         *
         * Reference from:
         * https://developers.google.com/android/reference/com/google/android/gms/location/places/ui/PlaceAutocompleteFragment
         */
        SourceAutocompleteFragment.setHint("From");
        DestinationAutocompleteFragment.setHint("To");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /**
         * set on place selected listen on autocomplete fragment when choose from the list
         *
         * Partial code from:
         * http://www.itgo.me/a/x3124093687586414109/android-using-placeautocompleteactivity-google-places-api
         */

        SourceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            /**
             * Callback invoked when a place has been selected from the "Source" PlaceAutocompleteFragment.
             *
             * @param place
             */
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place Selected: " + place.getName());
                Geocoder geocoder = new Geocoder(RiderMainActivity.this);
                sourceLocation = place.getAddress();

                if(!sourceLocation.toString().isEmpty()) {
                    try {
                        startAddress = geocoder.getFromLocationName(sourceLocation.toString(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (startAddress.size() == 1){
                        start = startAddress.get(0);
                        SourceAddress = start.getLocality();
                        startLatLng = new LatLng(start.getLatitude(), start.getLongitude());
                        if (StartMarker == null) {
                            //StartMarker = mMap.addMarker(new MarkerOptions().position(startLatLng).title("From"));
                            /**
                             * add start marker
                             * code from
                             * http://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
                             */
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.src);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            StartMarker = mMap.addMarker(new MarkerOptions()
                                    .position(startLatLng)
                                    .title("start")
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            );
                            StartMarker.showInfoWindow();
                        } else {
                            StartMarker.setPosition(startLatLng);
                        }
                        // Set Camera position
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(startLatLng));
                    } else {
                        start = new Address(Locale.CANADA);
                        start.setLatitude(53.523219);
                        start.setLongitude(-113.526354);
                        SourceAddress = start.getLocality();
                        startLatLng = new LatLng(start.getLatitude(), start.getLongitude());
                        if (StartMarker == null) {
                            //StartMarker = mMap.addMarker(new MarkerOptions().position(startLatLng).title("From"));
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.src);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            StartMarker = mMap.addMarker(new MarkerOptions()
                                    .position(startLatLng)
                                    .title("start")
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            );
                            StartMarker.showInfoWindow();
                        } else {
                            StartMarker.setPosition(startLatLng);
                        }
                        // Set Camera position
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(startLatLng));
                    }
                }

            }

            /**
             * Callbak invoked when a place has been selected from the PlaceAutocompleteFragment.
             *
             * @param status
             */
            @Override
            public void onError(Status status) {
                Log.e(TAG, "onError: Status = " + status.toString());
            }
        });

        DestinationAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            /**
             * Callback invoked when a place has been selected from the "Destination" PlaceAutocompleteFragment.
             *
             * @param place
             */
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place Selected: " + place.getName());
                Geocoder geocoder = new Geocoder(RiderMainActivity.this);
                destLocation = place.getAddress();

                if(!destLocation.toString().isEmpty()) {
                    try {
                        endAddress = geocoder.getFromLocationName(destLocation.toString(), 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (endAddress.size() == 1){
                        end = endAddress.get(0);
                        DestAddress = end.getLocality();
                        endLatLng = new LatLng(end.getLatitude(), end.getLongitude());
                        if (EndMarker == null) {
                            //EndMarker = mMap.addMarker(new MarkerOptions().position(endLatLng).title("To"));
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.des);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            EndMarker = mMap.addMarker(new MarkerOptions()
                                    .position(endLatLng)
                                    .title("end")
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            );
                            EndMarker.showInfoWindow();
                        } else {
                            EndMarker.setPosition(endLatLng);
                        }

                    } else {
                        end = new Address(Locale.CANADA);
                        end.setLatitude(53.523219);
                        end.setLongitude(-113.526354);
                        DestAddress = end.getLocality();
                        endLatLng = new LatLng(end.getLatitude(), end.getLongitude());
                        if (EndMarker == null) {
                            //EndMarker = mMap.addMarker(new MarkerOptions().position(endLatLng).title("To"));
                            /**
                             * add end marker
                             * code from
                             * http://stackoverflow.com/questions/35718103/how-to-specify-the-size-of-the-icon-on-the-marker-in-google-maps-v2-android
                             */
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.des);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            EndMarker = mMap.addMarker(new MarkerOptions()
                                    .position(endLatLng)
                                    .title("end")
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            );
                            EndMarker.showInfoWindow();
                        } else {
                            EndMarker.setPosition(endLatLng);
                        }
                    }
                }

                //LatLng avgLatLng = new LatLng((start.getLatitude() + end.getLatitude()) / DIVIDE_BY_TWO,
                       // (start.getLongitude() + end.getLongitude()) / DIVIDE_BY_TWO);

                // Set Camera position
                //mMap.animateCamera(CameraUpdateFactory.newLatLng(avgLatLng));

                //reference: http://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers
                //first calculate the bounds of both two markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(StartMarker.getPosition());
                builder.include(EndMarker.getPosition());
                LatLngBounds bounds = builder.build();
                int padding = 200; // offset from edges of the map in pixels
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cameraUpdate);

            }
            /**
             * Callbak invoked when a place has been selected from the PlaceAutocompleteFragment.
             *
             * @param status
             */
            @Override
            public void onError(Status status) {
                Log.e(TAG, "onError: Status = " + status.toString());
            }
        });


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

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) !=
                PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        setMapLongClickListener();
        //setMapClickListener();

        // Set a listener for info window events.
        //mMap.setOnInfoWindowClickListener(this);
    }
/*
    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            SourceAutocompleteFragment.setText(addresses.get(0).getLocality());
        } catch (IOException e) {
            e.printStackTrace();

        }
        destLocation = place.getAddress();

        if(!destLocation.toString().isEmpty()) {
            try {
                endAddress = geocoder.getFromLocationName(destLocation.toString(), 5);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (EndMarker != null) {
                EndMarker.remove();
            }

            if (endAddress.size() == 1){
                end = endAddress.get(0);
                DestAddress = end.getLocality();
                endLatLng = new LatLng(end.getLatitude(), end.getLongitude());
                EndMarker = mMap.addMarker(new MarkerOptions().position(endLatLng).title("To").draggable(true));

            } else {
                end = new Address(Locale.CANADA);
                end.setLatitude(53.523219);
                end.setLongitude(-113.526354);
                DestAddress = end.getLocality();
                endLatLng = new LatLng(end.getLatitude(), end.getLongitude());
                EndMarker = mMap.addMarker(new MarkerOptions().position(endLatLng).title("To").draggable(true));
            }
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
            //OriginMarker = mMap.addMarker(new MarkerOptions().position(loc).title("My Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13));

        }

    }





    /**
     * call when click on the PlaceRequest button
     * @param view
     */
    public void onViewEstimate(View view) {

        if(startAddress == null) {
            if(endAddress == null) {
                Toast.makeText(RiderMainActivity.this, "Please Enter Two Locations", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RiderMainActivity.this, "Empty Source Location!", Toast.LENGTH_SHORT).show();
            }
        } else if(endAddress == null) {
            Toast.makeText(RiderMainActivity.this, "Empty Destination!", Toast.LENGTH_SHORT).show();
        } else {


            Geocoder geocoder = new Geocoder(RiderMainActivity.this);
            try {
                from = geocoder.getFromLocation(startLatLng.latitude, startLatLng.longitude, 1);
                to = geocoder.getFromLocation(endLatLng.latitude, endLatLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                startString = from.get(0).getLocality();
            } catch (RuntimeException e) {
                startString = "Unable to parse the location";
            }

            try {
                endString = to.get(0).getLocality();
            } catch (RuntimeException e) {
                endString = "Unable to parse the location";
            }



            myRequest = new Request(RuntimeAccount.getInstance().myAccount, startLatLng, endLatLng, startString, endString);
            myRequest.EstimateByDistance(startLatLng,endLatLng);

            /**
             * remove the content of autocomplete fragment and maker when change page
             */
            SourceAutocompleteFragment.setText("");
            DestinationAutocompleteFragment.setText("");
            if (StartMarker != null) {
                StartMarker.remove();
                StartMarker = null;
            }

            if (EndMarker != null) {
                EndMarker.remove();
                EndMarker = null;
            }



            Intent intent = new Intent(this, ViewEstimateActivity.class);
            startActivity(intent);

        }

    }

    private void setMapClickListener() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                //StartMarker.remove();
                final LatLng LatLng = new LatLng(latLng.latitude, latLng.longitude);
                Marker = mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(latLng.latitude) +
                        " , " + String.valueOf(latLng.longitude)).draggable(true));
                // Set Camera position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        //Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show();

                        Geocoder geocoder = new Geocoder(RiderMainActivity.this);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(LatLng.latitude, LatLng.longitude, 5);
                            if(SourceAutocompleteFragment.toString().isEmpty()) {
                                SourceAutocompleteFragment.setText(addresses.get(0).getLocality());
                            } else {
                                DestinationAutocompleteFragment.setText(addresses.get(0).getLocality());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                });

            }
        });
    }

    private void setMapLongClickListener(){
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (longClickMarker == null) {
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    String temp1 = numberFormat.format(latLng.latitude);
                    String temp2 = numberFormat.format(latLng.longitude);
                    longClickMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(
                            "Lat:" + temp1 + " , " + "Lng:" + temp2));
                    longClickMarker.setDraggable(true);
                } else {
                    longClickMarker.setPosition(latLng);
                }

                GoogleMap.OnMarkerClickListener onMarkerClickedListener = new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        // show marker title
                        marker.showInfoWindow();

                        if (marker.equals(longClickMarker))
                        {
                            // TODO going to finish this
                            startActivity(new Intent(RiderMainActivity.this, PopUpWindow.class));
                        }
                        return true;
                    }
                };
                mMap.setOnMarkerClickListener(onMarkerClickedListener);
            }
        });


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
            Toast.makeText(RiderMainActivity.this, "Unable to find Requests by elastic search", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        // TODO
        if (popSelection.equals(STARTSELECTION)) {
            startLatLng = longClickMarker.getPosition();

            String startAddressStr = "";

            Geocoder geocoder = new Geocoder(RiderMainActivity.this);
            try {
                startAddress = geocoder.getFromLocation(startLatLng.latitude, startLatLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                for(int i = 0; i < startAddress.get(0).getMaxAddressLineIndex(); i++) {
                    startAddressStr = startAddressStr + startAddress.get(0).getAddressLine(i);
                }
            } catch (RuntimeException e) {
                startAddressStr = "Unable to parse the location";
            }

            Toast.makeText(RiderMainActivity.this, startAddressStr, Toast.LENGTH_SHORT).show();

            SourceAutocompleteFragment.setText(startAddressStr);
            popSelection = NOSELECTION;


        } else if (popSelection.equals(ENDSELECTION)) {
            endLatLng = longClickMarker.getPosition();

            String endAddressStr = "";

            Geocoder geocoder = new Geocoder(RiderMainActivity.this);
            try {
                endAddress = geocoder.getFromLocation(endLatLng.latitude, endLatLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                for(int i = 0; i < endAddress.get(0).getMaxAddressLineIndex(); i++) {
                    endAddressStr = endAddressStr + endAddress.get(0).getAddressLine(i);
                }
            } catch (RuntimeException e) {
                endAddressStr = "Unable to parse the location";
            }

            Toast.makeText(RiderMainActivity.this, endAddressStr, Toast.LENGTH_SHORT).show();

            DestinationAutocompleteFragment.setText(endAddressStr);
            popSelection = NOSELECTION;
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
