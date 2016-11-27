package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RequestDetailAndAcceptActivity extends Activity {

    private Bundle bundle;
    private Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail_and_accept);
    }

    @Override
    public void onStart(){
        super.onStart();

        bundle = getIntent().getExtras();

        if (bundle != null){
            int i = bundle.getInt("request_Detail");
            request = SearchByPriceActivity.resultRequests.get(i);
        }



    }

}
