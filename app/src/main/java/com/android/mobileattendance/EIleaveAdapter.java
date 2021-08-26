package com.android.mobileattendance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EIleaveAdapter extends BaseAdapter {
    Activity activity;
    List<EIleave> item;
    private LayoutInflater inflater;

    public EIleaveAdapter(Activity activity, List<EIleave> item) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.list_leave, null);

        TextView startDate = (TextView) convertView.findViewById(R.id.startDate);
        TextView endDate = (TextView) convertView.findViewById(R.id.endDate);
        TextView reason = (TextView) convertView.findViewById(R.id.reason);

        EIleave leave = item.get(position);

        startDate.setText(leave.getStartDate());
        endDate.setText(leave.getEndDate());
        reason.setText(leave.getReason());

        return convertView;
    }
}
