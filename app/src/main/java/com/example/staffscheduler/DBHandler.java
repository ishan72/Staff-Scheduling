package com.example.staffscheduler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.staffscheduler.models.Schedule;
import com.example.staffscheduler.models.Staff;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "scheduler";
    private static final int DB_VERSION = 1;
    private static final String TABLE_STAFF = "staff";
    private static final String STAFF_ID = "id";
    private static final String STAFF_FNAME = "fname";
    private static final String STAFF_LNAME = "lname";
    private static final String STAFF_ROLE = "role";


    private static final String TABLE_SCHEDULE = "schedule";
    private static final String SCHEDULE_ID = "schedule_id";
    private static final String SCHEDULE_STAFF_ID = "staff_id";
    private static final String SCHEDULE_DATE_COL = "date";
    private static final String SCHEDULE_START_TIME = "start_time";
    private static final String SCHEDULE_END_TIME = "end_time";

    public DBHandler(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String createStaffQuery = "CREATE TABLE " + TABLE_STAFF + "(" +
                STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STAFF_FNAME + " TEXT, " +
                STAFF_LNAME + " TEXT, " +
                STAFF_ROLE + " TEXT)";
        db.execSQL(createStaffQuery);

        String createScheduleQuery = "CREATE TABLE " + TABLE_SCHEDULE + "(" +
                SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SCHEDULE_STAFF_ID + " INTEGER, " +
                SCHEDULE_DATE_COL + " STRING NOT NULL, " +
                SCHEDULE_START_TIME + " LONG NOT NULL, " +
                SCHEDULE_END_TIME + " LONG NOT NULL, " +
                "FOREIGN KEY (" + SCHEDULE_STAFF_ID + ") REFERENCES " + TABLE_STAFF + "(" + STAFF_ID + "))";
        db.execSQL(createScheduleQuery);
    }



    public boolean addNewSchedule(int staffId, String date, long start_time, long end_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SCHEDULE_STAFF_ID, staffId);
        values.put(SCHEDULE_DATE_COL, date);
        values.put(SCHEDULE_START_TIME, start_time);
        values.put(SCHEDULE_END_TIME, end_time);
        System.out.println("startepoc " + start_time);
        try{
            long result = db.insert(TABLE_SCHEDULE, null, values);
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }finally {
            db.close();
        }
    }

    public List<Schedule> getAllSchedule(Long currentepoc , int uptodays) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select all staff names from the staff table
        String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through the cursor and add staff names to the list
        while (cursor.moveToNext()) {
            int columnIndexId = cursor.getColumnIndex(SCHEDULE_ID);
            int columnIndexStartTime = cursor.getColumnIndex(SCHEDULE_START_TIME);
            int columnIndexEndTime = cursor.getColumnIndex(SCHEDULE_END_TIME);
            int columnIndexDate = cursor.getColumnIndex(SCHEDULE_DATE_COL);
            int columnIndexStaffId = cursor.getColumnIndex(SCHEDULE_STAFF_ID);


//            Calendar c = Calendar.getInstance();
            if (columnIndexId >= 0) {
//                c.setTimeInMillis(cursor.getLong(columnIndexEndTime)* 1000);
//                if(c.after(Calendar.getInstance())){
                    Schedule schedule = new Schedule(cursor.getInt(columnIndexStaffId), cursor.getLong(columnIndexStartTime), cursor.getLong(columnIndexEndTime),cursor.getString(columnIndexDate));
                    schedules.add(schedule);
                System.out.println("retrieve date" + schedule.getDate());

//                }
            } else {
                // Handle the case where the column is not found
                // For example, log an error message
                Log.e("DBHandler", "Column 'name' not found in cursor");
            }
        }

        cursor.close();
        db.close();
        Calendar c = Calendar.getInstance();
        for (Schedule s :
                schedules) {
            c.setTimeInMillis(s.getStartTime()* 1000);
            System.out.print("id" + s.getStaff_id() + "scheduleABCD " + c.toString());
        }

        return schedules;
    }

    public void deleteSchedule(int scheduleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE, SCHEDULE_ID + " = ?", new String[]{String.valueOf(scheduleId)});
        db.close();
    }

    public ArrayList<Staff> getAllStaffs() {
        ArrayList<Staff> staffs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select all staff names from the staff table
        String selectQuery = "SELECT * FROM " + TABLE_STAFF;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through the cursor and add staff names to the list
        while (cursor.moveToNext()) {
            int columnIndexId = cursor.getColumnIndex(STAFF_ID);
            int columnIndexfn = cursor.getColumnIndex(STAFF_FNAME);
            int columnIndexln = cursor.getColumnIndex(STAFF_LNAME);
            int columnIndexr = cursor.getColumnIndex(STAFF_ROLE);
            if (columnIndexfn >= 0) {
                Staff staff = new Staff(cursor.getString(columnIndexfn),cursor.getString(columnIndexln),cursor.getString(columnIndexr));
                staff.setStaffId(cursor.getInt(columnIndexId));
                staffs.add(staff);
            } else {
                // Handle the case where the column is not found
                // For example, log an error message
                Log.e("DBHandler", "Column 'name' not found in cursor");
            }
        }

        cursor.close();
        db.close();

        return staffs;
    }

    public boolean addNewStaff(Staff staff){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STAFF_FNAME,staff.getFname());
        values.put(STAFF_LNAME,staff.getLname());
        values.put(STAFF_ROLE,staff.getRole());
        try{
            long result = db.insert(TABLE_STAFF, null, values);
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }finally {
            db.close();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_SCHEDULE);
        onCreate(db);
    }
}
