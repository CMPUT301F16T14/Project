package ca.ualberta.cs.linkai.beep;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.slf4j.helpers.FormattingTuple;

import java.util.ArrayList;
import java.util.List;

/**
 *@author Jinzhu
 */
public class SearchByPriceActivity extends Activity {

    Button search;
    EditText minimal;
    EditText maximal;
    ListView resultList;
    private RequestsAdapter adapter;
    private Integer type = 10;
    private ArrayList<Request> resultRequests = new ArrayList<Request>();
    private Integer flag = 0;

    public Integer onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.searchPrice:
                if (checked)
                    return type = 0;
            case R.id.searchUnitPrice:
                if (checked)
                    return type = 1;
        }
        return type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_price);

        search = (Button) findViewById(R.id.search);
        minimal = (EditText) findViewById(R.id.Min);
        maximal = (EditText) findViewById(R.id.Max);
        resultList = (ListView) findViewById(R.id.resultList);


        adapter = new RequestsAdapter(this, resultRequests);
        resultList.setAdapter(adapter);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double min = 0.00;
                Double max = 0.00;
                if (minimal.getText().toString().isEmpty() || maximal.getText().toString().isEmpty()){
                    Toast.makeText(SearchByPriceActivity.this,"Min or Max amount cannot be empty",Toast.LENGTH_SHORT).show();
                    flag = 1;
                } else {
                    min = Double.parseDouble(minimal.getText().toString());
                    max = Double.parseDouble(maximal.getText().toString());
                }


                ArrayList<Double> maxMinList = new ArrayList<Double>();
                maxMinList.add(min);
                maxMinList.add(max);


                ElasticsearchRequestController.GetRequestByTotalPrice getRequestByTotalPrice = new ElasticsearchRequestController.GetRequestByTotalPrice();
                getRequestByTotalPrice.execute(maxMinList);

                try {
                    resultRequests = getRequestByTotalPrice.get();
                }
                catch (Exception e) {
                    Log.i("Error", "Failed to get the Accounts out of the async object.");
                    Toast.makeText(SearchByPriceActivity.this, "Unable to find the Account by elastic search", Toast.LENGTH_SHORT).show();
                }

                if(resultRequests.isEmpty() && flag == 0){
                    Toast.makeText(SearchByPriceActivity.this, "No request find", Toast.LENGTH_SHORT).show();
                    adapter.clear();
                    adapter.addAll(resultRequests);
                    adapter.notifyDataSetChanged();
                } else {
                    // = resultRmyAdapter.clear();
                    adapter.clear();
                    adapter.addAll(resultRequests);
                    adapter.notifyDataSetChanged();

                }

                //TODO: get requests whose price between "min" and "max" from server
            }
        });
    }

}
