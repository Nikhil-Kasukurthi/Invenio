package com.gdgvitvellore.smile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.gdgvitvellore.smile.Helpers.NearbyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by nikhil on 9/4/16.
 */
public class TravelFragment extends Fragment {
    EditText location;
    Button search;
    ListView list;
    NearbyAdapter nearbyAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        location = (EditText)view.findViewById(R.id.location);
        search = (Button)view.findViewById(R.id.search);
        list = (ListView) view.findViewById(R.id.location_list);
        nearbyAdapter = new NearbyAdapter(getActivity());
        list.setAdapter(nearbyAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location != null) {
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    String url = "http://207.46.139.218:5000/travelApi?location="+location.getText().toString().trim();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response);
                                    parseJson(response);
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
                }
                else{
                    Toast.makeText(getContext(),"Enter Location",Toast.LENGTH_SHORT).show();
                }

            }
        });
        nearbyAdapter.setMode(Attributes.Mode.Single);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout) list.getChildAt(position - list.getFirstVisiblePosition())).open(true);
            }
        });
        return view;

    }

    public void parseJson(String response){
        int count = 0;
        nearbyAdapter.notifyDataSetChanged();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("nearby");
            while(count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                String description;
                String start;
                String distance;
                String duration;
                String end;
                String img;
                description = jo.getString("description");
                start = jo.getString("start");
                end = jo.getString("end");
                img = jo.getString("img");
                duration = jo.getString("duration");
                distance = jo.getString("distance");
                NearbyHelper n = new NearbyHelper(description,start,distance,duration,end,img);
                nearbyAdapter.add(n);
                nearbyAdapter.notifyDataSetChanged();

                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
