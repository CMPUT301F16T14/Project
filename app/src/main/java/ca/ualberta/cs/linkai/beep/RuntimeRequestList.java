package ca.ualberta.cs.linkai.beep;

import java.util.ArrayList;

/**
 * Created by linkai on 2016-11-26.
 */

public class RuntimeRequestList {
    private static RuntimeRequestList instance = null;
    ArrayList<Request> myRequestList;

    private RuntimeRequestList(ArrayList<Request> requestList){
        myRequestList = requestList;
    }

    public static RuntimeRequestList getInstance(){
        if (instance == null){
            instance = new RuntimeRequestList(RiderMainActivity.myRequestList);
        }
        return  instance;
    }
}
