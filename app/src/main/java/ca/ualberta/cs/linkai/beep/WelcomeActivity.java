package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    /* Call when the user click on the SignUp button */
    public void signUp(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

    public void signInDriver (View view) {
        Intent intent = new Intent(this, DriverMainActivity.class);
        startActivity(intent);
    }

    public void signInRider (View view) {
        Intent intent = new Intent(this, RiderMainActivity.class);
        startActivity(intent);
    }
}