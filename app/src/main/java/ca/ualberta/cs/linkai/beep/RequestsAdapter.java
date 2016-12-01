package ca.ualberta.cs.linkai.beep;

import android.content.Context;
import android.graphics.Color;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 *     Create a new adapter to deal with request list
 *     Include Adapter Pattern
 * </p>
 *
 *
 * @author Linkai, Jinzhu
 * @see RequestDetailActivity_CONFIRMED
 * @see RequestsListActivity
 * @see RequestsList
 * @since 13/11/2016
 *
 */

public class RequestsAdapter extends ArrayAdapter<Request>{

    // status variable
    private final static int CREATED = 0;
    private final static int OPEN_REQUEST = 1;
    private final static int CONFIRMED = 2;
    private final static int PAID = 3;
    private final static int CANCELLED = 4;

    public RequestsAdapter(Context context, ArrayList<Request> myRequests) {
        super(context, 0, myRequests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String infoString = "";
        String status = "";
        // Get the data item for this position
        Request request = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.requests_list_view, parent, false);
        }
        // Lookup view for data population
        TextView requestInfoView = (TextView) convertView.findViewById(R.id.request_info);;

        if (request.getStatus() == CREATED){
            infoString = "Status: CREATED \n";
        } else if (request.getStatus() == OPEN_REQUEST) {
            infoString = "Status: OPEN \n";
        } else if (request.getStatus() == CONFIRMED) {
            infoString = "Status: CONFIRMED \n";
        } else if (request.getStatus() == PAID) {
            infoString = "Status: PAID \n";
        } else if (request.getStatus() == CANCELLED) {
            infoString = "Status: CANCELLED \n";
            requestInfoView.setBackgroundColor(Color.parseColor("#ff7a7a"));;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateString = dateFormat.format(request.getDate());
        infoString = infoString + dateString + "\n";

        infoString = infoString + "From: "+ request.getStartLocality() + "   To:"+ request.getEndLocality() + "\n";
        infoString = infoString + "Reason: " + request.getKeyword() + "\n";


        /*
        // Populate the data into the template view using the data object
        Date date = request.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);

        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        String timeString = timeFormat.format(date);
        */

        requestInfoView.setText(infoString);

        // Return the completed view to render on screen
        return convertView;
    }
}
