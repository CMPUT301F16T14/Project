package ca.ualberta.project.cmput301f16t14.beep;

import android.location.Address;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by administrator on 2016-10-13.
 */
public class Request {


    public Request(Pair<Integer, Integer> startLocation, Pair<Integer, Integer> endLocation, UserAccount rider, String reason) {

    }

    public Request(Location startLocation, Location endLocation, UserAccount rider, String reason) {

    }

    public static void setAcceptances(UserAccount acceptances) {
        Request.acceptances = acceptances;
    }

    public void setAcceptedDriver(UserAccount driver) {
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

    public static ArrayList<UserAccount> getAcceptance() {
    }

    public Integer getEstimate() {
    }

    public Address getProfile() {
    }

    public ArrayList<Request> getPendingRequest(UserAccount driver) {
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
