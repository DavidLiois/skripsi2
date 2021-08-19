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

public class updatePwAdapter extends BaseAdapter {
    Activity activity;
    List<User> item;
    private LayoutInflater inflater;
    private String temp;

    public updatePwAdapter(Activity activity, List<User> item) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.update_pw_list, null);

        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView fullname = (TextView) convertView.findViewById(R.id.fullname);
        TextView jabatan = (TextView) convertView.findViewById(R.id.jabatan);
        TextView division = (TextView) convertView.findViewById(R.id.division);
        Button update = (Button) convertView.findViewById(R.id.updateBtn);

        User user = item.get(position);

        username.setText(user.getUsername());
        temp = user.getTemp();
        fullname.setText(user.getFullname());
        jabatan.setText(user.getJabatan());
        division.setText(user.getDivisi());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminHome = new Intent(activity, updatePassword2.class);
                adminHome.putExtra("fullname", temp);
                adminHome.putExtra("username",user.getUsername());
                activity.startActivity(adminHome);
                activity.finish();
            }
        });

        return convertView;
    }
}
