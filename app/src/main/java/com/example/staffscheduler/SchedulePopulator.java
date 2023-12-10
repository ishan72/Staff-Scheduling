package com.example.staffscheduler;

import com.example.staffscheduler.models.Schedule;

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
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
        Calendar c = Calendar.getInstance();
        Calendar ctemp = Calendar.getInstance();
        Long current = c.getTimeInMillis();

        List<Schedule> schedules = dbHandler.getAllSchedule(current,7);
        for (int i= 1; i <= 7; i++){
            String displayDate = getDisplayDate(c);
            List<String> scheduleForDay = new ArrayList<String>();
            for (Schedule s :
                    schedules) {
                ctemp.setTimeInMillis(s.getStartTime()* 1000);
                System.out.println("id " + s.getStaff_id() + " today" + c.get(Calendar.YEAR) + " scheduled day =" + ctemp.get(Calendar.YEAR) + " " +ctemp.get(Calendar.MONTH) + " dummydate "+ ctemp.get(Calendar.DATE));
                if(ctemp.get(Calendar.YEAR)== c.get(Calendar.YEAR) && ctemp.get(Calendar.MONTH)== c.get(Calendar.MONTH) &&ctemp.get(Calendar.DATE)== c.get(Calendar.DATE) ){
                    scheduleForDay.add(s.getStaff_id() + " " + s.getStartTime());
                }
            }
            expandableListDetail.put(getDisplayDate(c), scheduleForDay);
            c.add(Calendar.HOUR_OF_DAY, 24);
        }


        List<String> cricket = new ArrayList<String>();
        cricket.add("India");
        cricket.add("Pakistan");
        cricket.add("Australia");
        cricket.add("England");
        cricket.add("South Africa");

        List<String> football = new ArrayList<String>();
        football.add("Brazil");
        football.add("Spain");
        football.add("Germany");
        football.add("Netherlands");
        football.add("Italy");

        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
        basketball.add("Spain");
        basketball.add("Argentina");
        basketball.add("France");
        basketball.add("Russia");
//
//        expandableListDetail.put("CRICKET TEAMS", cricket);
//        expandableListDetail.put("FOOTBALL TEAMS", football);
//        expandableListDetail.put("BASKETBALL TEAMS", basketball);
        return expandableListDetail;
    }
}
