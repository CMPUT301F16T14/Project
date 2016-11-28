package ca.ualberta.cs.linkai.beep;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ting8 on 2016/11/14.
 */

public class SearchingTest {

    // rider1
    Account rider1 = new Account("testRiderName1", "7807101111", "CMPUT301@ualberta.ca", 3);
    LatLng startLocation1 = new LatLng(-100, 200);
    LatLng endLocation1 = new LatLng(-41, 139);

    // rider2
    Account rider2 = new Account("testRiderName2", "7807102222", "CMPUT302@ualberta.ca", 3);
    LatLng startLocation2 = new LatLng(-150, 250);
    LatLng endLocation2 = new LatLng(-21, 94);

    // driver
    Account driver = new Account("testDriverName", "7807103333", "CMPUT303@ualberta.ca", 3);

    /**
     * Test for UC-SE01 (US04.01.01 & US04.02.01)
     * Test for UC-SE01-A (US04.01.01)
     */
    @Test
    public void testSearchRequestsByGeo(){
        ArrayList<Request> searchResult = new ArrayList<Request>();

        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setKeyword("Reason: for test!");

        // rider2 creates a request
        Request testRequest2 = new Request(rider2, startLocation2, endLocation2);

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(testRequest1);
        addRequestTask.execute(testRequest2);

        LatLng testDriverLocation = new LatLng(-125, 225);
        // TODO: not implement yet
        ElasticsearchRequestController.GetRequestByGeoTask getRequestByGeoTask =
                new ElasticsearchRequestController.GetRequestByGeoTask();
        getRequestByGeoTask.execute();

        try {
            searchResult = getRequestByGeoTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }
        assertEquals(searchResult.size(), 2 );
    }

    /**
     * Test for UC-SE01 (US04.01.01 & US04.02.01)
     * Test for UC-SE01-B (US04.02.01)
     */
    @Test
    public void testSearchRequestsByKeyword(){
        ArrayList<Request> searchResult = new ArrayList<Request>();

        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setKeyword("Reason: for test!");

        // rider2 creates a request
        Request testRequest2 = new Request(rider2, startLocation2, endLocation2);
        testRequest2.setKeyword("Reason: for test!");

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(testRequest1);
        addRequestTask.execute(testRequest2);

        String testReason = new String("Reason: for test!");

        ElasticsearchRequestController.GetRequestByKeywordTask getRequestByKeywordTask =
                new ElasticsearchRequestController.GetRequestByKeywordTask();
        getRequestByKeywordTask.execute(testReason);

        try {
            searchResult = getRequestByKeywordTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }
        assertEquals(searchResult.size(), 2 );
    }

    /**
     * Test for UC-SE02 (US04.03.01)
     */
    @Test
    public void testSearchRequestsByPrice(){
        ArrayList<Request> searchResult = new ArrayList<Request>();

        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setFare(34.5);

        // rider2 creates a request
        Request testRequest2 = new Request(rider2, startLocation2, endLocation2);
        testRequest1.setFare(1024.45);

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(testRequest1);
        addRequestTask.execute(testRequest2);


        ArrayList<Double> searchList = new ArrayList<Double>();
        searchList.add(0.0);
        searchList.add(100000.0);
        ElasticsearchRequestController.GetRequestByUnitPrice getRequestByUnitPriceTask =
                new ElasticsearchRequestController.GetRequestByUnitPrice();
        getRequestByUnitPriceTask.execute(searchList);

        try {
            searchResult = getRequestByUnitPriceTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }
        assertEquals(searchResult.size(), 2 );
    }

    /**
     * Test for UC-SE03 (US04.04.01)
     * Test for UC-SE04 (US04.05.01)
     */
    @Test
    public void testSearchRequestsByAddress(){
        ArrayList<Request> searchResult = new ArrayList<Request>();

        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);

        // rider2 creates a request
        Request testRequest2 = new Request(rider2, startLocation2, endLocation2);

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(testRequest1);
        addRequestTask.execute(testRequest2);

        ElasticsearchRequestController.GetRequestByGeoTask getRequestByGeoTask =
                new ElasticsearchRequestController.GetRequestByGeoTask();
        getRequestByGeoTask.execute();

        try {
            searchResult = getRequestByGeoTask.get();
        }
        catch (Exception e) {
            assertTrue("Cannot get requests from the elastic search", false);
        }
        assertEquals(searchResult.size(), 0 );
    }

}
