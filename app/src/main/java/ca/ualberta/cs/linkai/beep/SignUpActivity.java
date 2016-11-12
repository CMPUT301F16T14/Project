package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;
    private Button finishButton;
    private Integer phone;
    private String name;
    private String email;
    private Integer flag;

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
                flag = 1;
                if (name.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Name field cannot be empty", Toast.LENGTH_SHORT).show();
                    flag = 0;
                }

                try {
                    phone = Integer.parseInt(userPhone.getText().toString());
                } catch(NumberFormatException e) {
                    //todo
                    email = userEmail.getText().toString();
                    if (email.isEmpty()){
                        Toast.makeText(SignUpActivity.this, "Contact information fields cannot be both empty", Toast.LENGTH_SHORT).show();
                        flag = 0;
                    }
                };



                //make a new profile with phone and email
                //Profile newProfile = new Profile(phone, email);

                //destroy this page, return to last page
                if (flag == 1) {
                    Account myAccount = new Account(name, phone, email);

                    // saveInFile(); // TODO replace this with elastic search
                    ElasticsearchAccountController.AddAccountTask addAccountTask = new ElasticsearchAccountController.AddAccountTask();
                    addAccountTask.execute(myAccount);

                    finish();
                }

            }
        });
    }
}
