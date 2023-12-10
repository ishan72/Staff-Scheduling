package com.example.staffscheduler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerStaffViewAdapter extends FragmentPagerAdapter {

    private DBHandler dbHandler;
    public ViewPagerStaffViewAdapter(@NonNull FragmentManager fm, DBHandler dbHandler) {
        super(fm);
        this.dbHandler = dbHandler;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new StaffListFragment();
        } else if (position == 1) {
            return new ScheduleFragment(dbHandler);
        } else{
            return new AddNewStaffFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        if(position == 0){
            return "Staff List";
        } else if (position == 1) {
            return "Schedule";
        } else{
            return "Add New Staff";
        }
    }
}
