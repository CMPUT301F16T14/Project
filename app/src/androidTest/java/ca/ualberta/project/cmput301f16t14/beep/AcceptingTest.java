package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Pair;

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
    public void testDisplayRequestStatus(){
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


        // test Confirmed&Paid status function
        testRequest.setStatus("Confirmed&Paid");
        String testStatus = testRequest.getStatus();
        assertTrue("Status not match", testStatus == "Confirmed&Paid");

        // test "Accepted" status function
        testRequest.setStatus("Accepted");
        testStatus = testRequest.getStatus();
        assertTrue("Status not match", testStatus == "Accepted");
    }
}
