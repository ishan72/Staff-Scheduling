package com.example.staffscheduler.models;

public class Staff {
    private int staffId;
    private String fname;
    private String lname;
    private String role;

    public Staff() {
    }

    public Staff(String fname, String lname, String role) {
        this.fname = fname;
        this.lname = lname;
        this.role = role;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
