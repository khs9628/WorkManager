package com.example.seunghyun.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SeungHyun on 2018-11-05.
 */

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ItemData> _oData)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView tDate = (TextView) convertView.findViewById(R.id.date);
        TextView tStime = (TextView) convertView.findViewById(R.id.stime);
        TextView tEtime = (TextView) convertView.findViewById(R.id.etime);
        TextView tDailyTime = (TextView) convertView.findViewById(R.id.daily_time);

        tDate.setText(m_oData.get(position).dateDay);
        tStime.setText(m_oData.get(position).startTime);
        tEtime.setText((m_oData.get(position).endTime));
        tDailyTime.setText(m_oData.get(position).dailyTime);
        return convertView;
    }
}

