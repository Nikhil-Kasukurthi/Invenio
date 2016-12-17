package com.gdgvitvellore.smile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhil on 17/4/16.
 */
public class ItiAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public void add(ItenaryHelper object) {
        list.add(object);
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItiHolder itiHolder = new ItiHolder();
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.layout_iti, null);
            itiHolder.name = (TextView) row.findViewById(R.id.place_name);
            row.setTag(itiHolder);
        }else{
            itiHolder = (ItiHolder) row.getTag();
        }
            ItenaryHelper itenaryHelper = (ItenaryHelper) this.getItem(position);
        itiHolder.name.setText(itenaryHelper.getName());
            return row;
    }

    public ItiAdapter(Context context, int resource) {
        super(context, resource);
    }


    public class ItiHolder{
        TextView name;
    }
}
