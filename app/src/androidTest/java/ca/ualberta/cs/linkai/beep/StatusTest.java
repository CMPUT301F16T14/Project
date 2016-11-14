package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Pair;

/**
 * Created by administrator on 2016-10-14.
 */
public class StatusTest extends ActivityInstrumentationTestCase2 {

    Profile myProfile = new Profile("testUserName", "7807109999", "CMPUT301@ualberta.ca");
    UserAccount rider = new UserAccount(myProfile);

    public StatusTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }


    /**
     * Test for UC-ST01 (US02.01.01)
     */
    public void testDisplayRequestStatus(){
        Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
        Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
        String reason = new String("This is a reason for testing.");
        Request testRequest = new Request(startLocation, endLocation, rider, reason);

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
