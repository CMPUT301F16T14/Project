package ca.ualberta.cs.linkai.beep;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by F16T14 on 2016-11-12.
 */

public class Request {
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    @JestId
    private  String id;

    private Account initiator;
    private LatLng startLocation;
    private LatLng endLocation;
    //private double start;
    //private double end;
    private String reason;
    private Float estimate;
    private Payment payment;
    private ArrayList<Account> acceptances = new ArrayList<Account>();
    private Account confirmedDriver;
    private Integer status;

    public Request(Account initiator, LatLng start, LatLng end) {
        this.initiator = initiator;
        this.startLocation = start;
        this.endLocation = end;
        this.status = 0;
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
}
