package ca.ualberta.cs.linkai.beep;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by lincolnqi on 2016-11-14.
 */

public class StatusTest {
    //rider1
    Account rider1 = new Account("testRiderName1", "7807101111", "CMPUT301@ualberta.ca");
    // driver
    Account driver = new Account("testDriverName", "7807103333", "CMPUT303@ualberta.ca");

    LatLng startLocation1 = new LatLng(-34, 151);
    LatLng endLocation1 = new LatLng(-41, 139);

    /**
     * Test for UC-ST01 (US02.01.01)
     */
    @Test
    public void testDisplayRequestStatus(){
        String reason = new String("This is a reason for testing.");
        Request myRequest = new Request(rider1, startLocation1, endLocation1);

        // test Confirmed&Paid status function
        assertTrue(myRequest.getStatus().equals(0));

        myRequest.setConfirmedDriver(driver);
        myRequest.setStatus(1);
        assertTrue(myRequest.getStatus().equals(1));

        myRequest.setPayment(new Payment());
        myRequest.setStatus(2);
        assertTrue("Status not match", myRequest.getStatus().equals(2));

        // test "Accepted" status function
        myRequest.setStatus(3);
        assertTrue("Status not match", myRequest.getStatus().equals(3));
    }
}
