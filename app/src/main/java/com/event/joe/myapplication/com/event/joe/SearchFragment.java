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
        import android.widget.Toast;

        import com.event.joe.myapplication.R;
        import com.event.joe.myapplication.listadapter.MemoryListAdapter;
        import com.event.joe.myapplication.listadapter.MemoryListDetailProvider;

        import java.util.List;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class SearchFragment extends Fragment {
    String test;
    ListView lv;
    View view;
    private String idPosition;
    private MySQLiteHelper mySQLiteHelper;
    private List<Memory> list;
    OnMemorySetListener onMemorySetListener;
    Memory memory;

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
    EditText searchTerm;
    private String searchTermText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_layout, container, false);
        Button btnSearch = (Button)view.findViewById(R.id.btnSearch);
        searchTerm = (EditText)view.findViewById(R.id.etSearchTerm);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTermText = searchTerm.getText().toString();
                pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                editor = pref.edit();
                userID = pref.getString("userID", "none");
                mySQLiteHelper = new MySQLiteHelper(getActivity());
                list = mySQLiteHelper.getAllMemoriesSearch(userID, searchTermText);
                if (list.isEmpty()){
                    Toast.makeText(getActivity(), "No Memories Found", Toast.LENGTH_SHORT).show();
                }
                setList(searchTermText);
            }
        });
        return view;
    }

    public void setList(String searchTerm) {
        ListView lv = (ListView)view.findViewById(R.id.searchList);
        list.clear();
        list = mySQLiteHelper.getAllMemoriesSearch(userID, searchTerm);
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
                memory = list.get(position);
                Memory memory = list.get(position);
                onMemorySetListener.viewMemoryDetails(memory);
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
                        setList(searchTermText);
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
        onMemorySetListener = (OnMemorySetListener)activity;
    }
}
