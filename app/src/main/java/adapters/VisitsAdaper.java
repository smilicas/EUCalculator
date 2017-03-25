package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.milica.eucalculator.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import converters.ConvertDate;
import database.Visit;

/**
 * Created by Milica on 14-Feb-17.
 */

public class VisitsAdaper extends ArrayAdapter<Visit> {


    public VisitsAdaper(Context context, List<Visit> visits) {
        super(context, 0, visits);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Visit visit = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
            TextView tvCountry = (TextView) convertView.findViewById(R.id.tv_country);
            TextView tvEntryDate = (TextView) convertView.findViewById(R.id.tv_entryDate);
            TextView tvExitDate = (TextView) convertView.findViewById(R.id.tv_exitDate);
            // Populate the data into the template view using the data object
            tvCountry.setText(visit.getCountry());
            tvEntryDate.setText(
                    ConvertDate.castDate(visit.getEntryDate()));
            tvExitDate.setText(ConvertDate.castDate(visit.getExitDate()));
            // Return the completed view to render on screen
            return convertView;

        }
/*
    private String castDate(long dateLong){
        Date date = new Date(dateLong);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatDate = sdf.format(date);
        return formatDate;
    }
*/
}













