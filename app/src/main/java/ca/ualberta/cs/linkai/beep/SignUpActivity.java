package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * sign up activity for new user
 * @author Jinzhu, Xizi
 * @see WelcomeActivity
 */

public class SignUpActivity extends Activity {

    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;
    private Button finishButton;
    private String phone;
    private String name;
    private String email;
    private ArrayList<Account> resultAccounts;

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
        userEmail = (EditText) findViewById(R.id.EmailEditText);
        finishButton = (Button) findViewById(R.id.finishSignUpbutton);

        //reach here when user click the finish button
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                name = userName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Name field cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    // check the if the username has been registered already
                    ElasticsearchAccountController.GetAccountTask getAccountTask = new ElasticsearchAccountController.GetAccountTask();
                    getAccountTask.execute(name.toLowerCase());
                    try {
                        resultAccounts = getAccountTask.get();
                    }
                    catch (Exception e) {
                        Log.i("Error", "Failed to get the accounts out of the async object.");
                        Toast.makeText(SignUpActivity.this, "Failed to get the accounts out of the async object.", Toast.LENGTH_SHORT).show();
                    }

                    if(resultAccounts.isEmpty()){
                        // The username is unique, the user can use the username
                        // get email
                        email = userEmail.getText().toString();
                        if (email.isEmpty()){
                            email = "No email info";
                        }

                        //get phone
                        phone = userPhone.getText().toString();
                        if (phone.isEmpty()){
                            phone = "No phone info";
                        }

                        if (email.equals("No email info") && phone.equals("No phone info")){
                            Toast.makeText(SignUpActivity.this, "Contact information fields cannot be both empty",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO elastic search
                            Account myAccount = new Account(name, phone, email, 0);

                            ElasticsearchAccountController.AddAccountTask addAccountTask = new ElasticsearchAccountController.AddAccountTask();
                            addAccountTask.execute(myAccount);

                            finish();
                        }

                    } else {
                        Toast.makeText(SignUpActivity.this, "The username has already been registered by other user",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
