package com.example.seunghyun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class LoginActivity extends AppCompatActivity {
    TextView warning;
    Intent _cardService;
    String id,sendmsg, oj, status;
    Button WorkOn, WorkOff;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    return true;

                case R.id.navigation_workRecord:
                    Intent intent2 = new Intent(LoginActivity.this, WorkRecordActivity.class);
                    startActivity(intent2);
                    return true;

                case R.id.navigation_schedule:
                    Intent intent3 = new Intent(LoginActivity.this, ScheduleActivity.class);
                    startActivity(intent3);
                    return true;

                case R.id.navigation_myPage:
                    Intent intent4 = new Intent(LoginActivity.this, MyPageActivity.class);
                    startActivity(intent4);
                    return true;

               }
            return false;
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(_cardService);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        warning = (TextView) findViewById(R.id.warning);
        _cardService = new Intent(this, CardService.class); startService(_cardService);

        status="0";
        if(SharedPreference.getAttribute(getApplicationContext(),"status")!=null){
            status = SharedPreference.getAttribute(getApplicationContext(),"status");
        }

            Button btnLogOut = (Button) findViewById(R.id.logout);
            btnLogOut.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    SharedPreference.removeAttribute(getApplicationContext(),"userId");
                    SharedPreference.removeAttribute(getApplicationContext(), "userPw");

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            id = SharedPreference.getAttribute(getApplicationContext(), "userId");
            // Inflate the layout for this fragment
            WorkOn = (Button)findViewById(R.id.workOn);
            WorkOff = (Button)findViewById(R.id.workOff);


            WorkOn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                   if(!status.equals("0")){
                        warning.setText("이미 출근 하셨습니다.\n퇴근 버튼을 눌러 주세요");
                    }
                    else {
                        sendmsg = "NFCMain_list";    //id
                        try {

                                String result = new Task(sendmsg).execute(id, "NFCMain_list").get();
                                oj = new String(result);


                            if (oj.trim().length() == 12) {
                                sendmsg = "addWorktime_write";
                                try {
                                    String rst = new Task(sendmsg).execute(id, status, "addWorktime_write" ,"status").get();
                                    if(rst.trim().equals("workOn")){
                                        Toast.makeText(getApplicationContext(), "출근 처리되었습니다.\n오늘도 화이팅~",
                                                Toast.LENGTH_SHORT).show();
                                        status="1";
                                        SharedPreference.setAttribute(getApplicationContext(),"status", status);
                                        warning.setText("");
                                    }
                                    else{
                                        warning.setText("버튼을 다시 누르고 \n리더기에 휴대폰을 터치해 주세요");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                warning.setText("버튼을 다시 누르고 \n리더기에 휴대폰을 터치해 주세요");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            WorkOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!status.equals("1")){
                        warning.setText("아직 출근 하지 않으셨습니다.\n출근 버튼을 눌러 주세요");
                    }
                    else {
                        sendmsg = "NFCMain_list";    //id
                        try {
                            String result = new Task(sendmsg).execute(id, "NFCMain_list").get();
                            oj = new String(result);

                            if (oj.trim().length()==12) {
                                sendmsg = "addWorktime_write";
                                try {
                                    String rst = new Task(sendmsg).execute(id, status, "addWorktime_write" ,"status").get();
                                    if(rst.trim().equals("workOff")){
                                        Toast.makeText(getApplicationContext(), "퇴근 처리되었습니다.\n오늘도 수고 많으셨습니다.",
                                                Toast.LENGTH_SHORT).show();
                                        status="0";
                                        SharedPreference.setAttribute(getApplicationContext(),"status", status);
                                        warning.setText("");
                                    }
                                    else{
                                        warning.setText("버튼을 다시 누르고 \n리더기에 휴대폰을 터치해 주세요");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else{
                                warning.setText("버튼을 다시 누르고 \n리더기에 휴대폰을 터치해 주세요");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
