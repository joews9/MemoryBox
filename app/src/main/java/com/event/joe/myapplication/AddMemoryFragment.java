package com.event.joe.myapplication;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import com.event.joe.myapplication.com.event.joe.Memory;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class AddMemoryFragment extends Fragment{
    private EditText dateItem;
    private SimpleDateFormat dateFormatter;
    private EditText description;
    private EditText title;
    private EditText location;
    private Spinner category;
    private String textDescription;
    private String textLocation;
    private String textTitle;


    private String imageResource = "big memory";
    //TODO: Small Image Resource for List
    private String smallImageResource = "big memory";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_memory_layout, container, false);

        /*
        * Set all Edit Texts
        */
        dateItem = (EditText)view.findViewById(R.id.memory_date);
        description = (EditText)view.findViewById(R.id.memory_description);
        title = (EditText)view.findViewById(R.id.memory_title);
        location = (EditText)view.findViewById(R.id.memory_location);
        category = (Spinner)view.findViewById(R.id.spinnerCat);

        EditText txtDate=(EditText)view.findViewById(R.id.memory_date);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog=new DatePickerFragment(v);
                FragmentTransaction ft =getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
            }

        });

        Button addMemory = (Button)view.findViewById(R.id.btnAddMemory);
        addMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDescription = description.getText().toString();
                textLocation = location.getText().toString();
                textTitle = title.getText().toString();
                String textCategory = category.getSelectedItem().toString();
                String memoryDate = dateItem.getText().toString();

                Memory memory = new Memory(textDescription, memoryDate, textLocation, imageResource, textTitle, textCategory, imageResource);
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

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        Toast.makeText(getActivity(), imageResource, Toast.LENGTH_SHORT).show();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        imageResource = "sdcard/pictures/" + imageFileName +"-1483040060" + ".jpg";
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}
