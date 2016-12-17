package com.gdgvitvellore.smile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdgvitvellore.smile.Helpers.RestaurantHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikhil on 17/4/16.
 */
public class RestaurantBreakFast extends Fragment {
    Toolbar toolbar;
    RestaurantAdapter restaurantAdapter;
    ListView breakfastList;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String body;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_res_breakfast_select, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_rest);
        breakfastList = (ListView) view.findViewById(R.id.breakfast_restaurant_list);
        restaurantAdapter = new RestaurantAdapter(getActivity(), R.layout.layout_restaurant);
        breakfastList.setAdapter(restaurantAdapter);
        body  = getArguments().getString("Body");
        toolbar.setTitle("Choose place for Breakfast");
        RequestQueue queue2 = Volley.newRequestQueue(getContext());
        String url = "http://207.46.139.218:6969/v1/restaurants";
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        System.out.println(response);
                        parseJson(response);
                        breakfastList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Bundle args = new Bundle();
                                RestaurantHelper res = (RestaurantHelper) restaurantAdapter.getItem(position);
                                String hotelName = res.getName();
                                args.putString("Breakfast",hotelName);
                                args.putString("Response",response);
                                args.putString("Body",body);
                                FragmentManager fragmentManager2 = getFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                RestaurantLunch frag = new RestaurantLunch();
                                frag.setArguments(args);
                                fragmentTransaction2.addToBackStack("Restaurant Break fast ");
                                fragmentTransaction2.hide(RestaurantBreakFast.this);
                                fragmentTransaction2.add(android.R.id.content, frag);
                                fragmentTransaction2.commit();
                            }
                        });
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("error",error.toString());
                    }
                }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue2.add(stringRequest2);
        return view;
    }
    public void parseJson(String response){
        int count = 0;
        restaurantAdapter.notifyDataSetChanged();
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("breakfast");
            String place;
            int rate;
            while(count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                place = jo.getString("name");
                rate = jo.getInt("rating");
                RestaurantHelper restaurantHelper = new RestaurantHelper(place,rate);
                restaurantAdapter.add(restaurantHelper);
                restaurantAdapter.notifyDataSetChanged();
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
