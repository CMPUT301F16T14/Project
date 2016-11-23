package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class RequestDetailActivity extends Activity {

    private TextView start;
    TextView end;
    TextView driver;
    TextView vehicle;
    Button cancel;
    Button confirm;
    int flag;
    String from;
    String to;
    Request mRequest;

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
        cancel = (Button) findViewById(R.id.cancelrequest);
        confirm = (Button) findViewById(R.id.confirm);

        if(bundle != null) {
            flag = bundle.getInt("senPosition");
            //from = RiderMainActivity.start.getLocality();
            //to = RiderMainActivity.DestAddress;
            mRequest = RequestsListActivity.myRequests.get(flag);
        }

        start.setText(mRequest.getStartLocation());
        end.setText(mRequest.getEndLocation());
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
