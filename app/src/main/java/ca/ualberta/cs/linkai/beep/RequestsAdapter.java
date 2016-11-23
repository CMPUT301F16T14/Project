package ca.ualberta.cs.linkai.beep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lincolnqi on 2016-11-13.
 */

public class RequestsAdapter extends ArrayAdapter<Request>{
    public RequestsAdapter(Context context, ArrayList<Request> myRequests) {
        super(context, 0, myRequests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Request mRequest = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.requests_list_view, parent, false);
        }
        // Lookup view for data population
        TextView mySrcView = (TextView) convertView.findViewById(R.id.Src);
        TextView myDesView = (TextView) convertView.findViewById(R.id.Des);
        // Populate the data into the template view using the data object

        mySrcView.setText(mRequest.getStartLocation());
        myDesView.setText(mRequest.getEndLocation());
        // Return the completed view to render on screen
        return convertView;
    }
}
