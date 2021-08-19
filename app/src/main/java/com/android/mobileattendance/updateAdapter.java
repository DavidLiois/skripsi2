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

public class updateAdapter extends BaseAdapter {
    Activity activity;
    List<User> item;
    private LayoutInflater inflater;
    private String temp;

    public updateAdapter(Activity activity, List<User> item) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.update_list, null);

        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView fullname = (TextView) convertView.findViewById(R.id.fullname);
        TextView jabatan = (TextView) convertView.findViewById(R.id.jabatan);
        TextView division = (TextView) convertView.findViewById(R.id.division);
        Button update = (Button) convertView.findViewById(R.id.updateBtn);

        User user = item.get(position);

        String usernames = user.getUsername();
        String positions = user.getJabatan();
        String divisions = user.getDivisi();
        String fullnames = user.getFullname();
        String pobs = user.getPob();
        String dobs = user.getDob();
        String address = user.getAlamat();
        String phonenumbers = user.getPhonenumber();
        String email = user.getEmail();
        temp = user.getTemp();

        username.setText(usernames);
        fullname.setText(fullnames);
        jabatan.setText(positions);
        division.setText(divisions);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminHome = new Intent(activity, updateProfile2.class);
                adminHome.putExtra("username", usernames);
                adminHome.putExtra("jabatan",positions);
                adminHome.putExtra("divisi",divisions);
                adminHome.putExtra("fullname",fullnames);
                adminHome.putExtra("pob",pobs);
                adminHome.putExtra("dob",dobs);
                adminHome.putExtra("address",address);
                adminHome.putExtra("phonenumber",phonenumbers);
                adminHome.putExtra("email",email);
                adminHome.putExtra("temp", temp);
                activity.startActivity(adminHome);
                activity.finish();
            }
        });

        return convertView;
    }
}
