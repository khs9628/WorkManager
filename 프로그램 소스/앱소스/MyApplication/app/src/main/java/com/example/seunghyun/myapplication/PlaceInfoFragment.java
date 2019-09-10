package com.example.seunghyun.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceInfoFragment extends Fragment {
    TextView tName,tPlaceName, tSalType, tStdSalary, tSalDate, tEntryDate, tWorkDay, tStartTime,tEndTime, tRestDay, tHoliday, tRestTime, tIsMoreExtra;
    String id, sendmsg, oj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_place_info, container, false);

        tName = view.findViewById(R.id.name);
        tPlaceName = view.findViewById(R.id.place_name);
        tSalType = view.findViewById(R.id.sal_type);
        tStdSalary = view.findViewById(R.id.std_salary);
        tWorkDay = view.findViewById(R.id.work_day);
        tStartTime = view.findViewById(R.id.start_time);
        tEndTime = view.findViewById(R.id.end_time);
        tRestDay = view.findViewById(R.id.rest_day);
        tHoliday = view.findViewById(R.id.holiday);
        tEntryDate = view.findViewById(R.id.entry_day);
        tSalDate = view.findViewById(R.id.sal_date);
        tRestTime = view.findViewById(R.id.rest_time);
        tIsMoreExtra = view.findViewById(R.id.is_more_extra);

        id = SharedPreference.getAttribute(getContext(),"userId");

        sendmsg = "hireInfo_list";    //이름
        try {
            String result = new Task(sendmsg).execute(id, "hireInfo_list").get();//디비값을 가져오기
            oj = new String(result);

            String[] element = oj.split("/");
            tName.setText(element[0].trim());
            tPlaceName.setText(element[1].trim());
            tSalType.setText(element[2].trim());
            tStdSalary.setText(element[3].trim()+" 원");
            tWorkDay.setText(element[4].trim());
            tStartTime.setText(element[5].trim());
            tEndTime.setText(element[6].trim());
            tRestDay.setText(element[7].trim()+"요일");
            tHoliday.setText(element[8].trim()+"일");
            tEntryDate.setText(element[9].trim());
            tSalDate.setText(element[10].trim());
            tRestTime.setText(element[11].trim()+"분");
            if(element[12].trim().equals("1"))tIsMoreExtra.setText("有".trim());
            else tIsMoreExtra.setText("無".trim());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_place_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_place_info) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }
}