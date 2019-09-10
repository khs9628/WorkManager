package com.example.seunghyun.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class ScheduleActivity extends AppCompatActivity {

    Toolbar toolbarSchedule;
    TabLayout tabLayoutSchedule;
    TabItem tabItem1;
    TabItem tabItem2;

    ViewPager viewPagerSchedule;
    PageAdapterSchedule pageAdapterSchedule;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(ScheduleActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    return true;

                case R.id.navigation_workRecord:
                    Intent intent2 = new Intent(ScheduleActivity.this, WorkRecordActivity.class);
                    startActivity(intent2);
                    return true;

                case R.id.navigation_schedule:
                    Intent intent3 = new Intent(ScheduleActivity.this, ScheduleActivity.class);
                    startActivity(intent3);
                    return true;

                case R.id.navigation_myPage:
                    Intent intent4 = new Intent(ScheduleActivity.this, MyPageActivity.class);
                    startActivity(intent4);
                    return true;

            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //Home start
        toolbarSchedule = findViewById(R.id.toolbarSchedule);
        toolbarSchedule.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbarSchedule);
        tabLayoutSchedule = findViewById(R.id.tablayoutSchedule);
        tabItem1 = findViewById(R.id.tabItem1Schedule);
        tabItem2 = findViewById(R.id.tabItem2Schedule);

        viewPagerSchedule = findViewById(R.id.viewPagerSchedule);
        pageAdapterSchedule = new PageAdapterSchedule(getSupportFragmentManager(), tabLayoutSchedule.getTabCount());
        viewPagerSchedule.setAdapter(pageAdapterSchedule);
        viewPagerSchedule.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutSchedule));
        //end


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_schedule);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}

