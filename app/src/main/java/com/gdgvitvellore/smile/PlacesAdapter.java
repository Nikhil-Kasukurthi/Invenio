package com.gdgvitvellore.smile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gdgvitvellore.smile.Helpers.PlacesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 17/4/16.
 */
public class PlacesAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public PlacesAdapter(Context context, int resource) {
        super(context, resource);
    }
    public void add(PlacesHelper object) {
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlaceHolder placeVisitHolder = new PlaceHolder();
        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.layout_place_visit, null);
            placeVisitHolder.rate = (TextView) row.findViewById(R.id.place_rating);
            placeVisitHolder.place = (TextView) row.findViewById(R.id.place_name);
            FontsOverride.overrideFont(getContext(), row, "fonts/Lato-Regular.ttf");
            FontsOverride.overrideFont(getContext(), row.findViewById(R.id.place_name), "fonts/Lato-Bold.ttf");
            row.setTag(placeVisitHolder);
        }else{
            placeVisitHolder  = (PlaceHolder)row.getTag();
        }
        PlacesHelper placesHelper = (PlacesHelper) this.getItem(position);
        placeVisitHolder.place.setText(placesHelper.getPlaceName());
        int n =placesHelper.getRate();
        if(n ==0){
            placeVisitHolder.rate.setText("NA");
        }else
            placeVisitHolder.rate.setText(n+"%");
        return row;
    }



}