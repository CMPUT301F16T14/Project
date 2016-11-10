package ca.ualberta.project.cmput301f16t14.beep;

import android.location.Address;
import android.util.Pair;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by administrator on 2016-10-13.
 */
public class Request {

    public Account initiator;
    public Location startLocation;
    public Location endLocation;
    public String Reason;
    public Float Estimate;
    public Payment payment;
    public ArrayList<Account> Acceptances;
    public Account ConfirmedDriver;
    public  Integer Status;

    public Request(Pair<Integer, Integer> startLocation, Pair<Integer, Integer> endLocation, Account rider, String reason) {

    }

    public Request(Location startLocation, Location endLocation, Account rider, String reason) {

    }

    public static void setAcceptances(Account acceptances) {
        Request.acceptances = acceptances;
    }

    public void setAcceptedDriver(Account driver) {
    }

    public void setStatus(String accepted) {
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public String getStatus() {
        return status;
    }

    public static ArrayList<Account> getAcceptance() {
    }

    public Integer getEstimate() {
    }

    public Address getProfile() {
    }

    public ArrayList<Request> getPendingRequest(Account driver) {
    }

    public ForkJoinTask getAcceptedDriver() {
    }

    public void acceptPayment() {
    }

    public long getStartLocation() {
    }

    public long getEndLocation() {
    }
}
