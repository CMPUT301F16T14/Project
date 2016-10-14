package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by ting8 on 10/14/16.
 */
public class LocationTest extends ActivityInstrumentationTestCase2 {

    // rider
    Profile testProfile1 = new Profile("testRiderName1", "7807101111", "CMPUT301@ualberta.ca");
    UserAccount rider = new UserAccount(testProfile1);

    public LocationTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }

    /**
     * Test for UC-L01 (US10.02.01)
     */
    public void testViewRequestByLocations(){
        Location testStartLocation = new Location(100, 200);
        Location testEndLocation = new Location(400, 500);
        String reason = new String("This is a reason for testing.");
        Request testRequest = new Request(testStartLocation, testEndLocation, rider, reason);
        assertEquals(testRequest.getStartLocation(),testStartLocation);
        assertEquals(testRequest.getEndLocation(),testEndLocation);


    }
}
