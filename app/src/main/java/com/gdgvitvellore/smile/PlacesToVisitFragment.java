package com.gdgvitvellore.smile;

import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdgvitvellore.smile.Helpers.PlacesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by nikhil on 17/4/16.
 */
public class PlacesToVisitFragment extends Fragment {
    Toolbar toolbar;
    String response;
    PlacesAdapter placesAdapter;
    ListView placeList;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String hotelName,body;
    int []p;
    int count=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_visit, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_place);
        placeList = (ListView) view.findViewById(R.id.place_visit_list);
        placesAdapter = new PlacesAdapter(getActivity(), R.layout.layout_place_visit);
        placeList.setAdapter(placesAdapter);
        toolbar.setTitle("Choose places to visit");
        hotelName = getArguments().getString("Hotel Name");
        FontsOverride.overrideFont(getContext(), view, "fonts/Lato-Regular.ttf");
        FontsOverride.overrideFont(getContext(), toolbar, "fonts/Lato-Regular.ttf");

        p= new int[5];
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://207.46.139.218:6969/v1/hotels?i=1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        placesAdapter.notifyDataSetChanged();
                       // System.out.println(response);
                        parseJson(response);
                        placeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
                                TextView t =(TextView)view.findViewById(R.id.place_rating);
                                t.setBackgroundResource(R.color.blue1);
                                t.setTextColor(Color.WHITE);

                                if(count<5){
                                    p[count]=position;
                                    System.out.println(""+ Arrays.toString(p));
                                    count++;
                                }else{
                                    PlacesHelper placesHelper;
                                    JSONObject j = new JSONObject();
                                    try {
                                        j.put("1",hotelName);
                                        placesHelper = (PlacesHelper) placesAdapter.getItem(p[0]);
                                        j.put("2",placesHelper.getPlaceName());
                                        placesHelper = (PlacesHelper) placesAdapter.getItem(p[1]);
                                        j.put("3",placesHelper.getPlaceName());
                                        placesHelper = (PlacesHelper) placesAdapter.getItem(p[2]);
                                        j.put("4",placesHelper.getPlaceName());
                                        placesHelper = (PlacesHelper) placesAdapter.getItem(p[3]);
                                        j.put("5",placesHelper.getPlaceName());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(j.toString());
                                    body = j.toString();

                                    Bundle args = new Bundle();
                                    args.putString("Body",body);
                                    FragmentManager fragmentManager2 = getFragmentManager();
                                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                    RestaurantBreakFast frag = new RestaurantBreakFast();
                                    frag.setArguments(args);
                                    fragmentTransaction2.addToBackStack("Places to Visit");
                                    fragmentTransaction2.hide(PlacesToVisitFragment.this);
                                    fragmentTransaction2.add(android.R.id.content, frag);
                                    fragmentTransaction2.commit();
                                }
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
        placesAdapter.notifyDataSetChanged();
        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("results");
            String place;
            int rate;
            while(count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                place = jo.getString("name");
                rate = jo.getInt("rating");
                PlacesHelper hotelHelper = new PlacesHelper(place,rate);
                placesAdapter.add(hotelHelper);
                placesAdapter.notifyDataSetChanged();
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
