package com.example.seunghyun.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    EditText AddSchedule;
    String Content, userId, sendmsg, memberId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        AddSchedule = view.findViewById(R.id.addschedule);

        Button RequestAdd = view.findViewById(R.id.requestAdd);
        RequestAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity root = getActivity();

                userId = SharedPreference.getAttribute(getContext(), "userId");
                sendmsg = "calendar_list";    //이름
                try {
                    String result = new Task(sendmsg).execute(userId, "calendar_list").get();//디비값을 가져오기
                    memberId = new String(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Content = AddSchedule.getText().toString();


                if(Content.trim().length()<0){
                    Toast.makeText(root, "날짜와 일정을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendmsg = "calendar_write";
                    try {
                        String rst = new Task(sendmsg).execute(Content, memberId, "calendar_write", "memberId_write").get();
                        Toast.makeText(root, "관리자에게 일정 추가 요청을 하였습니다.", Toast.LENGTH_SHORT).show();
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
        inflater.inflate(R.menu.menu_calendar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_calendar) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }
}
