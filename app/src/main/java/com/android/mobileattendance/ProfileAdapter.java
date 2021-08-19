package com.android.mobileattendance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProfileAdapter extends BaseAdapter {
    Activity activity;
    List<User> item;
    private LayoutInflater inflater;

    public ProfileAdapter(Activity activity, List<User> item) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.activity_profile, null);

        TextView fullname = (TextView) convertView.findViewById(R.id.fullname);
        TextView jabatan = (TextView) convertView.findViewById(R.id.jabatan);
        TextView division = (TextView) convertView.findViewById(R.id.division);
        TextView usernote = (TextView) convertView.findViewById(R.id.usernote);
        TextView gender = (TextView) convertView.findViewById(R.id.gender);
        TextView pob = (TextView) convertView.findViewById(R.id.pob);
        TextView dob = (TextView) convertView.findViewById(R.id.dob);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);
        TextView phonenumber = (TextView) convertView.findViewById(R.id.phonenumber);
        TextView email = (TextView) convertView.findViewById(R.id.email);

        User user = item.get(position);

        fullname.setText(user.getFullname());
        jabatan.setText(user.getJabatan());
        division.setText(user.getDivisi());
        usernote.setText(user.getUsernote());
        gender.setText(user.getJeniskelamin());
        pob.setText(user.getPob());
        dob.setText(user.getDob());
        alamat.setText(user.getAlamat());
        phonenumber.setText(user.getPhonenumber());
        email.setText(user.getEmail());

        return convertView;
    }
}
