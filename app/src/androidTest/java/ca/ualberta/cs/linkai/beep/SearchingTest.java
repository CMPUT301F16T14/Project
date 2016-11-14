package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by ting8 on 10/14/16.
 */
public class SearchingTest extends ActivityInstrumentationTestCase2 {

    // rider
    Profile testProfile1 = new Profile("testRiderName1", "7807101111", "CMPUT301@ualberta.ca");
    UserAccount rider = new UserAccount(testProfile1);

    public SearchingTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }

    /**
     * Test for UC-SE01 (US04.01.01 & US04.02.01)
     * Test for UC-SE01-A (US04.01.01)
     */
    public void testSearchRequestsByGeo(){
        RequestList searchResult = new RequestList();
        RequestList allRequest = new RequestList();

        Location testStartLocation1 = new Location(100, 200);
        Location testEndLocation1 = new Location(400, 500);
        String reason1 = new String("This is a reason for testing.");
        Request testRequest1 = new Request(testStartLocation1, testEndLocation1, rider, reason1);
        allRequest.add(testRequest1);

        Location testStartLocation2 = new Location(150, 250);
        Location testEndLocation2 = new Location(450, 550);
        String reason2 = new String("This is another reason for testing.");
        Request testRequest2 = new Request(testStartLocation2, testEndLocation2, rider, reason2);
        allRequest.add(testRequest2);

        Location testDriverLocation = new Location(110, 210);
        searchResult = allRequest.searchRequestsByGeo(testDriverLocation);
        assertEquals(searchResult.size(),2);
    }

    /**
     * Test for UC-SE01 (US04.01.01 & US04.02.01)
     * Test for UC-SE01-B (US04.02.01)
     */
    public void testSearchRequestsByKeyword(){
        RequestList searchResult = new RequestList();
        RequestList allRequest = new RequestList();

        Location testStartLocation1 = new Location(100, 200);
        Location testEndLocation1 = new Location(400, 500);
        String reason1 = new String("This is a reason for testing.");
        Request testRequest1 = new Request(testStartLocation1, testEndLocation1, rider, reason1);
        allRequest.add(testRequest1);

        Location testStartLocation2 = new Location(150, 250);
        Location testEndLocation2 = new Location(450, 550);
        String reason2 = new String("This is another reason for testing.");
        Request testRequest2 = new Request(testStartLocation2, testEndLocation2, rider, reason2);
        allRequest.add(testRequest2);

        String testReason = new String("testing");
        searchResult = allRequest.searchRequestsByKeyword(testReason);
        assertEquals(searchResult.size(),2);
    }
}
