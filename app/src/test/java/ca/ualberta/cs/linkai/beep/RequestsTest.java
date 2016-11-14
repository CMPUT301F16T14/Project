package ca.ualberta.cs.linkai.beep;

import android.util.Pair;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lincolnqi on 2016-11-14.
 */

public class RequestsTest {

    Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
    Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
    String reason = new String("This is a reason for testing.");
    Profile myProfile = new Profile("testUserName", "7807109999", "CMPUT301@ualberta.ca");
    UserAccount rider = new UserAccount(myProfile);

    public RequestsTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }


    /**
     * Test for UC-R01 (US01.01.01)
     * Test for UC-R02 (US01.02.01)
     */
    @Test
    public void testRequestRide(){
        Request myRequest = new Request(startLocation, endLocation, rider, reason);
        RequestList testRequestList= new RequestList();

        // testing the functionality of RequestList, it should be no request in the storage
        assertTrue(testRequestList.getRequest(rider) == null);

        // after adding the rider to the list, we should have the rider's request
        testRequestList.add(myRequest);
        assertEquals(testRequestList.getRequest(rider).size(), 1);
        assertTrue(testRequestList.getRequest(rider).get(0).equals(myRequest));
    }

    /**
     * Test for UC-R01 (US08.03.01)
     */
    public void testRequestRideOffline(){
        Boolean isConnect = Boolean.FALSE;  //offline
        Request myRequest = new Request(startLocation, endLocation, rider, reason);
        RequestList testRequestList= new RequestList();
        testRequestList.add(myRequest);
        testRequestList.pushToServer();

        assertTrue(server.getData().getRequest(rider).size() == 0);
        isConnect = Boolean.TRUE;  //online

        // After the system has network connectivity, check if the system has automatically
        // push the requestList to its server
        assertTrue(server.getData().getRequest(rider).size() == 1);
    }

    /**
     * Test for UC-R01 (US10.01.01)
     */
    public void testRequestByLocations(){
        Location testStartLocation = new Location(100, 200);
        Location testEndLocation = new Location(400, 500);
        try {
            Request testRequest = new Request(testStartLocation, testEndLocation, rider, reason);
        } catch (Exception e) {
            assertTrue("Test request by location unsuccessful", Boolean.FALSE);
        }
    }

    /**
     * Test for UC-R04 (US01.04.01)
     */
    public void testCancelRequest(){
        Request myRequest = new Request(startLocation, endLocation, rider, reason);
        RequestList testRequestList= new RequestList();

        // after adding the rider to the list, we should have the rider's request
        testRequestList.add(myRequest);
        assertEquals(testRequestList.getRequest(rider).size(), 1);
        assertTrue(testRequestList.getRequest(rider).get(0).equals(myRequest));

        // test Cancel/delete request
        testRequestList.delete(myRequest);
        assertEquals(testRequestList.getRequest(rider).size(), 0);
    }

    /**
     * Test for UC-R05 (US01.05.01)
     */

    public void testContactDriver(){
        Request myRequest = new Request(startLocation, endLocation, rider, reason);

        // set the request's status to be "accepted by a driver"
        // then, the rider is able to contact the driver
        myRequest.setStatus("Accepted");

        String testEmail = myRequest.getProfile().getEmail();
        assertTrue(testEmail == "CMPUT301@ualberta.ca");

        String testPhone = myRequest.getProfile().getPhone();
        assertTrue(testPhone == "7807109999");
    }

    /**
     * Test for UC-R06 (US01.06.01)
     */

    public void testEstimateFare(){
        Request myRequest = new Request(startLocation, endLocation, rider, reason);
        Integer estimateValue = myRequest.getEstimate();

        // test if we get a fare estimate
        assertNotSame("there should be a estimated value", estimateValue, 0);
    }

    /**
     * Test for UC-R07 (US01.07.01)
     */
    public void testConfirmCompletion(){
        Request myRequest = new Request(startLocation, endLocation, rider, reason);
        RequestList testRequestList= new RequestList();
        testRequestList.add(myRequest);

        // test if we correctly get the request from database
        Request testRequest = testRequestList.getRequest(rider).get(0);
        assertTrue("The two request should be the same", testRequest.equals(myRequest));
        testRequest.setStatus("Accepted");

        // set payment information
        Integer amount = 100;
        String paymentInformation = "this is test payment information";
        Date date = new Date();
        Payment payment = new Payment(amount, date, paymentInformation);

        // test if the request is paid
        testRequest.setPayment(payment);
        Payment myPayment = testRequest.getPayment();
        assertFalse("payment does not set", myPayment == payment);

        // test request's confirm status
        testRequest.setStatus("Confirmed&Paid");
        String testStatus = testRequest.getStatus();
        assertTrue("status should be changed", testStatus == "Confirmed&Paid");
    }

    /**
     * Test for UC-R08 (US01.08.01)
     */
    public void testConfirmDriverAcceptance(){
        Request myRequest = new Request(startLocation, endLocation, rider, reason);
        RequestList testRequestList= new RequestList();

        // set driver1
        Profile myProfile1 = new Profile("driver1", "7807101111", "CMPUT301@ualberta.ca");
        UserAccount driver1 = new UserAccount(myProfile1);
        // set driver2
        Profile myProfile2 = new Profile("driver2", "7807102222", "CMPUT301@ualberta.ca");
        UserAccount driver2 = new UserAccount(myProfile2);

        Request.setAcceptances(driver1);
        Request.setAcceptances(driver2);

        // test if the system correctly list available acceptance
        ArrayList<UserAccount> acceptanceList = Request.getAcceptance();
        assertTrue(acceptanceList.size() == 2);
    }
}
