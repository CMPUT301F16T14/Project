package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Ting
 * @since 22/11/16
 * @see RequestsListActivity
 * @see RequestDetailActivity_OPEN
 *
 * <p>
 *     This activity is showing the detail infomation about the riders requests whose status is OPEN.
 * It includes the start and end location,
 * the accepances' name and carInfo and other detailed information
 * </p>
 *
 *
 */
public class RequestDetailActivity_OPEN extends Activity {

    private TextView start;
    private TextView end;
    private TextView date;
    private TextView status;
    private Button cancel;
    private int flag;
    String startAddressStr = "";
    String endAddressStr = "";

    public static Request mRequest;
    private ListView allAcceptance;
    private AcceptanceAdapter myAdapter;
    private ArrayList<Account> acceptances;
    public static Account currentAcceptance;

    private List<Address> from;
    private List<Address> to;

    // status variable
    private final static int CANCELLED = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail__open);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        start = (TextView) findViewById(R.id.StartAddress);
        end = (TextView) findViewById(R.id.DestAddress);
        date = (TextView) findViewById(R.id.DateInfo);
        status = (TextView) findViewById(R.id.StatusInfo);
        cancel = (Button) findViewById(R.id.cancelrequest);
        allAcceptance = (ListView) findViewById(R.id.acceptanceList);

        if(bundle != null) {
            flag = bundle.getInt("sendPosition");
            mRequest = RuntimeRequestList.getInstance().myRequestList.get(flag);
            acceptances = mRequest.getAcceptances();
        }

        Geocoder geocoder = new Geocoder(RequestDetailActivity_OPEN.this);
        try {
            from = geocoder.getFromLocation(mRequest.getStartLatLng().latitude, mRequest.getStartLatLng().longitude, 1);
            to = geocoder.getFromLocation(mRequest.getEndLatLng().latitude, mRequest.getEndLatLng().longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for(int i = 0; i < from.get(0).getMaxAddressLineIndex(); i++) {
                startAddressStr = startAddressStr + from.get(0).getAddressLine(i);
            }
        } catch (RuntimeException e) {
            startAddressStr = "Unable to parse the location";
        }
        try {
            for(int i = 0; i < to.get(0).getMaxAddressLineIndex(); i++) {
                endAddressStr = endAddressStr + to.get(0).getAddressLine(i);
            }
        } catch (RuntimeException e) {
            endAddressStr = "Unable to parse the location";
        }

        start.setText(startAddressStr);
        end.setText(endAddressStr);


        date.setText(mRequest.getDate().toString());

        if (ViewAcceptanceProfileActivity.confirmOrNot){
            status.setText("Request accepted");
        }else {
            status.setText("Open Request");
        }

        myAdapter = new AcceptanceAdapter(this, acceptances);
        allAcceptance.setAdapter(myAdapter);

        allAcceptance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentAcceptance = acceptances.get(position);
                Intent intent = new Intent(RequestDetailActivity_OPEN.this, ViewAcceptanceProfileActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Toast.makeText(RequestDetailActivity_OPEN.this, "Request has been canceled", Toast.LENGTH_SHORT).show();

                RuntimeRequestList.getInstance().myRequestList.get(flag).setStatus(CANCELLED);
                ElasticsearchRequestController.AddRequestListTask addRequestListTask = new ElasticsearchRequestController.AddRequestListTask();
                addRequestListTask.execute(RuntimeRequestList.getInstance().myRequestList);

                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
