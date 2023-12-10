package com.example.staffscheduler.models;

public class Schedule {
    private int staff_id;
    private long startTime;
    private long endTime;
    private String date;

    public Schedule(int staff_id, long startTime, long endTime, String date) {
        this.staff_id = staff_id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


