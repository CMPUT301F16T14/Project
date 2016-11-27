package ca.ualberta.cs.linkai.beep;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jinzhu
 */
public class SearchByAddressActivity extends Activity {

    RadioButton byAddress;
    RadioButton nearbyAddress;
    Button search;
    EditText address;
    ListView resultList;
    private RequestsAdapter adapter;
    public static ArrayList<Request> requestList = new ArrayList<>();
    int flag;
    CharSequence Address;
    List<Address> addr;
    Address Addr;
    LatLng latLng;
    ArrayList<Double> list = new ArrayList<>();
    Double start;
    Double end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_address);

        byAddress = (RadioButton) findViewById(R.id.radioButton);
        nearbyAddress = (RadioButton) findViewById(R.id.radioButton2);
        address = (EditText) findViewById(R.id.editText2);
        search = (Button) findViewById(R.id.search);
        resultList = (ListView) findViewById(R.id.resultList);

        adapter = new RequestsAdapter(this, requestList);
        resultList.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(byAddress.isChecked()) {
                    flag = 0;
                } else if(nearbyAddress.isChecked()) {
                    flag = 1;
                }

                if(address.getText().toString().isEmpty()) {
                    Toast.makeText(SearchByAddressActivity.this,"Address cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Address = "Edmonton";
                    Geocoder geocoder = new Geocoder(SearchByAddressActivity.this);
                    try {
                        addr = geocoder.getFromLocationName(Address.toString(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Addr = addr.get(0);
                    //latLng = new LatLng(Addr.getLatitude(), Addr.getLongitude());

                    if(flag == 0) {
                        start = Addr.getLatitude();
                        end = Addr.getLongitude();
                        list.add(start);
                        list.add(end);
                        ElasticsearchRequestController.GetRequestByAddressTask getRequestByAddressTask = new ElasticsearchRequestController.GetRequestByAddressTask();
                        getRequestByAddressTask.execute(list);

                        try {
                            requestList = getRequestByAddressTask.get();
                        } catch (Exception e) {
                            Log.i("Error", "Failed to get the Accounts out of the async object.");
                        }

                        if (requestList.isEmpty()) {
                            Toast.makeText(SearchByAddressActivity.this, "No request find", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter.clear();
                            adapter.addAll(requestList);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
            }
        });

        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(SearchByAddressActivity.this, RequestDetailAndAcceptActivity.class);
                intent.putExtra("request_Detail",i);

                startActivity(intent);

            }
        });
    }
}
