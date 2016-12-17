package com.gdgvitvellore.smile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdgvitvellore.smile.Helpers.HotelHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 17/4/16.
 */
public class HotelAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public HotelAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(HotelHelper object) {
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
        HotelHolder hotelHolder = new HotelHolder();
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.layout_hotel_list, null);
            hotelHolder.hotel = (TextView) row.findViewById(R.id.hotel_name);
            hotelHolder.rate = (TextView) row.findViewById(R.id.hotel_rating);
            hotelHolder.l1 = (LinearLayout) row.findViewById(R.id.hotel_layout);
            FontsOverride.overrideFont(getContext(), row, "fonts/Lato-Regular.ttf");
            FontsOverride.overrideFont(getContext(),  row.findViewById(R.id.hotel_name), "fonts/Lato-Bold.ttf");
            row.setTag(hotelHolder);
        }else{
            hotelHolder  = (HotelHolder)row.getTag();
        }
        HotelHelper hotelHelper = (HotelHelper) this.getItem(position);
        hotelHolder.hotel.setText(hotelHelper.getHotelName());
        int n =hotelHelper.getRate();
        if(n == 0){
            hotelHolder.rate.setText("NA");

        }else
            hotelHolder.rate.setText(n+"%");
        return row;
    }



    class HotelHolder{
        TextView hotel, rate;
        LinearLayout l1;
    }
}
