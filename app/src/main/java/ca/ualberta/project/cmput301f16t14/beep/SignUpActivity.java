package ca.ualberta.project.cmput301f16t14.beep;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ting8 on 11/8/16.
 */
public class SignUpActivity extends AppCompatActivity {
    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;
    private Button finishButton;

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
        userEmail = (EditText) findViewById(R.id.E_mailEditText);
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
