package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by administrator on 2016-10-14.
 */
public class AcceptingTest extends ActivityInstrumentationTestCase2 {
    // rider1
    Profile testProfile1 = new Profile("testRiderName1", "7807101111", "CMPUT301@ualberta.ca");
    UserAccount rider1 = new UserAccount(testProfile1);

    // rider2
    Profile testProfile2 = new Profile("testRiderName2", "7807102222", "CMPUT301@ualberta.ca");
    UserAccount rider2 = new UserAccount(testProfile2);

    // driver
    Profile testProfile3 = new Profile("testDriverName", "7807103333", "CMPUT301@ualberta.ca");
    UserAccount driver = new UserAccount(testProfile3);

    public AcceptingTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }

    /**
     * Test for UC-A01 (US05.01.01 US01.03.01 US08.04.01)
     */
    public void testAcceptRequest(){
        // rider1 creates a request
        Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
        Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
        String reason = new String("This is a reason for testing.");
        Request testRequest1 = new Request(startLocation, endLocation, rider1, reason);

        // rider2 creates a request
        Request testRequest2 = new Request(startLocation, endLocation, rider2, reason);

        // after adding the rider to the list, we should have the rider's requests
        RequestList testRequestList= new RequestList();
        testRequestList.add(testRequest1);
        testRequestList.add(testRequest2);

        // get all available request
        ArrayList<Request> availableRequest = testRequestList.getAvailableRequest();
        assertTrue(availableRequest.size()==2);

        // set the request1 is accepted by the tested driver
        availableRequest.get(0).setAcceptedDriver(driver);
        availableRequest.get(0).setStatus("Accepted");

        // test if the accepted driver's information has been set
        UserAccount testDriver = testRequest1.getAcceptedDriver().get(0);
        assertTrue("not the same driver OR driver does not set", testDriver.equals(driver));
    }

    /**
     * Test for UC-A02 (US05.01.01)
     */
    public void testAcceptPayment(){
        // rider1 creates a request
        Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
        Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
        String reason = new String("This is a reason for testing.");
        Request testRequest1 = new Request(startLocation, endLocation, rider1, reason);

        // set the request1 is accepted by the tested driver
        testRequest1.setAcceptedDriver(driver);
        testRequest1.setStatus("Accepted");

        // create payment information
        Integer amount = 100;
        String paymentInformation = "this is test payment information";
        Date date = new Date();
        Payment payment = new Payment(amount, date, paymentInformation);

        // set payment information
        testRequest1.setPayment(payment);
        testRequest1.setStatus("Confirmed&Paid");

        testRequest1.acceptPayment();
        testRequest1.setStatus("PaymentAccepted");

        // test if the driver has accepted the payment
        assertTrue(testRequest1.getStatus().equals("PaymentAccepted"));
        assertTrue(testRequest1.getPayment().equals(payment));
    }


    /**
     * Test for UC-A03 (US05.02.01)
     */
    public void testViewPendingRequest(){
        // rider1 creates a request
        Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
        Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
        String reason = new String("This is a reason for testing.");
        Request testRequest1 = new Request(startLocation, endLocation, rider1, reason);

        // set the request1 is accepted by the tested driver
        testRequest1.setAcceptedDriver(driver);
        testRequest1.setStatus("Accepted");

        // get pending request
        ArrayList<Request> PendingRequestList= testRequest1.getPendingRequest(driver);
        Request testRequest = PendingRequestList.get(0);

        // test if we successfully get the pending request
        assertTrue("pending request not match", testRequest.equals(testRequest1));
    }

    /**
     * Test for UC-A04 (US05.03.01)
     */
    public void testViewAcceptanceStatus(){
        // rider1 creates a request
        Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
        Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
        String reason = new String("This is a reason for testing.");
        Request testRequest1 = new Request(startLocation, endLocation, rider1, reason);

        // add acceptance and set status
        testRequest1.setAcceptedDriver(driver);
        testRequest1.setStatus("Accepted");

        // create requestList and add the testRequest to the list
        RequestList testRequestList = new RequestList();
        testRequestList.add(testRequest1);

        // test if the system can the request by providing driver's information
        // and shows acceptance's status
        ArrayList<Request> AcceptanceList = testRequestList.getByDriver(driver);
        Request acceptance = AcceptanceList.get(0);
        assertTrue(acceptance.equals(testRequest1));
        assertTrue(acceptance.getStatus().equals("Accepted"));
    }


}
