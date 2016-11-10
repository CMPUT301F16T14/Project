package ca.ualberta.project.cmput301f16t14.beep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by ting8 on 11/8/16.
 */
public class SignUpActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userName = (EditText) findViewById(R.id.NameEditText);
        userPhone = (EditText) findViewById(R.id.PhoneEditText);
}
