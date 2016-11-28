package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import java.io.FileInputStream;

/**
 * @author Ting
 * @see RiderMainActivity
 */

public class ViewEstimateActivity extends Activity {

    private EditText currentEstimate;
    private EditText userKeyword;
    private Button placeRequestButton;

    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_estimate);
    }

    @Override
    protected void onStart() {
        super.onStart();

        saveInFile();

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

                if (currentEstimate.getText().toString().isEmpty()){
                    Toast.makeText(ViewEstimateActivity.this, "Please enter fare you willing to offer", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        fare = Double.parseDouble(currentEstimate.getText().toString());
                        RiderMainActivity.myRequest.setFare(fare);

                        keyword = userKeyword.getText().toString();
                        if(keyword.isEmpty()) {
                            keyword = "The rider doesn't left any info";
                        }

                        RiderMainActivity.myRequest.setKeyword(keyword);

                        RuntimeRequestList.getInstance().myRequestList.add(RiderMainActivity.myRequest);

                        ElasticsearchRequestController.AddRequestListTask addRequestListTask = new ElasticsearchRequestController.AddRequestListTask();
                        addRequestListTask.execute(RuntimeRequestList.getInstance().myRequestList);

                        // add request to request list
                        //RiderMainActivity.currentAccount.requestsList.add(RiderMainActivity.myRequest);

                        // change the number of requests the current user has
                        //RiderMainActivity.currentAccount.setRequestNum(RiderMainActivity.currentAccount.getRequestNum() + 1);
                        // update to the elastic search server
                        //ElasticsearchAccountController.AddAccountTask addAccountTask = new ElasticsearchAccountController.AddAccountTask();
                        //addAccountTask.execute(RiderMainActivity.currentAccount);
                        Toast.makeText(ViewEstimateActivity.this, "Request has been sent, please wait for drivers to accept.", Toast.LENGTH_SHORT).show();
                        //destroy this page, return to last page
                        finish();


                    } catch (Exception e) {
                        Log.e("Error", "Something was wrong when we placed the request!");
                    }
                }


                clearInFile();
                saveInFile();


            }
        });
    }

    private void saveInFile(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(RuntimeRequestList.getInstance().myRequestList, writer);
            writer.flush();
        } catch (FileNotFoundException e){
            throw new RuntimeException();
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    private void clearInFile(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(RuntimeRequestList.getInstance().myRequestList, writer);
            writer.flush();
        } catch (FileNotFoundException e){
            throw new RuntimeException();
        } catch (IOException e){
            throw new RuntimeException();
        }
    }
}
