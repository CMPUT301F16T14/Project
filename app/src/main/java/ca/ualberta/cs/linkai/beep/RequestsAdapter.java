package ca.ualberta.cs.linkai.beep;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Create a new adapter to deal with request list
 *
 * @author Linkai, Jinzhu
 * @see RequestDetailActivity
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
        String status = "";
        // Get the data item for this position
        Request request = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.requests_list_view, parent, false);
        }
        // Lookup view for data population
        TextView myStatusView = (TextView) convertView.findViewById(R.id.current_status);
        TextView myDateView = (TextView) convertView.findViewById(R.id.created_date);
        TextView myTimeView = (TextView) convertView.findViewById(R.id.created_time);

        if (request.getStatus() == CREATED){
            status = "CREATED";
        } else if (request.getStatus() == OPEN_REQUEST) {
            status = "OPEN";
        } else if (request.getStatus() == CONFIRMED) {
            status = "CONFIRMED";
        } else if (request.getStatus() == PAID) {
            status = "PAID";
        } else if (request.getStatus() == CANCELLED) {
            status = "CANCELLED";
            myStatusView.setBackgroundColor(Color.parseColor("#ff7a7a"));
            myDateView.setBackgroundColor(Color.parseColor("#ff7a7a"));
            myTimeView.setBackgroundColor(Color.parseColor("#ff7a7a"));
        }

        // Populate the data into the template view using the data object
        Date date = request.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);

        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        String timeString = timeFormat.format(date);

        myStatusView.setText(status);
        myDateView.setText(dateString);
        myTimeView.setText(timeString);

        // Return the completed view to render on screen
        return convertView;
    }
}
