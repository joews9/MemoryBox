package com.event.joe.myapplication.com.event.joe;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.event.joe.myapplication.R;

import org.w3c.dom.Text;

/**
 * Created by Joe Millership on 22/05/2016.
 */
public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, container, false);

//        EditText editText = (EditText)view.findViewById(R.id.etAddCategory);
//        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getActivity());
//        mySQLiteHelper.addCategory(editText.getText().toString());
//        Toast.makeText(getActivity(), "Category Added", Toast.LENGTH_SHORT).show();
        return view;
    }
}
