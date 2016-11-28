package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MakePaymentActivity extends Activity {

    private final static int PAID = 3;
    Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        payButton = (Button) findViewById(R.id.pay_button);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                Toast.makeText(MakePaymentActivity.this, "You have paid the driver", Toast.LENGTH_SHORT).show();
                RuntimeRequestList.getInstance().myRequestList.get(RequestDetailActivity_CONFIRMED.flag).setStatus(PAID);

                finish();

            }
        });
    }


}
