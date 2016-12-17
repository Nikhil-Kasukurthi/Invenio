package com.gdgvitvellore.smile;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdgvitvellore.smile.Helpers.HotelHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by nikhil on 16/4/16.
 */
public class HotelSelectorFragment extends Fragment {
    Toolbar toolbar;
    String response;
    HotelAdapter hotelAdapter;
    ListView hotelList;
    JSONObject jsonObject;
    JSONArray jsonArray;
String body;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_select, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_hotel);
        hotelList = (ListView) view.findViewById(R.id.hotel_list);
        hotelAdapter = new HotelAdapter(getActivity(), R.layout.layout_hotel_list);
        FontsOverride.overrideFont(getContext(), view, "fonts/Lato-Regular.ttf");
        hotelList.setAdapter(hotelAdapter);
        toolbar.setTitle("Select Hotel");
        FontsOverride.overrideFont(getContext(), toolbar, "fonts/Lato-Regular.ttf");

        if(Build.VERSION.SDK_INT>=21) {
            setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
            setReenterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.fade));
        }
  //      toolbar.setNavigationIcon(R.mipmap.ic_pin);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://207.46.139.218:6969/v1/hotels";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        parseJson(response);

                        hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Bundle args = new Bundle();
                                HotelHelper hotel = (HotelHelper) hotelAdapter.getItem(position);
                                String hotelName = hotel.getHotelName();
                                args.putString("Hotel Name",hotelName);
                                FragmentManager fragmentManager2 = getFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                PlacesToVisitFragment frag = new PlacesToVisitFragment();
                                frag.setArguments(args);
                                fragmentTransaction2.addToBackStack("Hotel Selector Fragment");
                                fragmentTransaction2.hide(HotelSelectorFragment.this);
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
                });
        queue.add(stringRequest);


        return view;
    }

    public void parseJson(String response){
        int count = 0;
        hotelAdapter.notifyDataSetChanged();
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("results");
            String hotel;
            int rate;
            while(count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                hotel = jo.getString("name");
                rate = jo.getInt("rating");
                HotelHelper hotelHelper = new HotelHelper(hotel,rate);
                hotelAdapter.add(hotelHelper);
                hotelAdapter.notifyDataSetChanged();
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
