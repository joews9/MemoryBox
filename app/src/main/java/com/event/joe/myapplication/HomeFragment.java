package com.event.joe.myapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.event.joe.myapplication.com.event.joe.Memory;
import com.event.joe.myapplication.com.event.joe.listadapter.MemoryListAdapter;
import com.event.joe.myapplication.com.event.joe.listadapter.MemoryListDetailProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class HomeFragment extends Fragment {
    View view;
    private String idPosition;
    MySQLiteHelper mySQLiteHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timeline_layout, container, false);
        mySQLiteHelper = new MySQLiteHelper(getActivity());
        setList();

        Button btnQuickPost = (Button) view.findViewById(R.id.btnAddTimeline);
        btnQuickPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get quick memory text from the edit text
                EditText quickMemory = (EditText) view.findViewById(R.id.etQuickPost);
                String quickMemoryText = quickMemory.getText().toString();

                //create a memory using only description
                Memory memory = new Memory(quickMemoryText);

                //save memory in database
                mySQLiteHelper.saveMemory(memory);
                System.out.println("goe");
                //clear quick memory edit text
                quickMemory.getText().clear();
                setList();
            }
        });

        return view;

    }

    public void setList() {
        ListView lv = (ListView)view.findViewById(R.id.list_timeline);
        List<Memory> list = mySQLiteHelper.getAllMemories();
        MemoryListAdapter eventAdapter = new MemoryListAdapter(getActivity(),R.layout.memory_list_cell);
        lv.setAdapter(eventAdapter);

        for(int i = 0; i <list.size(); i++) {
            Memory memory = list.get(i);
            MemoryListDetailProvider dataProvider = new MemoryListDetailProvider (memory.getMemoryDate(), memory.getTitle());
            eventAdapter.add(dataProvider);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ViewMemoryFragment vmf = new ViewMemoryFragment();
                fragmentTransaction.add(R.id.fragmentPlaceHolder, vmf);
                fragmentTransaction.commit();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                List<Memory> list = mySQLiteHelper.getAllMemories();
                Memory memory = list.get(position);
                idPosition = memory.getId();
                AlertDialog diaBox = AskOption();
                diaBox.show();
                return true;
            }
        });
    }
    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete this Memory?")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        mySQLiteHelper.deleteMemory(idPosition);
                        setList();
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
