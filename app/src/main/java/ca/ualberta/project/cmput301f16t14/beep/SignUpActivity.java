package ca.ualberta.project.cmput301f16t14.beep;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        userName = (EditText) findViewById(R.id.NameEditText);
        userPhone = (EditText) findViewById(R.id.PhoneEditText);
        userEmail = (EditText) findViewById(R.id.EmailEditText);
        finishButton = (Button) findViewById(R.id.finishSignUpbutton);

        //reach here when user click the finish button
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String name = userName.getText().toString();
                Integer phone = Integer.parseInt(userPhone.getText().toString());;
                String email = userEmail.getText().toString();

                //make a new profile with phone and email
                Profile newProfile = new Profile(phone, email);

                //destroy this page, return to last page
                finish();

            }
        });
    }
}
