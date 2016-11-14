package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RequestsListActivity extends Activity {

    public static RequestsAdapter myAdapter;
    private ListView myRequestsList;
    private ArrayList<Request> myRequests = new ArrayList<Request>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);

        myRequestsList = (ListView) findViewById(R.id.requestsListView);

        myAdapter = new RequestsAdapter(this, myRequests);
        myRequestsList.setAdapter(myAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();

        myRequests.clear();

        // Search Requests by initiator
        ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                new ElasticsearchRequestController.GetRequestByInitiatorTask();
        getRequestByInitiatorTask.execute(RuntimeAccount.getInstance().myAccount);

        try {
            myRequests = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to get the Requests out of the async object.");
            Toast.makeText(RequestsListActivity.this, "Unable to find Requests by elastic search", Toast.LENGTH_SHORT).show();
        }

        /*
        if(RuntimeAccount.getInstance().myAccount.equals(WelcomeActivity.logInAccount)){
            Toast.makeText(RequestsListActivity.this, "successful!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(RequestsListActivity.this, String.valueOf(myRequests.size()), Toast.LENGTH_SHORT).show();
        */

        myAdapter.clear();
        myAdapter.addAll(myRequests);
        myAdapter.notifyDataSetChanged();
    }
}
