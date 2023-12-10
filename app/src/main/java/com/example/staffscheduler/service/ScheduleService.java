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
        String date = startTime.get(Calendar.YEAR ) + " " +startTime.get(Calendar.MONTH ) + " " +startTime.get(Calendar.DATE );
        System.out.println("add date" + date);
        Long start = Date.UTC(startTime.get(Calendar.YEAR) - 1900, startTime.get(Calendar.MONTH),
                startTime.get(Calendar.DAY_OF_MONTH), startTime.get(Calendar.HOUR_OF_DAY),
                startTime.get(Calendar.MINUTE), startTime.get(Calendar.SECOND));
        Long end = Date.UTC(endTime.get(Calendar.YEAR) - 1900, endTime.get(Calendar.MONTH),
                endTime.get(Calendar.DAY_OF_MONTH), endTime.get(Calendar.HOUR_OF_DAY),
                endTime.get(Calendar.MINUTE), endTime.get(Calendar.SECOND));
        return dbHandler.addNewSchedule(staff.getStaffId(), date, start, end);
    }
}
