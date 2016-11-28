/**
 * Copyright 2016 Jinzhu Li, Linkai Qi, Ting Wang, Xizi Liu, Xingqi Guo
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>
 *     This is the class to deal with driver main activity
 * </p>
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
        /**
         * add timer for checking notification condition of
         * offer being confirmed
         * per 3 seconds with a delay of 1 second
         */
        Timer time = new Timer();
        time.schedule(new DriverMainActivity.TimeConfirm() ,1000,3000);

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

    /**
     *  Following two classes deal with hamburger buttons
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver_main, menu);

        return true;
    }


    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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

    /**
     * this method is to auto-check for a offer's confirmation per 3 seconds.
     */

    public class TimeConfirm extends TimerTask {
        public ArrayList<Request> realtimeRequests = new ArrayList<>();
        Account current = RuntimeAccount.getInstance().myAccount;

        @Override
        public void run(){
            // get the initiator's real time requests per 3 seconds
            ElasticsearchRequestController.GetAllReqeusts getAllReqeustsTask =
                    new ElasticsearchRequestController.GetAllReqeusts();
            getAllReqeustsTask.execute();
            try {
                realtimeRequests = getAllReqeustsTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get result.");
            }
            //2 iteratively checking driver's offer confirmed, occur notification, if not nothing.
            Iterator it = realtimeRequests.iterator();
            while (it.hasNext()) {
                Request request = (Request) it.next();
                if (request.getConfirmedDriver() == current) {
                    RequestNotification.notify(DriverMainActivity.this.getApplicationContext(), "Your offer is accepted", 2);
                }
            }
        }
    }
}
