package com.android.mobileattendance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ImageView image = (ImageView) convertView.findViewById(R.id.image_view);

        EIleave leave = item.get(position);

        startDate.setText(leave.getStartDate());
        endDate.setText(leave.getEndDate());
        reason.setText(leave.getReason());

        String photo = leave.getImage();

        if (!"".equals(photo)) {
            byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
            Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image.setImageBitmap(imgBitMap);
            image.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
