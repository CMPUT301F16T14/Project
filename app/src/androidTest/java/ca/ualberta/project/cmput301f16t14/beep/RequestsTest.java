package ca.ualberta.project.cmput301f16t14.beep;

import android.accounts.Account;
import android.location.Geocoder;
import android.provider.ContactsContract;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Pair;

/**
 * Created by cmput301f16t14 on 10/12/16.
 */
public class RequestsTest extends ActivityInstrumentationTestCase2{

    Pair<Integer,Integer> startLocation = new Pair<Integer, Integer>(100,100);
    Pair<Integer,Integer> endLocation = new Pair<Integer, Integer>(200,200);
    Profile myProfile = new Profile("testUserName", "7807109999", "CMPUT301@ualberta.ca");
    UserAccount rider = new UserAccount(myProfile);
    String reason = new String("This is a reason for testing.");

    public RequestsTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }


    /**
     * Test for US01.01.01
     */
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
     * Test for US08.03.01
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

    
}
