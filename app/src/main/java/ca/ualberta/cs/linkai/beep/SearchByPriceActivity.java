package ca.ualberta.cs.linkai.beep;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 *@author Jinzhu
 */
public class SearchByPriceActivity extends Activity {

    Button search;
    EditText minimal;
    EditText maximal;
    ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_price);

        search = (Button) findViewById(R.id.search);
        minimal = (EditText) findViewById(R.id.Min);
        maximal = (EditText) findViewById(R.id.Max);
        resultList = (ListView) findViewById(R.id.resultList);
    }

}
