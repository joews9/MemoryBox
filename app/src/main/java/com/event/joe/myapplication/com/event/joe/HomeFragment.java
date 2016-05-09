package com.event.joe.myapplication.com.event.joe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.event.joe.myapplication.R;
import com.event.joe.myapplication.listadapter.MemoryListAdapter;
import com.event.joe.myapplication.listadapter.MemoryListDetailProvider;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class HomeFragment extends Fragment {
    private View view;
    private String idPosition;
    private MySQLiteHelper mySQLiteHelper;
    private List<Memory> list;

    private static final String MEMORY_DATE = "memoryDate";
    private static final String MEMORY_CATEGORY = "memoryCategory";
    private static final String MEMORY_LOCATION = "memoryLocation";
    private static final String MEMORY_DESCRIPTION = "memoryDescription";
    private static final String MEMORY_IMAGE = "memoryImage";
    private static final String MEMORY_TITLE = "memoryTitle";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String username;
    String userID;
    Memory memory;

    OnMemorySetListener onMemorySetListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timeline_layout, container, false);
        pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        userID = pref.getString("userID", "none");
        mySQLiteHelper = new MySQLiteHelper(getActivity());
        list = mySQLiteHelper.getAllMemories(userID);
        setList();
        Button btnQuickPost = (Button) view.findViewById(R.id.btnAddTimeline);
        btnQuickPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get quick memory text from the edit text
                EditText quickMemory = (EditText) view.findViewById(R.id.etQuickPost);
                String quickMemoryText = quickMemory.getText().toString();
                Memory memory = new Memory(quickMemoryText, userID);
                mySQLiteHelper.saveMemory(memory);
                quickMemory.getText().clear();
                setList();
            }
        });

        return view;
    }

    public void setList() {
        ListView lv = (ListView)view.findViewById(R.id.list_timeline);
        list.clear();
        list = mySQLiteHelper.getAllMemories(userID);
        MemoryListAdapter eventAdapter = new MemoryListAdapter(getActivity(),R.layout.memory_list_cell);
        lv.setAdapter(eventAdapter);

        for(int i = 0; i <list.size(); i++) {
            Memory memory = list.get(i);
            MemoryListDetailProvider dataProvider = new MemoryListDetailProvider (memory.getMemoryDate(), memory.getTitle(), memory.getLocation(), memory.getSmallImageResource());
            eventAdapter.add(dataProvider);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memory memory = list.get(position);
                Intent intent = new Intent(getActivity(), MemoryViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(MEMORY_DATE, memory.getMemoryDate());
                bundle.putString(MEMORY_LOCATION, memory.getLocation());
                bundle.putString(MEMORY_CATEGORY, memory.getCategory());
                bundle.putString(MEMORY_DESCRIPTION, memory.getDescription());
                bundle.putString(MEMORY_IMAGE, memory.getImageResource());
                bundle.putString(MEMORY_TITLE, memory.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                List<Memory> list = mySQLiteHelper.getAllMemories(userID);
                memory = list.get(position);
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
                .setTitle("Edit")
                .setMessage("What would you like to do with this memory?")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        mySQLiteHelper.deleteMemory(idPosition);
                        setList();
                        dialog.dismiss();
                    }

                })
                .setNeutralButton("Edit", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        onMemorySetListener.editMemory(memory);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{

            onMemorySetListener = (OnMemorySetListener)activity;

        }catch(Exception ex){}

    }
}
