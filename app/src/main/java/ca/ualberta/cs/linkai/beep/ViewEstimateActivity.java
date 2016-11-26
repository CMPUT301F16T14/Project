package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * @see RiderMainActivity
 */

public class ViewEstimateActivity extends Activity {

    private EditText currentEstimate;
    private EditText userKeyword;
    private Button placeRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_estimate);
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentEstimate = (EditText) findViewById(R.id.currentEstimate);
        userKeyword = (EditText) findViewById(R.id.userKeyword);
        placeRequestButton = (Button) findViewById(R.id.placeRequestButton);

        //TODO: change text to current estimate!
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        currentEstimate.setHint("(CAD)" + numberFormat.format(RiderMainActivity.myRequest.getEstimate()));

        //reach here when user click the placeRequest button
        placeRequestButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String keyword;
                Double fare;
                Double distance = RiderMainActivity.myRequest.getDistance(RiderMainActivity.myRequest.getStartLatLng(),
                        RiderMainActivity.myRequest.getEndLatLng());

                try {
                    fare = Double.parseDouble(currentEstimate.getText().toString());
                    RiderMainActivity.myRequest.setFare(fare);
                    RiderMainActivity.myRequest.setUnitPrice(fare, distance);


                    keyword = userKeyword.getText().toString();
                    if(keyword.isEmpty()) {
                        Toast.makeText(ViewEstimateActivity.this, "Please enter keyword of your request", Toast.LENGTH_SHORT).show();
                    } else {
                        RiderMainActivity.myRequest.setKeyword(keyword);
                        RiderMainActivity.myRequest.setKeyword(keyword);

                        ElasticsearchRequestController.AddRequestTask addRequestTask = new ElasticsearchRequestController.AddRequestTask();
                        addRequestTask.execute(RiderMainActivity.myRequest);

                        // add request to request list
                        RiderMainActivity.currentAccount.requestsList.add(RiderMainActivity.myRequest);

                        // change request status to "sent"
                        RuntimeAccount.getInstance().myAccount.setStatus(1);

                        // change the number of requests the current user has
                        //RiderMainActivity.currentAccount.setRequestNum(RiderMainActivity.currentAccount.getRequestNum() + 1);
                        // update to the elastic search server
                        ElasticsearchAccountController.AddAccountTask addAccountTask = new ElasticsearchAccountController.AddAccountTask();
                        addAccountTask.execute(RiderMainActivity.currentAccount);
                        Toast.makeText(ViewEstimateActivity.this, "Request has been sent, please wait for drivers to accept.", Toast.LENGTH_SHORT).show();
                        //destroy this page, return to last page
                        finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(ViewEstimateActivity.this, "Please enter fare you willing to offer", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}
