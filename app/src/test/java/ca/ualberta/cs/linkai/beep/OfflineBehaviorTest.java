package ca.ualberta.cs.linkai.beep;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by lincolnqi on 2016-11-14.
 */

public class OfflineBehaviorTest {
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
     * Test for UC-OB01 (US08.01.01)
     */
    @Test
    public void testViewAcceptanceHistory(){
        Boolean isConnect = Boolean.TRUE;  //online

        // rider1 creates a request
        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        // add acceptance and set status
        myRequest.addAcceptance(driver);

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(myRequest);

        isConnect = Boolean.TRUE;  //offline

        // TODO: not implement yet
        ArrayList<Request> RequestList = OffLineBuffer.getAcceptedRequest();

        // test if the driver can view Acceptance History offline
        assertTrue(RequestList.get(0).equals(myRequest));

    }


    /**
     * Test for UC-OB02 (US08.02.01)
     */
    @Test
    public void testViewRequestHistory(){
        Boolean isConnect = Boolean.FALSE;  //offline

        // rider1 creates a request
        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        // add the request to the elastic search server
        ElasticsearchRequestController.AddRequestTask addRequestTask =
                new ElasticsearchRequestController.AddRequestTask();
        addRequestTask.execute(myRequest);

        // test if the system can the request by providing driver's information
        // and shows acceptance's status
        ArrayList<Request> RequestList = OffLineBuffer.getMadeRequest();

        // test if the rider can view request History offline
        assertTrue(AcceptanceList.get(0).equals(myRequest));

    }
}
