package com.android.mobileattendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class attendanceinfoAdapter extends BaseAdapter {

    Activity activity;
    List<attendanceinfomodel> item;
    private LayoutInflater inflater;

    public attendanceinfoAdapter(Activity activity, List<attendanceinfomodel> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.list_attendanceinfo, null);

        TextView date2 = (TextView) convertView.findViewById(R.id.Date2);
        TextView clockIn = (TextView) convertView.findViewById(R.id.clockIn);
        TextView clockOut = (TextView) convertView.findViewById(R.id.clockOut);
        TextView istirahat = (TextView) convertView.findViewById(R.id.istirahat);
        TextView afterBreak = (TextView) convertView.findViewById(R.id.afterBreak);

        attendanceinfomodel data = item.get(position);

        date2.setText(data.getDate2());
        clockIn.setText(data.getJamDatang());
        clockOut.setText(data.getJamPulang());
        istirahat.setText(data.getMulaiIstirahat());
        afterBreak.setText(data.getSelesaiIstirahat());

        return convertView;
    }
}
