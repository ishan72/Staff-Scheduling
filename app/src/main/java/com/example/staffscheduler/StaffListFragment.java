package com.example.staffscheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.staffscheduler.adapter.StaffAdapter;
import com.example.staffscheduler.models.Staff;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StaffListFragment extends Fragment {

    ListView listView;
    private ArrayList<Staff> staffList;
    private StaffAdapter adapter;
    private DBHandler dbHandler;

    public StaffListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff_list, container, false);
        listView = view.findViewById(R.id.staffList);
        staffList = new ArrayList<Staff>();

        dbHandler = new DBHandler(getContext());

        adapter = new StaffAdapter(getContext(), dbHandler, staffList);
        listView.setAdapter(adapter);

        populateStaffList();

        return view;
    }



    public void populateStaffList() {
        staffList.clear();
        staffList = dbHandler.getAllStaffs();
        adapter.addAll(staffList);
        adapter.notifyDataSetChanged();
    }
}