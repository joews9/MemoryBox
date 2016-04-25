package com.event.joe.myapplication.com.event.joe.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.event.joe.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe Millership on 16/04/2016.
 */
public class MemoryListAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public MemoryListAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler{
        TextView memoryTitle;
        TextView memoryDate;
        TextView memoryLocation;


    }
    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        DataHandler handler;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.memory_list_cell, parent, false);
            handler = new DataHandler();
            handler.memoryDate = (TextView)row.findViewById(R.id.title_item);
            handler.memoryTitle = (TextView)row.findViewById(R.id.date_item);
            handler.memoryLocation = (TextView)row.findViewById(R.id.location_item);
            row.setTag(handler);

        }else{
            handler = (DataHandler)row.getTag();
        }
        MemoryListDetailProvider eventProvider = (MemoryListDetailProvider)this.getItem(position);
        handler.memoryTitle.setText(eventProvider.getMemoryTitle());
        handler.memoryDate.setText(eventProvider.getMemoryDate());
        handler.memoryLocation.setText(eventProvider.getMemoryLocation());


        return row;
    }

}
