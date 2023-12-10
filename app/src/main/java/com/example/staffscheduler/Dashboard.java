package com.example.staffscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class Dashboard extends AppCompatActivity {
    TextView navInfo;
    TabLayout tab;
    ViewPager viewPager;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);
        dbHandler = new DBHandler(Dashboard.this);

        ViewPagerStaffViewAdapter adapter = new ViewPagerStaffViewAdapter(getSupportFragmentManager(), dbHandler);
        viewPager.setAdapter(adapter);



        navInfo = findViewById(R.id.navInfo);

        Bundle bundle = getIntent().getExtras();

        tab.setupWithViewPager(viewPager);
        assert bundle != null;
        String formattedNavText = String.format("Welcome, %s!", bundle.getString("uname"));

        navInfo.setText(formattedNavText);
    }
}