package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

import static ca.ualberta.cs.linkai.beep.R.styleable.View;

/**
 * Request list to store and show current user requests
 */

public class RequestsListActivity extends Activity {

    private RequestsAdapter myAdapter;
    private ListView myRequestsList;
    public static ArrayList<Request> myRequests= new ArrayList<Request>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);

        myRequestsList = (ListView) findViewById(R.id.requestsListView);

        myAdapter = new RequestsAdapter(this, myRequests);
        myRequestsList.setAdapter(myAdapter);

        myRequestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RequestsListActivity.this, RequestDetailActivity.class);
                intent.putExtra("sendPosition", position);
                startActivity(intent);
            }
        });
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
