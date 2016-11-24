package ca.ualberta.cs.linkai.beep;

import java.util.ArrayList;

/**
 * @author Jinzhu
 * @see Request
 * @see RequestsListActivity
 * @see RequestDetailActivity
 */

public class RequestsList {

    private ArrayList<Request> requests = new ArrayList<Request>();

    public RequestsList() {};

    public ArrayList<Request> getRequest () {
        return requests;
    }

    public void add(Request request) {
        requests.add(request);
    }

    public void delete(Request request) {
        requests.remove(request);
    }
}
