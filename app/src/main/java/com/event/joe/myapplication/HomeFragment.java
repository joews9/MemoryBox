package com.event.joe.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.event.joe.myapplication.com.event.joe.Memory;
import com.event.joe.myapplication.com.event.joe.listadapter.MemoryListAdapter;
import com.event.joe.myapplication.com.event.joe.listadapter.MemoryListDetailProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class HomeFragment extends Fragment {
    View view;
    MySQLiteHelper mySQLiteHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.timeline_layout, container, false);
        mySQLiteHelper = new MySQLiteHelper(getActivity());
        setList();

        Button btnQuickPost = (Button)view.findViewById(R.id.btnAddTimeline);
        btnQuickPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get quick memory text from the edit text
                EditText quickMemory = (EditText)view.findViewById(R.id.etQuickPost);
                String quickMemoryText = quickMemory.getText().toString();

                //create a memory using only description
                Memory memory = new Memory(quickMemoryText);

                //save memory in database
                mySQLiteHelper.saveMemory(memory);
                System.out.println("goe");
                //clear quick memory edit text
                quickMemory.getText().clear();
                setList();
                System.out.println("Testing Git");
            }
        });

        return view;

    }
    public void setList() {
        List<Map<String, String>> array = mySQLiteHelper.getAllMemories();
        ListView lv = (ListView)view.findViewById(R.id.list_timeline);
        MemoryListAdapter itemsAdapter = new MemoryListAdapter(getActivity().getApplicationContext(), R.layout.memory_list_cell);
        lv.setAdapter(itemsAdapter);

        HashMap<String, String> test = new HashMap<>();

        for(int i = 0; i < array.size(); i++){
            Map<String, String> currentMemory = array.get(i);
            //TODO: Remove system outs and toasts
            System.out.println("********" + currentMemory);
            String title = currentMemory.get("title");
            String date = currentMemory.get("date");
            MemoryListDetailProvider memoryData = new MemoryListDetailProvider(title, date);
            itemsAdapter.add(memoryData);
        }

    }
}
