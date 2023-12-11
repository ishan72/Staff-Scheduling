package com.example.staffscheduler;

import com.example.staffscheduler.models.Schedule;
import com.example.staffscheduler.models.Staff;
import com.example.staffscheduler.service.StaffService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SchedulePopulator {
    public static String getDisplayDate(Calendar c){
        int day = c.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = "";
        switch (day){
            case 1:
                dayOfWeek = "Monday";
                break;
            case 2:
                dayOfWeek = "Tuesday";
                break;
            case 3:
                dayOfWeek = "Wednesday";
                break;
            case 4:
                dayOfWeek = "Thursday";
                break;

            case 5:
                dayOfWeek = "Friday";
                break;
            case 6:
                dayOfWeek = "Saturday";
                break;
            default:
                dayOfWeek = "Sunday";
        }
        String date = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH)+ 1) +"/"+ c.get(Calendar.DATE);
        return dayOfWeek + " " + date;
    }
    public static HashMap<String, List<String>> getData(DBHandler dbHandler) {
        StaffService staffService = new StaffService(dbHandler);

        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
        Calendar c = Calendar.getInstance();
        Calendar ctemp = Calendar.getInstance();
        Long current = c.getTimeInMillis();

        List<Schedule> schedules = dbHandler.getAllSchedule(current,7);
        for (int i= 1; i <= 7; i++){
            String displayDate = getDisplayDate(c);
            List<String> scheduleForDay = new ArrayList<String>();
            for (Schedule s : schedules)
            {
                ctemp.setTimeInMillis(s.getStartTime());
                System.out.println("\nid " + s.getStaff_id() + " today = " + c.get(Calendar.YEAR) + " " +c.get(Calendar.MONTH) + " "+ c.get(Calendar.DATE)
                        + " scheduled day =" + ctemp.get(Calendar.YEAR) + " " +ctemp.get(Calendar.MONTH) + " dummydate "+ ctemp.get(Calendar.DATE));
                if(ctemp.get(Calendar.YEAR)== c.get(Calendar.YEAR) && ctemp.get(Calendar.MONTH)== c.get(Calendar.MONTH) &&ctemp.get(Calendar.DATE)== c.get(Calendar.DATE) ){
                    System.out.print("\t equal");
                    Staff staff = staffService.getStaffById(s.getStaff_id());
                    scheduleForDay.add(s.getDisplayTimeStart()+" - "+s.getDisplayTimeEnd()+"   "+staff.getFname()+" "+staff.getLname());
                }
            }
            expandableListDetail.put(getDisplayDate(c), scheduleForDay);
            c.add(Calendar.HOUR_OF_DAY, 24);
        }

        return expandableListDetail;
    }
}
