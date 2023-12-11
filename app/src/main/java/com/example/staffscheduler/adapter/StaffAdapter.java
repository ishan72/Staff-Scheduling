package com.example.staffscheduler.adapter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.staffscheduler.DBHandler;
import com.example.staffscheduler.R;
import com.example.staffscheduler.ScheduleFragment;
import com.example.staffscheduler.StaffListFragment;
import com.example.staffscheduler.models.Staff;
import com.example.staffscheduler.service.ScheduleService;

import java.util.ArrayList;
import java.util.Calendar;

public class StaffAdapter extends ArrayAdapter<Staff> {
    private DBHandler dbHandler;
    private ScheduleService scheduleService;

    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();
    private Context context;
    public StaffAdapter(Context context, DBHandler dbHandler, ArrayList<Staff> Staffs) {
        super(context, 0, Staffs);
        this.dbHandler = dbHandler;
        this.context = context;
        this.scheduleService = new ScheduleService(dbHandler);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Staff staff = getItem(position);
//        convertView.setTag(staff.getStaffId());

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_staff, parent, false);
        }
        // Lookup view for data population
        TextView fName = (TextView) convertView.findViewById(R.id.fName
        );
        TextView lName = (TextView) convertView.findViewById(R.id.lName);
        TextView role = (TextView) convertView.findViewById(R.id.role);
        Button btnAddSchedule = (Button) convertView.findViewById(R.id.btnAddSchedule);
        btnAddSchedule.setTag(position);
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Staff staff = getItem(position);
                showDateTimePicker(staff);
            }
        });
        // Populate the data into the template view using the data object
        fName.setText(staff.getFname());
        lName.setText(staff.getLname());
        role.setText(staff.getRole());
        // Return the completed view to render on screen
        return convertView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDateTimePicker(Staff staff) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.getContext(),

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date
                        calendarStart.set(Calendar.YEAR, year);
                        calendarStart.set(Calendar.MONTH, month);
                        calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendarEnd.set(Calendar.YEAR, year);
                        calendarEnd.set(Calendar.MONTH, month);
                        calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Open time picker after selecting date
                        Toast.makeText(getContext(), "Pick Start Time.", Toast.LENGTH_LONG).show();
                        showTimePicker(staff,calendarEnd, 2);
                        showTimePicker(staff, calendarStart, 1);

                    }
                },
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePicker(Staff staff, final Calendar calendar, int index) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        // Handle the selected time
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);

//                        If end time is also changed then update the schedule
                        if (index == 2){
                            System.out.println("ishan final date changed "+calendarStart+" "+calendarEnd);
                            if (scheduleService.addSchedule(staff, calendarStart, calendarEnd)){
                                Toast.makeText(getContext(),"Schedule added successfully", Toast.LENGTH_SHORT).show();
                            }
                            refreshScheduleView();
                        }else {
                            Toast.makeText(getContext(), "Pick End Time.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true // 24-hour format
        );

        timePickerDialog.show();
    }


    private void refreshScheduleView(){
        ScheduleFragment scheduleFragment = new ScheduleFragment(dbHandler);
        FragmentTransaction transaction =  ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, scheduleFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

