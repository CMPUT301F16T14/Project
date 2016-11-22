package ca.ualberta.cs.linkai.beep;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * This is activity deal with request
 * @author Linkai
 */

public class Request {

    @JestId
    private  String id;
    private Account initiator;
    private LatLng startLocation;
    private LatLng endLocation;
    private CharSequence start;
    private CharSequence end;
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

    public Request(Account initiator, CharSequence start, CharSequence end) {
        this.initiator = initiator;
        this.start = start;
        this.end = end;
        this.date = new Date();
        this.status = 0;
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

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getEndLocation() {
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
