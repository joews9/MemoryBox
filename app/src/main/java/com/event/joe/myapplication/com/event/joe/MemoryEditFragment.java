package com.event.joe.myapplication.com.event.joe;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.event.joe.myapplication.R;

/**
 * Created by Joe Millership on 09/05/2016.
 */
public class MemoryEditFragment extends Fragment {
    View view;
    MySQLiteHelper mySQLiteHelper;
    OnMemorySetListener onMemorySetListener;

    private static final String MEMORY_DATE = "memoryDate";
    private static final String MEMORY_CATEGORY = "memoryCategory";
    private static final String MEMORY_LOCATION = "memoryLocation";
    private static final String MEMORY_DESCRIPTION = "memoryDescription";
    private static final String MEMORY_IMAGE = "memoryImage";
    private static final String MEMORY_TITLE = "memoryTitle";
    private static final String MEMORY_ID = "memoryID";

    private String title;
    private String description;
    private String location;
    private String memoryID;
    private String date;
    private String category;
    private String imageResource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_memory_layout, container, false);
        mySQLiteHelper = new MySQLiteHelper(getActivity());
        EditText editTitle = (EditText)view.findViewById(R.id.edit_memory_title);
        EditText editDescription = (EditText)view.findViewById(R.id.edit_memory_description);
        EditText editDate = (EditText)view.findViewById(R.id.edit_memory_date);
        EditText editLocation = (EditText)view.findViewById(R.id.edit_memory_location);
        Spinner editCategory = (Spinner)view.findViewById(R.id.edit_spinnerCat);

        title = getArguments().getString(MEMORY_TITLE);
        description = getArguments().getString(MEMORY_DESCRIPTION);
        location = getArguments().getString(MEMORY_LOCATION);
        date = getArguments().getString(MEMORY_DATE);
        memoryID = getArguments().getString(MEMORY_ID);
        category = getArguments().getString(MEMORY_CATEGORY);
        imageResource = getArguments().getString(MEMORY_IMAGE);

        editTitle.setText(title);
        editLocation.setText(location);
        editDescription.setText(description);
        editDate.setText(date);

        Button btnCancelEdit = (Button)view.findViewById(R.id.btnCancelEdit);
        Button btnEditMemory = (Button)view.findViewById(R.id.btnEditMemory);
        btnEditMemory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editTitle = (EditText)view.findViewById(R.id.edit_memory_title);
                EditText editDescription = (EditText)view.findViewById(R.id.edit_memory_description);
                EditText editDate = (EditText)view.findViewById(R.id.edit_memory_date);
                EditText editLocation = (EditText)view.findViewById(R.id.edit_memory_location);
                Spinner editCategory = (Spinner)view.findViewById(R.id.edit_spinnerCat);

                title = editTitle.getText().toString();
                description = editDescription.getText().toString();
                date = editDate.getText().toString();
                location = editLocation.getText().toString();
                category = editCategory.getSelectedItem().toString();
                if (!category.equals("Quick Memory")) {
                    imageResource = "Big Memory";
                }
                Memory memory = new Memory(description, date, location, imageResource, title, category, null, null);
                memory.setId(memoryID);
                mySQLiteHelper.editCurrentMemory(memory);
                onMemorySetListener.editMemoryComplete();
            }
        });

        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMemorySetListener.editMemoryComplete();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onMemorySetListener = (OnMemorySetListener)activity;

    }
}
