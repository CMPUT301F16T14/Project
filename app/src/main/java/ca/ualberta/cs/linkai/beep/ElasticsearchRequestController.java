package ca.ualberta.cs.linkai.beep;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * This is the controller for account elastic search
 * Code from lab: https://github.com/SRomansky/AndroidElasticSearch
 * @author Linkai
 */

public class ElasticsearchRequestController {
    private static JestDroidClient client;

    public static class GetRequestByInitiatorTask extends AsyncTask<Account, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(Account... search_parameters) {
            verifySettings();

            ArrayList<Request> myRequests = new ArrayList<Request>();

            String search_string = "{\"from\" : 0, \"size\" : 1000, \"query\" : {\"term\" : {\"username\":\"" +
                    search_parameters[0].getUsername().toLowerCase() + "\" }}}";
            // assume that search_parameters[0] is the only search term we are interested in using
            Search search = new Search.Builder(search_string)
                    .addIndex("f16t14")
                    .addType("Request")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);
                    myRequests.addAll(foundRequests);
                }
                else {
                    Log.i("Error", "The search query failed to find any request that matched.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return myRequests;
        }

        /*
        protected void onPostExecute(ArrayList<Request> myRequests) {
            RequestsListActivity.myAdapter.notifyDataSetChanged();
        }
        */
    }

    // TODO
    public static class GetRequestByGeoTask extends AsyncTask<Account, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(Account... search_parameters) {
            verifySettings();

            ArrayList<Request> myRequests = new ArrayList<Request>();

            String search_string = "{\"from\" : 0, \"size\" : 1000, \"query\" : {\"term\" : {\"keyword\":\"" + search_parameters[0] + "\" }}}";
            // assume that search_parameters[0] is the only search term we are interested in using
            Search search = new Search.Builder(search_string)
                    .addIndex("f16t14")
                    .addType("Request")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);
                    myRequests.addAll(foundRequests);
                }
                else {
                    Log.i("Error", "The search query failed to find any request that matched.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return myRequests;
        }
    }

    /**
     * Search based on a string type keyword
     */
    public static class GetRequestByKeywordTask extends AsyncTask<String, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Request> myRequests = new ArrayList<Request>();

            String search_string = "{\"query\" : {\"term\" : {\"keyword\":\"" + search_parameters[0] + "\" }}}";
            // assume that search_parameters[0] is the only search term we are interested in using
            Search search = new Search.Builder(search_string)
                    .addIndex("f16t14")
                    .addType("Request")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);
                    myRequests.addAll(foundRequests);
                }
                else {
                    Log.i("Error", "The search query failed to find any request that matched.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return myRequests;
        }
    }


    public static class AddRequestTask extends AsyncTask<Request, Void, Void> {

        @Override
        protected Void doInBackground(Request... newRequest) {
            verifySettings();

            Index index = new Index.Builder(newRequest[0]).index("f16t14").type("Request").build();

            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    newRequest[0].setId(result.getId());
                }
                else {
                    Log.i("Error", "Elastic search was not able to add the request.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "We failed to add the request to elastic search!");
                e.printStackTrace();
            }

            return null;
        }
    }


    private static void verifySettings() {
        // if the client hasn't been initialized then we should make it!
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
