package com.example.seunghyun.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ByPeriodResultActivity extends AppCompatActivity {

    private ListView m_oListView = null;

    TextView tPlace, tSalType, tStartDate, tEndDate, tTotalTime, tLate, tAbsent, tEarly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_period_result);

        String msg =SharedPreference.getAttribute(getApplicationContext(), "byPeriod");
        String element[] = msg.split("-");

        tPlace = (TextView)findViewById(R.id.place);
        tSalType =(TextView)findViewById(R.id.sal_type);
        tStartDate=(TextView)findViewById(R.id.start_date);
        tEndDate = (TextView)findViewById(R.id.end_date);
        tTotalTime=(TextView)findViewById(R.id.total_time);
        tLate = (TextView)findViewById(R.id.late);
        tAbsent = (TextView)findViewById(R.id.absent);
        tEarly = (TextView)findViewById(R.id.early);

        int size = Integer.parseInt(element[0]);
        String place = element[1];
        String salType = element[2];
        String startDate = element[3];
        String endDate = element[4];
        String totalTime = element[5];
        String late = element[6];
        String absent = element[7];
        String early = element[8];
        String[] date =new String [size];
        String[] day = new String [size];
        String[] dateDay = new String[size];
        String[] startTime = new String [size];
        String[] endTime = new String [size];
        String[] dailyTime = new String [size];

        int i, n=9;
        for(i=0;i<size;i++){
            date[i] = element[n].substring(0,2)+"/"+element[n++].substring(2,4)+" ";
        }
        for(i=0;i<size;i++){
            day[i] = element[n++];
        }
        for(i=0;i<size;i++){
            dateDay[i] =date[i]+"\t"+day[i];
        }
        for(i=0;i<size;i++){
            startTime[i]  = element[n].substring(0,2)+":"+element[n++].substring(2,4);
        }
        for(i=0;i<size;i++){
            endTime[i] = element[n].substring(0,2)+":"+element[n++].substring(2,4);
        }

        int intDailyTime, intDailyMin;
        for(i=0;i<size;i++){
            dailyTime[i]= element[n++];
            if(Double.parseDouble(dailyTime[i])%1 != 0){
                intDailyTime = (int)(Double.parseDouble(dailyTime[i]))/1;
                intDailyMin = (int) ((Double.parseDouble(dailyTime[i]))%1*60);
                dailyTime[i]= Integer.toString(intDailyTime) + ":" + Integer.toString(intDailyMin);
            }
            else{
                intDailyTime = (int)(Double.parseDouble(dailyTime[i]))/1;
                dailyTime[i]= Integer.toString(intDailyTime) + ":00";
            }
        }
        tPlace.setText(place);
        tSalType.setText(salType);
        tStartDate.setText(startDate);
        tEndDate.setText(endDate);
        tLate.setText(late);
        tAbsent.setText(absent);
        tEarly.setText(early);

        double douTotalTime = Double.parseDouble(totalTime);
        int intTotalTIme = (int)(douTotalTime/1);
        int intTotalMin = (int)(douTotalTime%1*60);

        if(douTotalTime%1 != 0){
            totalTime=Integer.toString(intTotalTIme)+"시간 "+ Integer.toString(intTotalMin)+"분";
        }
        else totalTime = Integer.toString(intTotalTIme)+"시간";

        tTotalTime.setText(totalTime);

        ArrayList<ItemData> oData = new ArrayList<>();
        for (i=0; i<size; ++i)
        {
            ItemData oItem = new ItemData();
            oItem.dateDay = dateDay[i];
            oItem.startTime = startTime[i];
            oItem.endTime = endTime[i];
            oItem.dailyTime=dailyTime[i];
            oData.add(oItem);
            //if (nDatCnt >= strDate.length) nDatCnt = 0;
        }

        // ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)findViewById(R.id.listView);
        ListAdapter oAdapter = new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

        Button btnSendComplete = (Button)findViewById(R.id.complete);
        btnSendComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}