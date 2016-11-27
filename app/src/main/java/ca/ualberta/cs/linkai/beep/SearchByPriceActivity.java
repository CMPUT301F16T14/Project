package ca.ualberta.cs.linkai.beep;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.slf4j.helpers.FormattingTuple;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.checked;

/**
 *@author Jinzhu
 */
public class SearchByPriceActivity extends Activity {

    Button search;
    EditText minimal;
    EditText maximal;
    ListView resultList;
    RadioGroup radioGroup;
    RadioButton searchToalPrice;
    RadioButton searchUnitPrice;

    private RequestsAdapter adapter;
    private Integer type = 10;
    public static ArrayList<Request> resultRequests = new ArrayList<Request>();
    private Integer flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_price);

        search = (Button) findViewById(R.id.search);
        minimal = (EditText) findViewById(R.id.Min);
        maximal = (EditText) findViewById(R.id.Max);
        resultList = (ListView) findViewById(R.id.resultList);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        searchToalPrice = (RadioButton) findViewById(R.id.searchPrice);
        searchUnitPrice = (RadioButton) findViewById(R.id.searchUnitPrice);


        adapter = new RequestsAdapter(this, resultRequests);
        resultList.setAdapter(adapter);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = 10;
                flag = 0;

                if (searchToalPrice.isChecked()){
                    type = 0;
                }
                if (searchUnitPrice.isChecked()){
                    type = 1;
                }


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

                if (type == 0) {
                    ElasticsearchRequestController.GetRequestByTotalPrice getRequestByTotalPrice = new ElasticsearchRequestController.GetRequestByTotalPrice();
                    getRequestByTotalPrice.execute(maxMinList);

                    try {
                        resultRequests = getRequestByTotalPrice.get();
                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the Accounts out of the async object.");
                        Toast.makeText(SearchByPriceActivity.this, "Unable to find the Account by elastic search", Toast.LENGTH_SHORT).show();
                    }

                    if (resultRequests.isEmpty() && flag == 0) {
                        Toast.makeText(SearchByPriceActivity.this, "No request find", Toast.LENGTH_SHORT).show();
                        adapter.clear();
                        adapter.addAll(resultRequests);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.clear();
                        adapter.addAll(resultRequests);
                        adapter.notifyDataSetChanged();

                    }
                } else if (type == 1) {
                    ElasticsearchRequestController.GetRequestByUnitPrice getRequestByUnitPrice = new ElasticsearchRequestController.GetRequestByUnitPrice();
                    getRequestByUnitPrice.execute(maxMinList);

                    try {
                        resultRequests = getRequestByUnitPrice.get();
                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the Accounts out of the async object.");
                        Toast.makeText(SearchByPriceActivity.this, "Unable to find the Account by elastic search", Toast.LENGTH_SHORT).show();
                    }

                    if (resultRequests.isEmpty() && flag == 0) {
                        Toast.makeText(SearchByPriceActivity.this, "No request find", Toast.LENGTH_SHORT).show();
                        adapter.clear();
                        adapter.addAll(resultRequests);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.clear();
                        adapter.addAll(resultRequests);
                        adapter.notifyDataSetChanged();

                    }

                }
                else {
                    Toast.makeText(SearchByPriceActivity.this, "Please select search bu Unit Price or Total Price", Toast.LENGTH_SHORT).show();
                }

            }
        });

        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchByPriceActivity.this, RequestDetailAndAcceptActivity.class);
                intent.putExtra("request_detail",i);

                startActivity(intent);

            }
        });

    }

}
