package ca.ualberta.cs.linkai.beep;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.phenotype.Flag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.searchbox.annotations.JestId;

/**
 * This is class deal with request.
 * It contains all information the request needs
 *
 * @author Linkai, Jinzhu
 *
 */

public class Request {

    @JestId
    private  String id;
    private Account initiator;
    private LatLng startLocation;
    private LatLng endLocation;
    private String keyword;
    private Double estimate;
    private Double fare;
    private Double unitPrice;
    private Double distance;
    private Payment payment;
    private ArrayList<Account> acceptances = new ArrayList<Account>();
    private Account confirmedDriver;
    private Date date;
    private Integer status;
    private DateFormat dateFormat;
    private String dateString;
    private float mRating;
    private static final String TAG = Request.class.getSimpleName();

    // For elastic search
    private Double[] location = new Double[2];

    // variables used to get string address
    private String startAddress;
    private String endAddress;
    Geocoder geocoder;
    List<Address> start;
    List<Address> end;

    // status variable
    private final static int CREATED = 0;
    private final static int OPEN_REQUEST = 1;
    private final static int CONFIRMED = 2;
    private final static int PAID = 3;
    private final static int CANCELLED = 4;


    /**
     * Constructor initialize the request
      * @param initiator initiator indicate the current user
     * @param start represent the start location of the ride
     * @param end represent the destination of the ride
     */
    public Request(Account initiator, LatLng start, LatLng end) {
        this.initiator = initiator;
        this.startLocation = start;
        this.endLocation = end;
        this.date = new Date();
        this.status = CREATED;

        // For elastic search
        location[0] = start.longitude;
        location[1] = start.latitude;
    }

    public Date getDate(){
        return this.date;
    }

    public Float setRating (Float rating) {
        this.mRating = rating;
        return mRating;
    }


    /**
     * Calculate the estimation by Beep automatically
     * as a reference for the rider
     *
     * @param start start location
     * @param end destination
     */
    public void EstimateByDistance(LatLng start, LatLng end) {
        getDistance(start, end);
        if(this.distance <= 3) {
            this.estimate = 2.25; // Base price is CAD2.25 first 3 km
        } else if(this.distance > 3) {
            this.estimate = 2.25 + (this.distance - 3) * 1.48; // After first 3 km, cost CAD1.48 per km
        } else {
            Log.i(TAG, "Not a valid request");
        }
    }
    public Double getEstimate() {
        return estimate;
    }

    /**
     * reference: https://en.wikipedia.org/wiki/Geographical_distance
     *
     * @param start start location
     * @param end destination
     */
    public void getDistance(LatLng start, LatLng end) {
        int Radius = 6371; // km
        Double startLat = start.latitude;
        Double startLng = start.longitude;
        Double endLat = end.latitude;
        Double endLng = end.longitude;
        Double dLat = Math.toRadians(startLat-endLat);
        Double dLng = Math.toRadians(startLng-endLng);
        Double mLat = (startLat+endLat)/2;

        this.distance = Radius * Math.sqrt(dLat*dLat + (Math.cos(mLat)*dLng*Math.cos(mLat)*dLng));
    }

    /**
     * calculate the price per km
     * set Fate
     * set status to OPEN_REQUEST
     *
     * @param fare the fare is from the rider input which represents the price rider willing to offer
     */
    public void setFare(Double fare) {
        this.fare = fare;
        this.unitPrice = fare / this.distance;
        this.status = OPEN_REQUEST;

    }

    public Double getFare() {
        return fare;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ArrayList<Account> getAcceptances() {
        return acceptances;
    }

    public void addAcceptance(Account acceptance) {
        this.acceptances.add(acceptance);
    }

    public Account getConfirmedDriver() {
        return confirmedDriver;
    }

    public void setConfirmedDriver(Account confirmedDriver) {
        this.confirmedDriver = confirmedDriver;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String reason) {
        this.keyword = reason;
    }

    public Account getInitiator() {
        return initiator;
    }

    public void setInitiator(Account initiator) {
        this.initiator = initiator;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getStartLatLng() {
        return startLocation;
    }

    public LatLng getEndLatLng() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public void acceptPayment(){

    }

    //getter and setter about JestId
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
