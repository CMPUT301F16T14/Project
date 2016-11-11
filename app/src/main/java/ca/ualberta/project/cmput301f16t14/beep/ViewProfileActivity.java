package ca.ualberta.project.cmput301f16t14.beep;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewProfileActivity extends Activity {

    private TextView currentUserName;
    private TextView currentUserPhone;
    private TextView currentUserEmail;
    private Button finishButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_profile, menu);
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