package com.gdgvitvellore.smile.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gdgvitvellore.smile.NearbyHelper;
import com.gdgvitvellore.smile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 9/4/16.
 */
public class NearbyAdapter extends BaseSwipeAdapter {
    List<NearbyHelper> list = new ArrayList<>();
    private Context mContext;

    public void add(NearbyHelper object){list.add(object);}

    public int getCount(){return list.size();}

    public Object getItem(int position){return list.get(position);}

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public NearbyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;

    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_nearby, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addSwipeListener(new SimpleSwipeListener(){
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.aas));
            }
        });

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        View row;
        row = convertView;
        Nearby nearby = new Nearby();

        nearby.description = (TextView) row.findViewById(R.id.description);
        nearby.distance = (TextView) row.findViewById(R.id.distance);
        nearby.duration = (TextView) row.findViewById(R.id.duration);
        nearby.end = (TextView) row.findViewById(R.id.end);
        nearby.start = (TextView) row.findViewById(R.id.start);
        row.setTag(nearby);

        NearbyHelper nearbyHelper = (NearbyHelper) this.getItem(position);
        nearby.description.setText(nearbyHelper.getDescription());
        nearby.distance.setText(nearbyHelper.getDistance());
        nearby.duration.setText(nearbyHelper.getDuration());
        nearby.end.setText(nearbyHelper.getEnd());
        nearby.start.setText(nearbyHelper.getStart());
    }

    class Nearby{
        TextView description, distance, duration, end, start;
    }
}
