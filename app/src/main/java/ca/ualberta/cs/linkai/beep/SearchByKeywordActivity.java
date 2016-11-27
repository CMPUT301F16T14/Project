package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    public static ArrayList<Request> requestsList = new ArrayList<Request>();
    private RequestsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_keyword);

        keyword = (EditText) findViewById(R.id.keywordSearch);
        search = (Button) findViewById(R.id.search);
        resultList = (ListView) findViewById(R.id.resultList);

        adapter = new RequestsAdapter(this, requestsList);
        adapter.clear();
        resultList.setAdapter(adapter);

        // click on the search button to view browse request list
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                String KeyWord = null;
                //TODO: get satisfied requests from elastic search server
                if(keyword.getText().toString().isEmpty()) {
                    Toast.makeText(SearchByKeywordActivity.this, "Keyword cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    KeyWord = keyword.getText().toString();

                }
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
                    adapter.clear();
                    adapter.addAll(requestsList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SearchByKeywordActivity.this, "keyword found", Toast.LENGTH_SHORT).show();
                }

            }
        });


        // click on each request to view request detail and accept
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchByKeywordActivity.this, RequestDetailAndAcceptActivity.class);
                intent.putExtra("request_Detail",i);
                startActivity(intent);
            }
        });
    }

}
