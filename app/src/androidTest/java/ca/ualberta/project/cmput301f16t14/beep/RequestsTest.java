package ca.ualberta.project.cmput301f16t14.beep;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by linkai on 10/12/16.
 */
public class RequestsTest extends ActivityInstrumentationTestCase2{

    public RequestsTest(){
        super(ca.ualberta.project.cmput301f16t14.beep.MainActivity.class);
    }

    public void testRequest(){
        location1 = new location;
        Request myRequest = new Request(location1);

    }
}
