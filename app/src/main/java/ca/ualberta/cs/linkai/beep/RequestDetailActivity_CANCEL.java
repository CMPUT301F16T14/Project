package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
/**
 * @author Ting
 * @since 22/11/16
 * @see RequestsListActivity
 *
 * <p>
 *     This activity is showing the detail infomation about the riders requests whose status is CANCLE.
 * It includes the start, end location, and other detailed information
 * </p>
 *
 */
public class RequestDetailActivity_CANCEL extends Activity {

    private TextView start;
    private TextView end;
    private TextView date;
    private TextView status;
    private int flag;
    private Request mRequest;
    String startAddressStr;
    String endAddressStr;

    private List<Address> from;
    private List<Address> to;

    // status variable
    private final static int CREATED = 0;
    private final static int OPEN_REQUEST = 1;
    private final static int CONFIRMED = 2;
    private final static int PAID = 3;
    private final static int CANCELLED = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail__cancel);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        start = (TextView) findViewById(R.id.StartAddress);
        end = (TextView) findViewById(R.id.DestAddress);
        date = (TextView) findViewById(R.id.DateInfo);
        status = (TextView) findViewById(R.id.StatusInfo);

        if(bundle != null) {
            flag = bundle.getInt("sendPosition");
            mRequest = RuntimeRequestList.getInstance().myRequestList.get(flag);
        }

        Geocoder geocoder = new Geocoder(RequestDetailActivity_CANCEL.this);
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
        status.setText("Request cancelled");
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
