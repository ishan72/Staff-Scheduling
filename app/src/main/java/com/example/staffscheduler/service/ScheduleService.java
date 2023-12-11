package com.example.staffscheduler.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.staffscheduler.DBHandler;
import com.example.staffscheduler.models.Staff;

import java.util.Calendar;
import java.util.Date;

public class ScheduleService {
    private DBHandler dbHandler;
    public ScheduleService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean addSchedule(Staff staff, Calendar startTime, Calendar endTime){
        String date = startTime.get(Calendar.YEAR ) + " " +startTime.get(Calendar.MONTH ) + " " +startTime.get(Calendar.DATE);

        System.out.println("ishanDate" + date);

        long start = startTime.getTimeInMillis();

        long end = endTime.getTimeInMillis();

        return dbHandler.addNewSchedule(staff.getStaffId(), date, start, end);
    }
}
