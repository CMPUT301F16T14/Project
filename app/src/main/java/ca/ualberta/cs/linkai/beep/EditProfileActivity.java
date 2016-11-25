package ca.ualberta.cs.linkai.beep;

import android.os.Bundle;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the activity for user to edit profile
 * @author Ting Wang
 * @see DriverMainActivity
 * @see RiderMainActivity
 */
public class EditProfileActivity extends Activity {
    private TextView userName;
    private EditText newPhone;
    private EditText newEmail;
    private Button finishChangeButton;
    private String userPhone;
    private String userEmail;
    Account currentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //currentAccount = WelcomeActivity.logInAccount;
        currentAccount = RuntimeAccount.getInstance().myAccount;

        Toast.makeText(EditProfileActivity.this, currentAccount.getUsername(),
                Toast.LENGTH_SHORT).show();

        userName = (TextView) findViewById(R.id.currentNameTextView);
        newPhone = (EditText) findViewById(R.id.newPhoneEditText);
        newEmail = (EditText) findViewById(R.id.newEmailEditText);
        finishChangeButton = (Button) findViewById(R.id.finishChangeButton);

        //change text to current user profile!
        userName.setText(currentAccount.getUsername());

        if (currentAccount.getEmail().equals("No email info")){
            //if user does not have email information before, leave the editText blank, wait for change
            newEmail.setText("");
        }else{
            newEmail.setText(currentAccount.getEmail());
        }

        if (currentAccount.getPhone().equals("No phone info")){
            //if user does not have phone information before, leave the editText blank, wait for change
            newPhone.setText("");
        }else{
            newPhone.setText(currentAccount.getPhone());
        }

        /**
         * Reach here when user click the finishChange button
         * This is the button function which use to save the modified value into the cloud
         * @author Ting Wang
         * @see DriverMainActivity
         * @see RiderMainActivity
         */
        finishChangeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                // get email
                userEmail = newEmail.getText().toString();
                if (userEmail.isEmpty()) {
                    userEmail = "No email info";
                }

                //get phone
                userPhone = newPhone.getText().toString();
                if (userPhone.isEmpty()) {
                    userPhone = "No phone info";
                }
                //check if newName or newEmail is valid
                if (userEmail.equals("No email info") && userPhone.equals("No phone info")) {
                    Toast.makeText(EditProfileActivity.this, "Contact information fields cannot be both empty",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //Save newPhone and newEmail to elasticSearch
                    //Update to cloud
                    currentAccount.setEmail(userEmail);
                    currentAccount.setPhone(userPhone);
                    ElasticsearchAccountController.AddAccountTask addAccountTask = new ElasticsearchAccountController.AddAccountTask();
                    addAccountTask.execute(currentAccount);
                    //destroy this page, return to last page
                    finish();
                }
            }
        });
    }
}
