package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Request list to store and show current user requests
 *
 * @see RequestsAdapter
 * @see RequestDetailActivity_CONFIRMED
 *
 */

public class RequestsListActivity extends Activity {

    private RequestsAdapter myAdapter;
    ListView myRequestsList;

    // status variable
    private final static int CREATED = 0;
    private final static int OPEN_REQUEST = 1;
    private final static int CONFIRMED = 2;
    private final static int PAID = 3;
    private final static int CANCELLED = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);
        /**
         * add timer for checking notification condition here
         * per 3 seconds with a delay of 1 second
         */
        Timer timer = new Timer();
        timer.schedule(new NotifyTask() ,1000,3000);

        myRequestsList = (ListView) findViewById(R.id.requestsListView);

        myAdapter = new RequestsAdapter(this, RuntimeRequestList.getInstance().myRequestList);
        myRequestsList.setAdapter(myAdapter);

        myRequestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (RuntimeRequestList.getInstance().myRequestList.get(position).getStatus() == OPEN_REQUEST) {
                    Intent intent = new Intent(RequestsListActivity.this, RequestDetailActivity_OPEN.class);
                    intent.putExtra("sendPosition", position);
                    startActivity(intent);
                }else if (RuntimeRequestList.getInstance().myRequestList.get(position).getStatus() == PAID) {
                    Intent intent = new Intent(RequestsListActivity.this, RequestDetailActivity_PAID.class);
                    intent.putExtra("sendPosition", position);
                    startActivity(intent);
                }else if (RuntimeRequestList.getInstance().myRequestList.get(position).getStatus() == CANCELLED) {
                    Intent intent = new Intent(RequestsListActivity.this, RequestDetailActivity_CANCEL.class);
                    intent.putExtra("sendPosition", position);
                    startActivity(intent);
                }else if (RuntimeRequestList.getInstance().myRequestList.get(position).getStatus() == CONFIRMED){
                    Intent intent = new Intent(RequestsListActivity.this, RequestDetailActivity_CONFIRMED.class);
                    intent.putExtra("sendPosition", position);
                    startActivity(intent);
                } else {
                    Log.i("Error", "Cannot find correspond status activity!");
                }

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        //Toast.makeText(RequestsListActivity.this, String.valueOf(RuntimeRequestList.getInstance().myRequestList.size()), Toast.LENGTH_SHORT).show();

        //myRequests.clear();
        //myAdapter.clear();
        //myAdapter.addAll(RuntimeRequestList.getInstance().myRequestList);
        myAdapter.notifyDataSetChanged();


        // Search Requests by initiator
        /*
        ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                new ElasticsearchRequestController.GetRequestByInitiatorTask();
        getRequestByInitiatorTask.execute(RuntimeAccount.getInstance().myAccount);

        try {
            RuntimeRequestList.getInstance().myRequestList = getRequestByInitiatorTask.get();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to get the Requests out of the async object.");
            Toast.makeText(RequestsListActivity.this, "Unable to find Requests by elastic search", Toast.LENGTH_SHORT).show();
        }
        */

        /*
        if(RuntimeAccount.getInstance().myAccount.equals(WelcomeActivity.logInAccount)){
            Toast.makeText(RequestsListActivity.this, "successful!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(RequestsListActivity.this, String.valueOf(myRequests.size()), Toast.LENGTH_SHORT).show();
        */
    }


    /**
     * This method is to implement a TImerTask that will response whenever a reqeust is accepted
     */

    public class NotifyTask extends TimerTask {
        public ArrayList<Request> realtimeRequests = new ArrayList<>();

        @Override
        public void run(){
            // get the initiator's real time requests per 3 seconds
            ElasticsearchRequestController.GetRequestByInitiatorTask getRequestByInitiatorTask =
                    new ElasticsearchRequestController.GetRequestByInitiatorTask();
            getRequestByInitiatorTask.execute(RuntimeAccount.getInstance().myAccount);
            try {
                realtimeRequests = getRequestByInitiatorTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get result.");
            }
            //2 iteratively checking rider_requests accepted, occur notification, if not nothing.
            Iterator it = realtimeRequests.iterator();
            while (it.hasNext()) {
                Request request = (Request) it.next();
                if (request.getAcceptances().size() >= 0) {
                    RequestNotification.notify(RequestsListActivity.this.getApplicationContext(), "Your Rider Request is accepted", 1);
                }
            }
        }
    }



}
