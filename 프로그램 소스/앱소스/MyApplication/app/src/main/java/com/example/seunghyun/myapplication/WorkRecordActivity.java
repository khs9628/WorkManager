package com.example.seunghyun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class WorkRecordActivity extends AppCompatActivity {

    Toolbar toolbarWorkRecord;
    TabLayout tabLayoutWorkRecord;
    TabItem tabItem1;
    TabItem tabItem2;


    ViewPager viewPagerWorkRecord;

    PageAdapterWorkRecord pageAdapterWorkRecord;


    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(WorkRecordActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    return true;

                case R.id.navigation_workRecord:
                    Intent intent2 = new Intent(WorkRecordActivity.this, WorkRecordActivity.class);
                    startActivity(intent2);
                    return true;

                case R.id.navigation_schedule:
                    Intent intent3 = new Intent(WorkRecordActivity.this, ScheduleActivity.class);
                    startActivity(intent3);
                    return true;

                case R.id.navigation_myPage:
                    Intent intent4 = new Intent(WorkRecordActivity.this, MyPageActivity.class);
                    startActivity(intent4);
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_record);

        //Home start
        toolbarWorkRecord = findViewById(R.id.toolbarWorkRecord);
        toolbarWorkRecord.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbarWorkRecord);
        tabLayoutWorkRecord = findViewById(R.id.tablayoutWorkRecord);
        tabItem1 = findViewById(R.id.tabItem1Work);
        tabItem2 = findViewById(R.id.tabItem2Work);

        viewPagerWorkRecord = findViewById(R.id.viewPagerWorkRecord);
        pageAdapterWorkRecord = new PageAdapterWorkRecord(getSupportFragmentManager(), tabLayoutWorkRecord.getTabCount());
        viewPagerWorkRecord.setAdapter(pageAdapterWorkRecord);
        viewPagerWorkRecord.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutWorkRecord));
        //end


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_workRecord);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
