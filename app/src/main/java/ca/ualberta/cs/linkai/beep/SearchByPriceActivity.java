package ca.ualberta.cs.linkai.beep;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *@author Jinzhu
 */
public class SearchByPriceActivity extends Activity {

    Button search;
    EditText minimal;
    EditText maximal;
    ListView resultList;
    private ArrayAdapter<Request> adapter;
    private ArrayList<Request> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_price);

        search = (Button) findViewById(R.id.search);
        minimal = (EditText) findViewById(R.id.Min);
        maximal = (EditText) findViewById(R.id.Max);
        resultList = (ListView) findViewById(R.id.resultList);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double min;
                Double max;
                min = Double.parseDouble(minimal.getText().toString());
                max = Double.parseDouble(maximal.getText().toString());

                //TODO: get requests whose price between "min" and "max" from server
            }
        });
    }

}
