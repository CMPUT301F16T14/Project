package ca.ualberta.cs.linkai.beep;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lincolnqi on 2016-11-14.
 */

public class RequestsTest {

    //rider1
    Account rider1 = new Account("testRiderName1", "7807101111", "CMPUT301@ualberta.ca", 3);
    // driver
    Account driver = new Account("testDriverName", "7807103333", "CMPUT303@ualberta.ca", 3);

    LatLng startLocation1 = new LatLng(-34, 151);
    LatLng endLocation1 = new LatLng(-41, 139);

    /**
     * Test for UC-R01 (US01.01.01)
     * Test for UC-R02 (US01.02.01)
     */
    @Test
    public void testRequestRide(){
        ArrayList<Request> myRequestsInitiator = new ArrayList<Request>();

        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        assertTrue("Cannot set start location", myRequest.getStartLatLng().equals(startLocation1));
        assertTrue("Cannot set end location", myRequest.getEndLatLng().equals(endLocation1));
        assertTrue("Cannot set initiator", myRequest.getInitiator().equals(rider1));

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(myRequest);

        // search current request that the user have open
        ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                new ElasticsearchRequestController.GetRequestByInitiatorTask();
        getRequestByInitiatorTask.execute(rider1);

        try {
            myRequestsInitiator = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }

        assertTrue(myRequestsInitiator.size()==1);
        assertTrue(myRequestsInitiator.get(0).equals(myRequest));
    }

    /**
     * Test for UC-R01 (US08.03.01)
     */
    @Test
    public void testRequestRideOffline(){
        Boolean isConnect = Boolean.FALSE;  //offline
        Request myRequest = new Request(rider1, startLocation1, endLocation1);
        ArrayList<Request> myRequestsInitiator = new ArrayList<Request>();

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(myRequest);

        ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                new ElasticsearchRequestController.GetRequestByInitiatorTask();
        getRequestByInitiatorTask.execute(rider1);

        try {
            myRequestsInitiator = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }


        assertTrue(myRequestsInitiator.size() == 0);

        isConnect = Boolean.TRUE;  //online

        // add the request to the elastic search server
        addRequestTask.execute(myRequest);
        getRequestByInitiatorTask.execute(rider1);

        try {
            myRequestsInitiator = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }

        // After the system has network connectivity, check if the system has automatically
        // push the requestList to its server
        assertTrue(myRequestsInitiator.size() == 1);
    }

    /**
     * Test for UC-R01 (US10.01.01)
     */
    @Test
    public void testRequestByLocations(){
        Request myRequest = null;
        try {
            myRequest = new Request(rider1, startLocation1, endLocation1);
        } catch (Exception e) {
            assertTrue("Test request by location unsuccessful", Boolean.FALSE);
        }

        assertTrue("Cannot set start location", myRequest.getStartLatLng().equals(startLocation1));
        assertTrue("Cannot set end location", myRequest.getEndLatLng().equals(endLocation1));
    }

    /**
     * Test for UC-R04 (US01.04.01)
     */
    @Test
    public void testCancelRequest(){
        ArrayList<Request> myRequestsInitiator = new ArrayList<Request>();

        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(myRequest);

        // TODO: not implement yet
        // delete the request from server
        ElasticsearchRequestController.AddRequestTask deleteRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        deleteRequestTask.execute(myRequest);

        // search the request which has just been deleted
        ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                new ElasticsearchRequestController.GetRequestByInitiatorTask();
        getRequestByInitiatorTask.execute(rider1);

        try {
            myRequestsInitiator = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }

        assertTrue("The request still exist in the server, DeleteRequestTask failed",
                myRequestsInitiator.size() == 0);


    }

    /**
     * Test for UC-R05 (US01.05.01)
     */
    @Test
    public void testContactDriver(){
        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        // set the request's status to be "accepted by a driver"
        // then, the rider is able to contact the driver
        myRequest.setConfirmedDriver(driver);
        myRequest.setStatus(1);

        String testEmail = myRequest.getConfirmedDriver().getEmail();
        assertTrue(testEmail == "CMPUT303@ualberta.ca");

        String testPhone = myRequest.getConfirmedDriver().getPhone();
        assertTrue(testPhone == "7807103333");
    }

    /**
     * Test for UC-R06 (US01.06.01)
     */
    @Test
    public void testEstimateFare(){
        Request myRequest = new Request(rider1, startLocation1, endLocation1);
        Double estimateValue = myRequest.getEstimate();

        // test if we get a fare estimate
        assertNotEquals("there should be a estimated value", estimateValue, 0);
    }

    /**
     * Test for UC-R07 (US01.07.01)
     */
    @Test
    public void testConfirmCompletion(){
        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        myRequest.setConfirmedDriver(driver);
        myRequest.setStatus(1);

        // TODO: not implement yet
        // set payment information
        Integer amount = 100;
        String paymentInformation = "this is test payment information";
        Date date = new Date();
        Payment payment = new Payment();

        // test if the request is paid
        myRequest.setPayment(payment);
        Payment myPayment = myRequest.getPayment();
        assertFalse("payment does not set", myPayment == payment);

        // test request's confirm status
        myRequest.setStatus(2);
        Integer testStatus = myRequest.getStatus();
        assertTrue("status should be changed", testStatus == 2);
    }

    /**
     * Test for UC-R08 (US01.08.01)
     */
    @Test
    public void testConfirmDriverAcceptance(){
        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        myRequest.addAcceptance(driver);
        myRequest.setConfirmedDriver(driver);
        myRequest.setStatus(1);

        // test if the system correctly list available acceptance
        ArrayList<Account> acceptanceList = myRequest.getAcceptances();
        Account acceptedDriver = acceptanceList.get(0);
        assertTrue(acceptedDriver.equals(driver));
        assertTrue(myRequest.getConfirmedDriver().equals(driver));
    }
}
