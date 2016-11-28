package ca.ualberta.cs.linkai.beep;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 2016/11/27.
 * <p>
 *     Include Adapter Pattern
 * </p>
 */

public class AcceptanceAdapter extends ArrayAdapter<Account> {

    // status variable
    private final static int CONFIRMED = 2;

    public AcceptanceAdapter(Context context, ArrayList<Account> acceptances) {
        super(context, 0, acceptances);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String status = "";
        // Get the data item for this position
        Account acceptance = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_all_acceptances, parent, false);
        }
        // Lookup view for data population
        TextView driverName = (TextView) convertView.findViewById(R.id.driver_name);
        TextView vehicleInfo = (TextView) convertView.findViewById(R.id.vehicle_info);

        if (RequestDetailActivity_OPEN.mRequest.getStatus() == CONFIRMED){
            driverName.setBackgroundColor(Color.GREEN);
            vehicleInfo.setBackgroundColor(Color.GREEN);
        }

        driverName.setText(acceptance.getUsername());
        vehicleInfo.setText(acceptance.getVehicleInfo());

        // Return the completed view to render on screen
        return convertView;
    }
}
