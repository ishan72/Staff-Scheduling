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

import com.example.staffscheduler.DBHandler;
import com.example.staffscheduler.R;
import com.example.staffscheduler.models.Staff;
import com.example.staffscheduler.service.ScheduleService;

import java.util.ArrayList;
import java.util.Calendar;

public class StaffAdapter extends ArrayAdapter<Staff> {
    private DBHandler dbHandler;
    private ScheduleService scheduleService;

    public StaffAdapter(Context context, DBHandler dbHandler, ArrayList<Staff> Staffs) {
        super(context, 0, Staffs);
        this.dbHandler = dbHandler;
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
        final Calendar calendarStart = Calendar.getInstance();
        final Calendar calendarEnd = Calendar.getInstance();

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
                        showTimePicker(staff,calendarStart);
                        Toast.makeText(getContext(), "Pick End Time.", Toast.LENGTH_LONG).show();
                        showTimePicker(staff, calendarEnd);
                    }
                },
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
        scheduleService.addSchedule(staff, calendarStart, calendarEnd);
    }

    private void showTimePicker(Staff staff, final Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        // Handle the selected time
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);

                        // Format the selected date and time
//                        String selectedDateTime = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", calendar).toString();
//                        Toast.makeText(getContext(), calendar.get(Calendar.YEAR) +calendar.get(Calendar.MONTH) +calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR) +calendar.get(Calendar.MINUTE) +" " , Toast.LENGTH_SHORT).show();

                    }
                },
                hour,
                minute,
                true // 24-hour format
        );

        timePickerDialog.show();
        System.out.println(staff.getFname() +" " + staff.getStaffId());

    }


}
