package com.example.seunghyun.myapplication;

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

public class MyPageActivity extends AppCompatActivity {

    Toolbar toolbarMyPage;
    TabLayout tabLayoutMyPage;
    TabItem tabItem1;
    TabItem tabItem2;


    ViewPager viewPagerMyPage;
    PageAdapterMyPage pageAdapterMyPage;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(MyPageActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    return true;

                case R.id.navigation_workRecord:
                    Intent intent2 = new Intent(MyPageActivity.this, WorkRecordActivity.class);
                    startActivity(intent2);
                    return true;

                case R.id.navigation_schedule:
                    Intent intent3 = new Intent(MyPageActivity.this, ScheduleActivity.class);
                    startActivity(intent3);
                    return true;

                case R.id.navigation_myPage:
                    Intent intent4 = new Intent(MyPageActivity.this, MyPageActivity.class);
                    startActivity(intent4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        //Home start
        toolbarMyPage = findViewById(R.id.toolbarMyPage);
        toolbarMyPage.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbarMyPage);
        tabLayoutMyPage = findViewById(R.id.tablayoutMyPage);
        tabItem1 = findViewById(R.id.tabItem1MyPage);
        tabItem2 = findViewById(R.id.tabItem2MyPage);

        viewPagerMyPage = findViewById(R.id.viewPagerMyPage);
        pageAdapterMyPage = new PageAdapterMyPage(getSupportFragmentManager(), tabLayoutMyPage.getTabCount());
        viewPagerMyPage.setAdapter(pageAdapterMyPage);
        viewPagerMyPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutMyPage));
        //end

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_myPage);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
