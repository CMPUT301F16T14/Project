package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by linkai on 2016-12-01.
 */
public class PopUpWindow extends Activity{

    private Button setStartLocationButton;
    private Button setEndLocationButton;

    // set popUp windows status constant
    private static final Integer NOSELECTION = 0;
    private static final Integer STARTSELECTION = 1;
    private static final Integer ENDSELECTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pop_window);

        setStartLocationButton = (Button) findViewById(R.id.set_start_location_button);
        setEndLocationButton = (Button) findViewById(R.id.set_end_location_button);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.4), (int) (height * 0.21));



        setStartLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RiderMainActivity.popSelection = STARTSELECTION;
                finish();
            }
        });

        setEndLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RiderMainActivity.popSelection = ENDSELECTION;
                finish();
            }
        });

    }
}
