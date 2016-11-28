package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * @author Linkai
 * @since 22/11/16
 * @see RequestsListActivity
 *
 * <P>
 *     This activity is showing the detail infomation about the riders requests whose status is CONFIRMED.
 * It includes the start, end location, and other detailed information
 * The rider can make the payment to the driver at this activity
 * </P>
 *
 */
public class RequestDetailActivity_CONFIRMED extends Activity {

    TextView start;
    TextView end;
    TextView driver;
    TextView vehicle;
    TextView date;
    TextView status;
    TextView rate;
    Button cancel;
    Button arriveAndPayButton;
    RatingBar ratingBar;
    public static  int flag;
    Request mRequest;

    List<Address> from;
    List<Address> to;

    // status variable
    private final static int CREATED = 0;
    private final static int OPEN_REQUEST = 1;
    private final static int CONFIRMED = 2;
    private final static int PAID = 3;
    private final static int CANCELLED = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        start = (TextView) findViewById(R.id.StartAddress);
        end = (TextView) findViewById(R.id.DestAddress);
        driver = (TextView) findViewById(R.id.DriverInfo);
        vehicle = (TextView) findViewById(R.id.CarInfo);
        date = (TextView) findViewById(R.id.DateInfo);
        //status = (TextView) findViewById(R.id.StatusInfo);
        rate = (TextView) findViewById(R.id.rate) ;
        cancel = (Button) findViewById(R.id.cancelrequest);
        arriveAndPayButton = (Button) findViewById(R.id.arrive_pay);

        if(bundle != null) {
            flag = bundle.getInt("sendPosition");
            mRequest = RuntimeRequestList.getInstance().myRequestList.get(flag);
        }

        Geocoder geocoder = new Geocoder(RequestDetailActivity_CONFIRMED.this);
        try {
            from = geocoder.getFromLocation(mRequest.getStartLatLng().latitude, mRequest.getStartLatLng().longitude, 1);
            to = geocoder.getFromLocation(mRequest.getEndLatLng().latitude, mRequest.getEndLatLng().longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        start.setText(from.get(0).getLocality());
        end.setText(to.get(0).getLocality());
        date.setText(mRequest.getDate().toString());

        driver.setText(mRequest.getConfirmedDriver().getUsername());
        vehicle.setText(mRequest.getConfirmedDriver().getVehicleInfo());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                Toast.makeText(RequestDetailActivity_CONFIRMED.this, "Request has been canceled", Toast.LENGTH_SHORT).show();

                RuntimeRequestList.getInstance().myRequestList.get(flag).setStatus(CANCELLED);
                ElasticsearchRequestController.AddRequestListTask addRequestListTask = new ElasticsearchRequestController.AddRequestListTask();
                addRequestListTask.execute(RuntimeRequestList.getInstance().myRequestList);
                
                finish();

            }
        });

        arriveAndPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                Intent intent = new Intent(RequestDetailActivity_CONFIRMED.this, MakePaymentActivity.class);
                startActivity(intent);

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
