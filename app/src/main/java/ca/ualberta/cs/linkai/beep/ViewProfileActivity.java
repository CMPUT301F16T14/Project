package ca.ualberta.cs.linkai.beep;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static ca.ualberta.cs.linkai.beep.R.id.phoneTextView;
import static ca.ualberta.cs.linkai.beep.R.id.textView;

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
        currentUserName.setText(RequestDetailAndAcceptActivity.request.getInitiator().getUsername());
        currentUserPhone.setText(RequestDetailAndAcceptActivity.request.getInitiator().getUsername());
        currentUserEmail.setText(RequestDetailAndAcceptActivity.request.getInitiator().getEmail());


        //http://stackoverflow.com/questions/8599657/dialing-a-phone-call-on-click-of-textview-in-android
        // Make a call when click on phone number
        //String phoneNumber = currentUserPhone.getText().toString();
        currentUserPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = RequestDetailAndAcceptActivity.request.getInitiator().getPhone();
                currentUserPhone.setBackgroundResource(R.color.green);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+" + phoneNumber.trim()));
                if (ActivityCompat.checkSelfPermission(ViewProfileActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }startActivity(callIntent);

            }
        });

        //https://www.tutorialspoint.com/android/android_sending_email.htm
        // send an email when click on phone number
        currentUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Send email", "");
                String[] TO = {RequestDetailAndAcceptActivity.request.getInitiator().getEmail()};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ViewProfileActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }


        });

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
