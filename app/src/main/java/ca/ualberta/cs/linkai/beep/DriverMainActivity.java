package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This is the class to deal with driver main activity
 *
 * @author Jinzhu
 * @see EditProfileActivity
 * @see RequestsListActivity
 */
public class DriverMainActivity extends Activity {

    Button searchByLocation;
    Button searchByPrice;
    Button searchByKeyword;
    Button searchByAddress;

    public static int searchType;
    // define different search type
    public final static int SEARCH_BY_LOCATION = 1;
    public final static int SEARCH_BY_KEYWORD = 2;
    public final static int SEARCH_BY_PRICE = 3;
    public final static int SEARCH_BY_ADDRESS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        searchByLocation = (Button) findViewById(R.id.search1);
        searchByKeyword = (Button) findViewById(R.id.search2);
        searchByPrice = (Button) findViewById(R.id.search3);
        searchByAddress = (Button) findViewById(R.id.search4);

    }

    public void SearchByLocation(View view) {
        searchType = SEARCH_BY_LOCATION;
        Intent intent = new Intent(this, SearchByLocationActivity.class);
        startActivity(intent);
    }
    public void SearchByPrice(View view) {
        searchType = SEARCH_BY_PRICE;
        Intent intent = new Intent(this, SearchByPriceActivity.class);
        startActivity(intent);
    }
    public void SearchByKeyword(View view) {
        searchType = SEARCH_BY_KEYWORD;
        Intent intent = new Intent(this, SearchByKeywordActivity.class);
        startActivity(intent);
    }
    public void SearchByAddress(View view) {
        searchType = SEARCH_BY_ADDRESS;
        Intent intent = new Intent(this, SearchByAddressActivity.class);
        startActivity(intent);
    }

    /* Following two classes deal with hamburger buttons */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.view_request) {
            Intent intent = new Intent(this, RequestsListActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
