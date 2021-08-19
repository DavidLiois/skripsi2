package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class attendanceInfo extends AppCompatActivity {

    private Button exitBtn;
    private Button backBtn;
    private LinearLayout linearLayout;
    private TextView currentDateTxt;
    private TextView clockIn;
    private TextView clockOut;
    private TextView istirahat;
    private TextView afterBreak;
    private String clock_in_date;
    private String clock_out_date;
    private String clock_in_time;
    private String clock_out_time;
    private String break_date;
    private String break_time;
    private String after_break_time;
    private String after_break_date;
    private String present_intent;
    private String id;
    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_info);

        clock_in_date = getIntent().getStringExtra("clock_in_date");
        clock_out_date = getIntent().getStringExtra("clock_out_date");
        clock_in_time = getIntent().getStringExtra("clock_in_time");
        clock_out_time = getIntent().getStringExtra("clock_out_time");
        break_date = getIntent().getStringExtra("break_date");
        break_time = getIntent().getStringExtra("break_time");
        after_break_time = getIntent().getStringExtra("after_break_time");
        after_break_date = getIntent().getStringExtra("after_break_date");
        present_intent = getIntent().getStringExtra("present_intent");
        id = getIntent().getStringExtra("id");
        fullname = getIntent().getStringExtra("fullname");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        linearLayout = findViewById(R.id.linearLayout);
        currentDateTxt = findViewById(R.id.currentDateTxt);
        clockIn = findViewById(R.id.clockIn);
        clockOut = findViewById(R.id.clockOut);
        istirahat = findViewById(R.id.istirahat);
        afterBreak = findViewById(R.id.afterBreak);

        currentDateTxt.setText(clock_in_date);
        clockIn.setText(clock_in_time);
        clockOut.setText(clock_out_time);
        istirahat.setText(break_time);
        afterBreak.setText(after_break_time);

        if (currentDateTxt.getText().equals("") | currentDateTxt.getText().equals("-")){
            linearLayout.setVisibility(View.INVISIBLE);
        }else{
            linearLayout.setVisibility(View.VISIBLE);
        }

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitBtn();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBtn();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(attendanceInfo.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(attendanceInfo.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(attendanceInfo.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent userHome = new Intent(attendanceInfo.this, userHome.class);
        userHome.putExtra("clock_in_date",clock_in_date);
        userHome.putExtra("clock_out_date",clock_out_date);
        userHome.putExtra("clock_in_time",clock_in_time);
        userHome.putExtra("clock_out_time",clock_out_time);
        userHome.putExtra("break_date",break_date);
        userHome.putExtra("after_break_date",after_break_date);
        userHome.putExtra("break_time",break_time);
        userHome.putExtra("after_break_time",after_break_time);
        userHome.putExtra("present_intent",present_intent);
        userHome.putExtra("id",id);
        userHome.putExtra("fullname",fullname);
        startActivity(userHome);
        finish();
    }
}