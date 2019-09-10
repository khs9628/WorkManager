package com.example.seunghyun.myapplication;


import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviseRecordFragment extends Fragment {
    TextView tOption;
    EditText InputDate, InputSTime, InputETime, InputContent;
    String reviseDate, sTime, eTime, Content, userId, sendmsg, memberId;
    RadioGroup rg;
    RadioButton rb1, rb2;
    Button RequestRevise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_revise_record, container, false);

        InputDate = view.findViewById(R.id.revisedate);
        InputSTime = view.findViewById(R.id.stime);
        InputETime = view.findViewById(R.id.etime);
        InputContent = view.findViewById(R.id.content);
        tOption = view.findViewById(R.id.option);

        rg = view.findViewById(R.id.rg);
        rb1 = view.findViewById(R.id.record);
        rb2 = view.findViewById(R.id.calendar);

        RequestRevise = view.findViewById(R.id.requestRevise);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tOption.setText("근무기록 수정요청");
                RequestRevise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activity root = getActivity();

                        reviseDate = InputDate.getText().toString();
                        sTime  = InputSTime.getText().toString();
                        eTime = InputETime.getText().toString();
                        Content = InputContent.getText().toString();
                        userId = SharedPreference.getAttribute(getContext(), "userId");

                        sendmsg = "calendar_list";    //이름
                        try {
                            String result = new Task(sendmsg).execute(userId, "calendar_list").get();//디비값을 가져오기
                            memberId = new String(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(!checkDate(reviseDate)){
                            Toast.makeText(root, "날짜를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!checkTime(sTime)){
                            Toast.makeText(root, "출근 시간을 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!checkTime(eTime)){
                            Toast.makeText(root, "퇴근 시간을 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(Content.trim().length()==0){
                            Toast.makeText(root, "사유를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            sendmsg = "reviseRequest_write";
                            try {
                                String rst = new Task(sendmsg).execute(reviseDate, sTime, eTime, Content, memberId, "reviseRequest_write", "sTime_write", "eTime_write", "content_write", "memberId_write").get();
                                Toast.makeText(root, "관리자에게 근무기록 수정요청을 하였습니다.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tOption.setText("일정수정 요청");
                RequestRevise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activity root = getActivity();

                        reviseDate = InputDate.getText().toString();
                        Content = InputContent.getText().toString();
                        userId = SharedPreference.getAttribute(getContext(), "userId");

                        sendmsg = "calendar_list";    //이름
                        try {
                            String result = new Task(sendmsg).execute(userId, "calendar_list").get();//디비값을 가져오기
                            memberId = new String(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(!checkDate(reviseDate)){
                            Toast.makeText(root, "날짜를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(Content.trim().length()==0){
                            Toast.makeText(root, "사유를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            sendmsg = "calendar_write";
                            try {
                                String rst = new Task(sendmsg).execute(Content, memberId,reviseDate, "calendar_write", "memberId_write", "date_write").get();
                                Toast.makeText(root, "관리자에게 일정 수정요청을 하였습니다.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_revise_record, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_revise_record) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }

    public static boolean checkDate(String date) {

        String regex = "[0-9]+/[0-9]+/[0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(date);
        boolean isNormal = m.matches();
        return isNormal;
    }
    public static boolean checkTime(String time) {

        String regex = "[0-9]+:[0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(time);
        boolean isNormal = m.matches();
        return isNormal;
    }
}
