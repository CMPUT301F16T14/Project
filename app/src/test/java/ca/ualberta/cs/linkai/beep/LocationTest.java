package ca.ualberta.cs.linkai.beep;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by ting8 on 2016/11/14.
 */

public class LocationTest {

    // rider
    Account rider1 = new Account("testRiderName1", "7807101111", "CMPUT301@ualberta.ca");

    /**
     * Test for UC-L01 (US10.02.01)
     */
    @Test
    public void testViewRequestRoute(){
        LatLng startLocation1 = new LatLng(-34, 151);
        LatLng endLocation1 = new LatLng(-41, 139);

        // rider1 creates a request
        Request testRequest1 = new Request(rider1, startLocation1, endLocation1);
        testRequest1.setReason("Reason: for test!");
        assertEquals(testRequest1.getStartLocation(),startLocation1);
        assertEquals(testRequest1.getEndLocation(),endLocation1);


    }
}
