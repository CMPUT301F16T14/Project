package ca.ualberta.project.cmput301f16t14.beep;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewEstimateActivity extends Activity {

    private TextView currentEstimate;
    private EditText userKeyword;
    private Button placeRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_estimate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_estimate, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentEstimate = (TextView) findViewById(R.id.currentEstimate);
        userKeyword = (EditText) findViewById(R.id.userKeyword);
        placeRequestButton = (Button) findViewById(R.id.placeRequestButton);

        //TODO: change text to current estimate!
        currentEstimate.setText("");

        //reach here when user click the placeRequest button
        placeRequestButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                //TODO
                //destroy this page, return to last page
                finish();

            }
        });
    }
}
