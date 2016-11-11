package ca.ualberta.project.cmput301f16t14.beep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView currentUserName;
    private TextView currentUserPhone;
    private TextView currentUserEmail;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUserName = (TextView) findViewById(R.id.nameTextView);
        currentUserPhone = (TextView) findViewById(R.id.phoneTextView);
        currentUserEmail = (TextView) findViewById(R.id.emailTextView);
        finishButton = (Button) findViewById(R.id.finishViewProfileButton);

        //TODO: change text to current user profile!
        currentUserName.setText("");
        currentUserPhone.setText("");
        currentUserEmail.setText("");

        //reach here when user click the finish button
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                //destroy this page, return to last page
                finish();

            }
        });
    }
}