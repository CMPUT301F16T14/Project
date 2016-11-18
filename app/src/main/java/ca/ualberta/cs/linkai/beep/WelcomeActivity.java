package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class is the main view class in Beep class
 * It deals with sign in/up activity
 * User can sign in as rider or driver, or sign up
 * Elastic search will detect duplicate username or invalid username
 * @since 1.0
 * @version 14/11/16
 * @see SignUpActivity
 * @see DriverMainActivity
 * @see RiderMainActivity
 */

public class WelcomeActivity extends Activity {

    //private Button riderSignInButton;
    //private Button driverSignInButton;
    private EditText usernameEditText;
    private String username;
    private ArrayList<Account> resultAccounts;
    public static Account logInAccount;
    private static int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //riderSignInButton = (Button) findViewById(R.id.button);
        //driverSignInButton = (Button) findViewById(R.id.button2);
        usernameEditText = (EditText) findViewById(R.id.editText);

        // get location permission
        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }
    }

    /* Call when the user click on the SignUp button */
    public void signUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /* Call when the user click on the SignIn as Driver button */
    public void signInDriver (View view) {
        username = usernameEditText.getText().toString();
        usernameEditText.setText("");

        ElasticsearchAccountController.GetAccountTask getAccountTask = new ElasticsearchAccountController.GetAccountTask();
        getAccountTask.execute(username.toLowerCase());

        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to get the Accounts out of the async object.");
            Toast.makeText(WelcomeActivity.this, "Unable to find the Account by elastic search", Toast.LENGTH_SHORT).show();
        }

        if(resultAccounts.isEmpty()){
            Toast.makeText(WelcomeActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
        } else {
            logInAccount = resultAccounts.get(0);
            Intent intent = new Intent(this, DriverMainActivity.class);
            startActivity(intent);
        }
    }

    /* Call when the user click on the SignIn as Rider button */
    public void signInRider (View view) {
        username = usernameEditText.getText().toString();

        ElasticsearchAccountController.GetAccountTask getAccountTask = new ElasticsearchAccountController.GetAccountTask();
        getAccountTask.execute(username.toLowerCase());

        try {
            resultAccounts = getAccountTask.get();
        }
        catch (Exception e) {
            Log.i("Error", "Failed to get the Accounts out of the async object.");
            Toast.makeText(WelcomeActivity.this, "Unable to find the Account by elastic search", Toast.LENGTH_SHORT).show();
        }

        if(resultAccounts.isEmpty()){
            Toast.makeText(WelcomeActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
        } else {
            logInAccount = resultAccounts.get(0);
            Intent intent = new Intent(this, RiderMainActivity.class);
            startActivity(intent);
        }
    }
}