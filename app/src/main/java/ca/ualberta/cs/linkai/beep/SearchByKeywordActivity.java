package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * @author Jinzhu
 */
public class SearchByKeywordActivity extends Activity {

    EditText keyword;
    Button search;
    ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_keyword);

        keyword = (EditText) findViewById(R.id.keywordSearch);
        search = (Button) findViewById(R.id.search);
        resultList = (ListView) findViewById(R.id.resultList);
    }
}
