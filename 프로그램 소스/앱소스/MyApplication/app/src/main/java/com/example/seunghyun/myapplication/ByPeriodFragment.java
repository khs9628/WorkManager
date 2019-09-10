package com.example.seunghyun.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByPeriodFragment extends Fragment {
    EditText eStartDate, eEndDate;
    String id, sendmsg, startDate, endDate, oj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_by_period, container, false);

        eStartDate = view.findViewById(R.id.start_date);
        eEndDate = view.findViewById(R.id.end_date);

        startDate = eStartDate.getText().toString();
        endDate = eEndDate.getText().toString();


        id = SharedPreference.getAttribute(getContext(), "userId");

        Button btnConfirm = view.findViewById(R.id.confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity root = getActivity();

                startDate = eStartDate.getText().toString();
                endDate = eEndDate.getText().toString();

                if(!checkDate(startDate)||!checkDate(endDate)) {
                    Toast.makeText(root, "날짜를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendmsg = "byPeriod_list";    //이름
                    try {
                        String result = new Task(sendmsg).execute(id,startDate, endDate, "byPeriod_list", "startDate_list", "endDate_list").get();
                        oj = new String(result);
                        SharedPreference.setAttribute(getContext(),"byPeriod",oj);

                        Intent intent = new Intent(getActivity(), ByPeriodResultActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_by_period, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_by_period) {
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
}