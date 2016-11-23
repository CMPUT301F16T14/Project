package ca.ualberta.cs.linkai.beep;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * This is activity deal with request
 * @author Linkai, Jinzhu
 */

public class Request {

    @JestId
    private  String id;
    private Account initiator;
    private LatLng startLocation;
    private LatLng endLocation;
    private String start;
    private String end;
    private String reason;
    private Float estimate;
    private Payment payment;
    private ArrayList<Account> acceptances = new ArrayList<Account>();
    private Account confirmedDriver;
    private Date date;
    private Integer status;

    /**
     * Constructor initialize the request
      * @param initiator
     * @param start
     * @param end
     */
    public Request(Account initiator, LatLng start, LatLng end) {
        this.initiator = initiator;
        this.startLocation = start;
        this.endLocation = end;
        this.date = new Date();
        this.status = 0;
    }

    /**
     * Constructor for request, using autocomplete fragment
     *
     * @param initiator
     * @param start
     * @param end
     */
    public Request(Account initiator, String start, String end) {
        this.initiator = initiator;
        this.start = start;
        this.end = end;
        this.date = new Date();
        this.status = 0;
    }

    public String getStartLocation() {
        return start;
    }

    public String getEndLocation() {
        return end;
    }








    public Date getDate(){
        return date;
    }

    public Float getEstimate() {
        return estimate;
    }

    public void setEstimate(Float estimate) {
        this.estimate = estimate;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
