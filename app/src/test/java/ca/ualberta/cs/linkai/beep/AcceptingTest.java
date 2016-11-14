package ca.ualberta.cs.linkai.beep;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by lincolnqi on 2016-11-14.
 */

public class AcceptingTest {
    // rider1
    Account rider1 = new Account("testRiderName1", "7807101111", "CMPUT301@ualberta.ca");
    LatLng startLocation1 = new LatLng(-34, 151);
    LatLng endLocation1 = new LatLng(-41, 139);

    // rider2
    Account rider2 = new Account("testRiderName2", "7807102222", "CMPUT302@ualberta.ca");
    LatLng startLocation2 = new LatLng(-20, 93);
    LatLng endLocation2 = new LatLng(-21, 94);

    // driver
    Account driver = new Account("testDriverName", "7807103333", "CMPUT303@ualberta.ca");

    /**
     * Test for UC-A01 (US05.01.01 US01.03.01 US08.04.01)
     */
    @Test
    public void testAcceptRequest(){
        ArrayList<Request> myRequestsInitiator = new ArrayList<Request>();
        ArrayList<Request> myRequestsGeo = new ArrayList<Request>();
        ArrayList<Request> myRequestsKeyword = new ArrayList<Request>();

        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setReason("Reason: for test!");

        // rider2 creates a request
        Request testRequest2 = new Request(rider2, startLocation2, endLocation2);

        // add the accounts to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(testRequest1);
        addRequestTask.execute(testRequest2);

        /**
         * test GetRequestByInitiatorTask
         */
        ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                new ElasticsearchRequestController.GetRequestByInitiatorTask();
        getRequestByInitiatorTask.execute(rider1);

        try {
            myRequestsInitiator = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }

        // test the functionality of GetRequestByInitiatorTask
        assertTrue(myRequestsInitiator.size()==1);
        assertTrue(myRequestsInitiator.get(0).equals(testRequest1));

        /**
         * test getRequestByGeoTask
         */
        // TODO: not implement yet
        ElasticsearchRequestController.GetRequestByGeoTask getRequestByGeoTask =
                new ElasticsearchRequestController.GetRequestByGeoTask();
        getRequestByGeoTask.execute(startLocation2);

        try {
            myRequestsGeo = getRequestByGeoTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }

        // test the functionality of getRequestByGeoTask
        assertTrue(myRequestsGeo.size()==1);
        assertTrue(myRequestsGeo.get(0).equals(testRequest2));


        // set the request1 is accepted by the tested driver
        myRequestsInitiator.get(0).addAcceptance(driver);

        // update the status to elastic search server
        addRequestTask.execute(myRequestsInitiator.get(0));

        // retrieve the request from server
        /**
         * test GetRequestByKeywordTask
         */
        // TODO: not implement yet
        ElasticsearchRequestController.GetRequestByKeywordTask getRequestByKeywordTask =
                new ElasticsearchRequestController.GetRequestByKeywordTask();
        getRequestByKeywordTask.execute("test");

        try {
            myRequestsKeyword = getRequestByKeywordTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }

        // test the functionality of getRequestByGeoTask
        assertTrue(myRequestsKeyword.size()==1);

        // test if the rider can accept request
        assertTrue("Wrong confirmed driver OR driver no set",myRequestsKeyword.get(0).
                getAcceptances().get(0).equals(driver));


    }

    /**
     * Test for UC-A02 (US05.01.01)
     */
    @Test
    public void testAcceptPayment(){
        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setReason("Reason: for test!");

        // set the request1 is accepted by the tested driver
        testRequest1.setConfirmedDriver(driver);
        testRequest1.setStatus(1); // status:1 the driver has been confirmed

        // TODO: not implement yet
        // create payment information
        Integer amount = 100;
        String paymentInformation = "this is test payment information";
        Date date = new Date();
        Payment payment = new Payment(amount, date, paymentInformation);

        // set payment information
        testRequest1.setPayment(payment);
        testRequest1.setStatus(2); // status:2 rider has paid

        testRequest1.acceptPayment();
        testRequest1.setStatus(3); // status:3 driver has received the payment

        // test if the driver has accepted the payment
        assertTrue(testRequest1.getStatus().equals("3"));
        assertTrue(testRequest1.getPayment().equals(payment));
    }


    /**
     * Test for UC-A03 (US05.02.01)
     */
    @Test
    public void testViewPendingRequest(){
        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setReason("Reason: for test!");

        // set the request1 is accepted by the tested driver
        testRequest1.setConfirmedDriver(driver);
        testRequest1.setStatus(1); // status:1 the driver has been confirmed

        // get pending request
        ArrayList<Request> PendingRequestList= testRequest1.getPendingRequest(driver);
        Request testRequest = PendingRequestList.get(0);

        // test if we successfully get the pending request
        assertTrue("pending request not match", testRequest.equals(testRequest1));
    }

    /**
     * Test for UC-A04 (US05.03.01)
     */
    @Test
    public void testViewAcceptanceStatus(){
        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setReason("Reason: for test!");

        // add acceptance and set status
        testRequest1.setConfirmedDriver(driver);
        testRequest1.setStatus(1);

        // getAcceptanceStatus
        Integer myStatus = testRequest1.getStatus()
        assertTrue(myStatus.equals(1));
    }
}
