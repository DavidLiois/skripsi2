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

public class PresensiAdapter extends BaseAdapter {

    Activity activity;
    List<Data> item;
    private LayoutInflater inflater;
    private String fullnames;

    public PresensiAdapter(Activity activity, List<Data> item) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.list, null);

        TextView fullname = (TextView) convertView.findViewById(R.id.fullname);
        TextView jabatan = (TextView) convertView.findViewById(R.id.jabatan);
        TextView division = (TextView) convertView.findViewById(R.id.division);
        TextView present = (TextView) convertView.findViewById(R.id.present);
        TextView absent = (TextView) convertView.findViewById(R.id.absent);
        TextView cuti = (TextView) convertView.findViewById(R.id.cuti);
        Button attendanceInfoBtn = (Button) convertView.findViewById(R.id.attendanceInfoBtn);
        Button leaveInfoBtn = (Button) convertView.findViewById(R.id.leaveInfoBtn);

        Data data = item.get(position);

        String StaffId = data.getStaffId();
        fullnames = data.getTemp();
        fullname.setText(data.getFullname());
        jabatan.setText(data.getPosition());
        division.setText(data.getDivision());
        present.setText(data.getPresent());
        absent.setText(data.getAbsent());
        cuti.setText(data.getCuti());

        attendanceInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminHome = new Intent(activity, employeeInfoAttendance.class);
                adminHome.putExtra("StaffId", StaffId);
                adminHome.putExtra("fullname", fullnames);
                activity.startActivity(adminHome);
                activity.finish();
            }
        });
        leaveInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminHome = new Intent(activity, employeeInfoLeave.class);
                adminHome.putExtra("StaffId", StaffId);
                adminHome.putExtra("fullname", fullnames);
                activity.startActivity(adminHome);
                activity.finish();
            }
        });

        return convertView;
    }
}
