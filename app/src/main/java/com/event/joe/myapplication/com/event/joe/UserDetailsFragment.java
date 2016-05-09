package com.event.joe.myapplication.com.event.joe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.event.joe.myapplication.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Joe Millership on 22/04/2016.
 */
public class UserDetailsFragment extends Fragment {
    View view;
    HashMap<String, String> currentUser;
    MySQLiteHelper mySQLiteHelper;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String username;
    List<Memory> list;
    OnMemorySetListener onMemorySetListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_details_layout, container, false);
        mySQLiteHelper = new MySQLiteHelper(getActivity());
        pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        username = pref.getString("username", "None Found");
        currentUser = mySQLiteHelper.getUserDetails(username);
        EditText etFirstName = (EditText)view.findViewById(R.id.user_firstName);
        EditText etLastName = (EditText)view.findViewById(R.id.user_lastName);
        etFirstName.setText(currentUser.get("firstName"));
        etLastName.setText(currentUser.get("lastName"));

        Button btnUpdateAccount = (Button) view.findViewById(R.id.btnSaveDetails);
        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etFirstName = (EditText)view.findViewById(R.id.user_firstName);
                EditText etLastName = (EditText)view.findViewById(R.id.user_lastName);
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                mySQLiteHelper.editUserDetails(firstName, lastName, currentUser.get("userID"));
                onMemorySetListener.editMemoryComplete();
                onMemorySetListener.setNewName(firstName + " " + lastName);
            }
        });

        Button btnDeleteAccount = (Button)view.findViewById(R.id.btnDeleteAccount);
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskOption();
            }
        });


        list = mySQLiteHelper .getAllMemories(currentUser.get("userID"));

        TextView amountOfMemories = (TextView)view.findViewById(R.id.tvNumberMemories);
        amountOfMemories.setText("You currently have " + list.size() + " memories saved");


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onMemorySetListener = (OnMemorySetListener)activity;
    }

    private AlertDialog AskOption() {
        final AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mySQLiteHelper.deleteAccount(currentUser.get("userID"));
                        getActivity().finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
