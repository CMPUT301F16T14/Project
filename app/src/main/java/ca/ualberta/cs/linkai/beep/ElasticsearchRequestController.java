package ca.ualberta.cs.linkai.beep;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
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
 * <p>
 *     This is the controller for account elastic search
 * </p>
 *
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

            int status = 1;
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

    /**
     * Search based on an input address
     */
    public static class GetRequestByAddressTask extends AsyncTask<ArrayList<Double>, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(ArrayList<Double> ... search_parameters) {
            verifySettings();

            ArrayList<Request> myRequests = new ArrayList<Request>();

            Double lat = search_parameters[0].get(0);
            Double lng = search_parameters[0].get(1);

            String search_string = "{ \n" +
                    "  \"query\": {\n" +
                    "      \"match_all\": {}\n" +
                    "   },\n" +
                    "       \"filter\": { \n" +
                    "           \"geo_distance\":{\n" +
                    "               \"distance\":\"1.0km\",\n" +
                    "                   \"location\":["+lng+","+lat+"]\n" +
                    "               }\n" +
                    "           }\n" +
                    "}";
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
     * Search based on an input address with search within 10kn
     */
    public static class GetRequestByNearbyAddressTask extends AsyncTask<ArrayList<Double>, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(ArrayList<Double> ... search_parameters) {
            verifySettings();

            ArrayList<Request> myRequests = new ArrayList<Request>();

            Double lat = search_parameters[0].get(0);
            Double lng = search_parameters[0].get(1);

            String search_string = "{ \n" +
                    "  \"query\": {\n" +
                    "      \"match_all\": {}\n" +
                    "   },\n" +
                    "       \"filter\": { \n" +
                    "           \"geo_distance\":{\n" +
                    "               \"distance\":\"10.0km\",\n" +
                    "                   \"location\":["+lng+","+lat+"]\n" +
                    "               }\n" +
                    "           }\n" +
                    "}";
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

    //here is a function to get the rider requests by different price
    public static class GetRequestByTotalPrice extends AsyncTask<ArrayList<Double>, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(ArrayList<Double>... search_parameters) {
            verifySettings();

            ArrayList<Request> myRequests = new ArrayList<Request>();

            Double min = search_parameters[0].get(0);
            Double max = search_parameters[0].get(1);

            String search_string = "{\n" +
                    "    \"query\": {\n" +
                    "        \"range\" : {\n" +
                    "            \"fare\" : {\n" +
                    "                \"gte\" : " + min + ",\n" +
                    "                \"lte\" : " + max + "\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            // assume that search_parameters[0], search_parameters[1] are the only search terms we are interested in using
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

    //here is a function to get the rider requests by different price
    public static class GetRequestByUnitPrice extends AsyncTask<ArrayList<Double>, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(ArrayList<Double>... search_parameters) {
            verifySettings();

            ArrayList<Request> myRequests = new ArrayList<Request>();

            Double min = search_parameters[0].get(0);
            Double max = search_parameters[0].get(1);

            String search_string = "{\n" +
                    "    \"query\": {\n" +
                    "        \"range\" : {\n" +
                    "            \"unitPrice\" : {\n" +
                    "                \"gte\" : " + min + ",\n" +
                    "                \"lte\" : " + max + "\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            // assume that search_parameters[0], search_parameters[1] are the only search terms we are interested in using
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



    public static class AddRequestListTask extends AsyncTask<ArrayList<Request>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<Request>... newRequests) {
            verifySettings();

            ArrayList<Request> requests = newRequests[0];
            for (Request request : requests){
                Index index = new Index.Builder(request).index("f16t14").type("Request").build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        request.setId(result.getId());
                    }
                    else {
                        Log.i("Error", "Elastic search was not able to add the request.");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "We failed to add the request to elastic search!");
                    e.printStackTrace();
                }
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


    /**
     * Search all requests for monitor acceptions
     * added by Xingqi
     */
    public static class GetAllReqeusts extends AsyncTask<String, Void, ArrayList<Request>> {
        @Override
        protected ArrayList<Request> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Request> GetAllReqeusts = new ArrayList<Request>();

            int status = 1;
            String search_string = "{\"query\" : {*:*}";
            // assume that search_parameters[0] is the only search term we are interested in using
            Search search = new Search.Builder(search_string)
                    .addIndex("f16t14")
                    .addType("Request")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Request> foundRequests = result.getSourceAsObjectList(Request.class);
                    GetAllReqeusts.addAll(foundRequests);
                }
                else {
                    Log.i("Error", "The search query failed to find any request that matched.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return GetAllReqeusts;
        }
    }
}
