package com.gdgvitvellore.smile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikhil on 17/4/16.
 */
public class Itinireary extends Fragment {
    ListView list;
    Toolbar toolbar;
    String bf,lunch,dinner, response;
    String []listV;
    ItiAdapter itiAdapter;
    int count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itinierary, container, false);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar_iti);
        list = (ListView) view.findViewById(R.id.iti_list);
        toolbar.setTitle("Your Customised Itinerary");
        bf = getArguments().getString("Breakfast");
        lunch = getArguments().getString("Lunch");
        dinner = getArguments().getString("Dinner");
        response = getArguments().getString("Response");
        listV = new String[6];
        itiAdapter = new ItiAdapter(getContext(),R.layout.layout_iti);
        list.setAdapter(itiAdapter);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("itenary");
            System.out.println(jsonArray.toString());
            while(count<jsonArray.length()){
                JSONObject jo = jsonArray.optJSONObject(count);
                String name;
                name = jo.getString("name");
               /* switch (count){
                    case 0:
                    case 1:
                       itenaryHelper = new ItenaryHelper(name);
                        itiAdapter.add(itenaryHelper);
                        break;
                    case 2:
                        itenaryHelper = new ItenaryHelper(bf);
                        itiAdapter.add(itenaryHelper);
                        break;
                    case 3:
                        itenaryHelper = new ItenaryHelper(name);
                        itiAdapter.add(itenaryHelper);
                        break;
                    case 4:
                        itenaryHelper = new ItenaryHelper(name);
                        itiAdapter.add(itenaryHelper);
                        break;
                    case 5:
                        itenaryHelper = new ItenaryHelper(lunch);
                        itiAdapter.add(itenaryHelper);
                        break;
                    case 6:
                        itenaryHelper = new ItenaryHelper(name);
                        itiAdapter.add(itenaryHelper);
                        break;
                    case 7:
                        itenaryHelper = new ItenaryHelper(name);
                        itiAdapter.add(itenaryHelper);
                        break;
                    case 8:
                        itenaryHelper = new ItenaryHelper(name);
                        itiAdapter.add(itenaryHelper);
                        break;
                }*/
                ItenaryHelper itenaryHelper = new ItenaryHelper(name);
                itiAdapter.add(itenaryHelper);
                itiAdapter.notifyDataSetChanged();
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}
