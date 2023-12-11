package com.example.staffscheduler.service;

import com.example.staffscheduler.DBHandler;
import com.example.staffscheduler.models.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffService {

    private ArrayList<Staff> staffList;
    private DBHandler dbHandler;
    public StaffService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        this.staffList = new ArrayList<>();
    }

    public void populateStaffs(){
        this.staffList = dbHandler.getAllStaffs();
    }
    public Staff getStaffById(int id){
        if (staffList.size() == 0){
            populateStaffs();
        }
        for (Staff staff : staffList) {
            if (staff.getStaffId() == id){
                return staff;
            }
        }
        return new Staff("Not found", "Not found", "none");
    }
}
