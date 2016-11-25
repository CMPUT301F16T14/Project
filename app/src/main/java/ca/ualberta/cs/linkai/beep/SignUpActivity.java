package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
    private CheckBox Driver;
    private CheckBox Rider;
    private TextView CarInfo;
    private EditText Description;
    public static int IsDriver = 0;
    public static int IsRider = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Driver = (CheckBox) findViewById(R.id.driver);
        Rider = (CheckBox) findViewById(R.id.rider);
        CarInfo = (TextView) findViewById(R.id.CarDescription);
        Description = (EditText) findViewById(R.id.description);

    }

    // Deal with the driver or rider sign up
    public void onCheckboxClicked(View view) {

        if (Driver.isChecked()) {
            IsDriver = 1;
            CarInfo.setVisibility(View.VISIBLE);
            Description.setVisibility(View.VISIBLE);
        }
        if (!Driver.isChecked()) {
            IsDriver = 0;
            CarInfo.setVisibility(View.INVISIBLE);
            Description.setVisibility(View.INVISIBLE);
        }
        if (Rider.isChecked()) {
            IsRider = 1;
        }
        if (!Rider.isChecked()) {
            IsRider = 0;
        }
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

                            if(Driver.isChecked()) {
                                myAccount.setUserType(1);
                                if(Rider.isChecked()) {
                                    myAccount.setUserType(3);
                                }
                            }
                            else if(Rider.isChecked()) {
                                myAccount.setUserType(2);
                                if(Driver.isChecked()) {
                                    myAccount.setUserType(3);
                                }
                            }



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
