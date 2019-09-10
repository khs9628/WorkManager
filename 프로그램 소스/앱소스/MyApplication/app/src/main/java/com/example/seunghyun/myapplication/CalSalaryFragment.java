package com.example.seunghyun.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CalSalaryFragment extends Fragment {
    TextView tTotal, tNowTime, tBasic, tExtend, tNight, tWeekly, tRest, tHoliday, tDeduct;
    String id, sendmsg, oj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_cal_salary, container, false);

        tTotal = view.findViewById(R.id.total);
        tNowTime = view.findViewById(R.id.nowTime);
        tBasic = view.findViewById(R.id.basic);
        tExtend = view.findViewById(R.id.extend);
        tNight = view.findViewById(R.id.night);
        tWeekly = view.findViewById(R.id.weekly);
        tRest = view.findViewById(R.id.rest);
        tHoliday = view.findViewById(R.id.holiday);
        tDeduct = view.findViewById(R.id.deduct);

        id = SharedPreference.getAttribute(getContext(),"userId");

        sendmsg = "calSalary_list";    //이름


        try {
            String result = new Task(sendmsg).execute(id, "calSalary_list").get();//디비값을 가져오기
            oj = new String(result);

            String[] element = oj.split("-");
            String total = Comma(element[0].trim());
            tTotal.setText(total+" 원");

            tNowTime.setText(element[1].trim()+" 기준");

            String basic = Comma(element[2].trim());
            tBasic.setText(basic+" 원");

            String extend = Comma(element[3].trim());
            tExtend.setText(extend+" 원");

            String night = Comma(element[4].trim());
            tNight.setText(night+" 원");

            String weekly = Comma(element[5].trim());
            tWeekly.setText(weekly+" 원");

            String rest = Comma(element[6].trim());
            tRest.setText(rest+" 원");

            String holiday = Comma(element[7].trim());
            tHoliday.setText(holiday+" 원");

            String deduct = Comma(element[8].trim());
            tDeduct.setText(deduct+" 원");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cal_salary, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cal_salary) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }
    public static String Comma(String num) {
        int inValues = Integer.parseInt(num);
        DecimalFormat Commas = new DecimalFormat("#,###");
        String result_int = (String) Commas.format(inValues);
        return result_int;
    }
}