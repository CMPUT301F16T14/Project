package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by administrator on 2016-10-14.
 */
public class OfflineBehaviorTest extends ActivityInstrumentationTestCase2 {

    // rider1
    Profile testProfile1 = new Profile("testRiderName1", "7807101111", "CMPUT301@ualberta.ca");
    UserAccount rider1 = new UserAccount(testProfile1);

    // rider2
    Profile testProfile2 = new Profile("testRiderName2", "7807102222", "CMPUT301@ualberta.ca");
    UserAccount rider2 = new UserAccount(testProfile2);

    // driver
    Profile testProfile3 = new Profile("testDriverName", "7807103333", "CMPUT301@ualberta.ca");
    UserAccount driver = new UserAccount(testProfile3);

    public OfflineBehaviorTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }

    /**
     * Test for UC-OB01 (US08.01.01)
     */
    public void testViewAcceptanceHistory(){
        Boolean isConnect = Boolean.FALSE;  //offline

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

        // test if the driver can view Acceptance History offline
        assertTrue(AcceptanceList.get(0).equals(testRequest1));

    }


    /**
     * Test for UC-OB02 (US08.02.01)
     */
    public void testViewRequestHistory(){
        Boolean isConnect = Boolean.FALSE;  //offline

        // rider1 creates a request
        Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
        Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
        String reason = new String("This is a reason for testing.");
        Request testRequest1 = new Request(startLocation, endLocation, rider1, reason);

        // create requestList and add the testRequest to the list
        RequestList testRequestList = new RequestList();
        testRequestList.add(testRequest1);

        // test if the system can the request by providing driver's information
        // and shows acceptance's status
        ArrayList<Request> AcceptanceList = testRequestList.getRequest(rider1);

        // test if the rider can view request History offline
        assertTrue(AcceptanceList.get(0).equals(testRequest1));

    }

}
