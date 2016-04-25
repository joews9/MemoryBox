package com.event.joe.myapplication;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.event.joe.myapplication.com.event.joe.Memory;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class AddMemoryFragment extends Fragment{
    private EditText date;
    private SimpleDateFormat dateFormatter;
    private EditText description;
    private EditText title;
    private EditText location;
    private Spinner category;
    private String textDescription;
    private String textLocation;
    private String textTitle;


    private String imageResource = "none";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_memory_layout, container, false);

        /*
        * Set all Edit Texts
        */
        date = (EditText)view.findViewById(R.id.memory_date);
        description = (EditText)view.findViewById(R.id.memory_description);
        title = (EditText)view.findViewById(R.id.memory_title);
        location = (EditText)view.findViewById(R.id.memory_location);
        category = (Spinner)view.findViewById(R.id.spinnerCat);

        textDescription = description.getText().toString();
        textLocation = location.getText().toString();
        textTitle = title.getText().toString();
        final String textCategory = category.getSelectedItem().toString();

        Button addMemory = (Button)view.findViewById(R.id.btnAddMemory);
        addMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memory memory = new Memory(textDescription, "Joe", textLocation, imageResource, textTitle, textCategory);
                MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getActivity());
                mySQLiteHelper.saveMemory(memory);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment hf = new HomeFragment();
                fragmentTransaction.replace(R.id.fragmentPlaceHolder, hf);
                fragmentTransaction.commit();
            }
        });


        ImageButton cameraButton = (ImageButton)view.findViewById(R.id.btnCamera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        return view;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText)getView().findViewById(R.id.memory_date);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DatePickerFragment dialog=new DatePickerFragment(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }

}
