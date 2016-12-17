package com.gdgvitvellore.smile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gdgvitvellore.smile.Helpers.RestaurantHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 17/4/16.
 */
public class RestaurantAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public void add(RestaurantHelper object) {
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

    public RestaurantAdapter(Context context, int resource) {
        super(context, resource);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RestaurantHolder restaurantHolder = new RestaurantHolder();
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.layout_restaurant, null);
            restaurantHolder.name = (TextView) row.findViewById(R.id.restaurant_name);
            restaurantHolder.rate = (TextView) row.findViewById(R.id.restaurant_rating);
            FontsOverride.overrideFont(getContext(), row, "fonts/Lato-Regular.ttf");
            FontsOverride.overrideFont(getContext(), row.findViewById(R.id.restaurant_name), "fonts/Lato-Bold.ttf");
            row.setTag(restaurantHolder);
        }else{
            restaurantHolder = (RestaurantHolder) row.getTag();
        }
        RestaurantHelper restaurantHelper = (RestaurantHelper) this.getItem(position);
        restaurantHolder.name.setText(restaurantHelper.getName());
        restaurantHolder.rate.setText(restaurantHelper.getRate()+"%");
        return row;
    }
    public class RestaurantHolder{
        TextView name, rate;
    }
}
