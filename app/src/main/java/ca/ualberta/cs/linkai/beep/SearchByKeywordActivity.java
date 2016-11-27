package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Jinzhu
 */
public class SearchByKeywordActivity extends Activity {

    EditText keyword;
    Button search;
    ListView resultList;
    private ArrayList<Request> requestsList;
    private ArrayAdapter<Request> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_keyword);

        keyword = (EditText) findViewById(R.id.keywordSearch);
        search = (Button) findViewById(R.id.search);
        resultList = (ListView) findViewById(R.id.resultList);
        resultList.setAdapter(adapter);

        // click on the search button to view browse request list
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                //TODO: get satisfied requests from elastic search server
                String KeyWord = keyword.getText().toString();
                ElasticsearchRequestController.GetRequestByKeywordTask getRequestByKeywordTask = new ElasticsearchRequestController.GetRequestByKeywordTask();
                getRequestByKeywordTask.execute(KeyWord);

                try {
                    requestsList = getRequestByKeywordTask.get();

                } catch (Exception e) {
                    Log.i("Error", "Failed to get result.");
                }

                if(requestsList.isEmpty()) {
                    Toast.makeText(SearchByKeywordActivity.this, "No request has keyword" + " '" + KeyWord + "' ", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SearchByKeywordActivity.this, "keyword found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Request>(this, R.layout.list_item, requestsList);
        //resultList.setAdapter(adapter);
    }
}
