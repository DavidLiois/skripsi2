package com.android.mobileattendance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EIattendanceAdapter extends BaseAdapter {
    Activity activity;
    List<presensi> item;
    private LayoutInflater inflater;

    public EIattendanceAdapter(Activity activity, List<presensi> item) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.list_presensi, null);

        TextView Date = (TextView) convertView.findViewById(R.id.date);
        TextView clockIn = (TextView) convertView.findViewById(R.id.clockIn);
        TextView clockOut = (TextView) convertView.findViewById(R.id.clockOut);
        TextView istirahat = (TextView) convertView.findViewById(R.id.istirahat);
        TextView afterBreak = (TextView) convertView.findViewById(R.id.afterBreak);

        presensi presensi = item.get(position);

        Date.setText(presensi.getDate());
        clockIn.setText(presensi.getClockIn());
        clockOut.setText(presensi.getClockOut());
        istirahat.setText(presensi.getIstirahat());
        afterBreak.setText(presensi.getAfterBreak());

        return convertView;
    }
}
