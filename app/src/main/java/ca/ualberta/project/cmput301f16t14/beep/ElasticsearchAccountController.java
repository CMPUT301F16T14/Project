package ca.ualberta.project.cmput301f16t14.beep;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by lincolnqi on 2016-11-09.
 */

public class ElasticsearchAccountController {
    private static JestDroidClient client;

    // TODO we need a function that gets account information!
    public static class GetAccountTask extends AsyncTask<String, Void, Account> {
        @Override
        protected Account doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Account> myAccount = new ArrayList<Account>();

            String search_string = "{\"query\" : {\"term\" : {\"username\":\"" + search_parameters[0] + "\" }}}";
            // assume that search_parameters[0] is the only search term we are interested in using
            Search search = new Search.Builder(search_string)
                    .addIndex("f16t14")
                    .addType("Account")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Account> foundAccount = result.getSourceAsObjectList(Account.class);
                    myAccount.addAll(foundAccount);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return myAccount.get(0);
        }
    }


    // TODO we need a function which adds an account!
    public static class AddAccountTask extends AsyncTask<Account, Void, Void> {

        @Override
        protected Void doInBackground(Account... newAccount) {
            verifySettings();

            Index index = new Index.Builder(newAccount[0]).index("f16t14").type("Account").build();

            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    newAccount[0].setId(result.getId());
                }
                else {
                    Log.i("Error", "Elastic search was not able to add the tweet.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "We failed to add a tweet to elastic search!");
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
