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
import android.widget.Toast;

import org.apache.commons.lang3.ObjectUtils;

public class SignUpActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;
    private Button finishButton;
    private Integer phone;
    private String name;
    private String email;
    private Integer flag;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
                Profile newProfile = new Profile(phone, email);

                //destroy this page, return to last page
                if (flag == 1) {



                    finish();
                }

            }
        });
    }
}
