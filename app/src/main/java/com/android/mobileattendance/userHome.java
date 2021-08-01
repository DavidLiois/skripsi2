package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class userHome extends AppCompatActivity {

    private Button exitBtn;
    private Button attendanceBtn;
    private Button istirahatBtn;
    private Button attendanceInfoBtn;
    private Button leaveBtn;
    private Button profileBtn;
    private TextView name;
    private String usernameTxt;
    private String clock_in_date;
    private String clock_out_date;
    private String clock_in_time;
    private String clock_out_time;
    private String break_date;
    private String break_time;
    private String after_break_time;
    private String after_break_date;
    private String present_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        usernameTxt = getIntent().getStringExtra("username");
        clock_in_date = getIntent().getStringExtra("clock_in_date");
        clock_out_date = getIntent().getStringExtra("clock_out_date");
        clock_in_time = getIntent().getStringExtra("clock_in_time");
        clock_out_time = getIntent().getStringExtra("clock_out_time");
        break_date = getIntent().getStringExtra("break_date");
        break_time = getIntent().getStringExtra("break_time");
        after_break_time = getIntent().getStringExtra("after_break_time");
        after_break_date = getIntent().getStringExtra("after_break_date");
        present_intent = getIntent().getStringExtra("present_intent");

        exitBtn = findViewById(R.id.exitBtn);
        attendanceBtn = findViewById(R.id.attendanceBtn);
        istirahatBtn = findViewById(R.id.istirahatBtn);
        attendanceInfoBtn = findViewById(R.id.attendanceInfoBtn);
        leaveBtn = findViewById(R.id.leaveBtn);
        profileBtn = findViewById(R.id.profileBtn);
        name = findViewById(R.id.name);

        name.setText(usernameTxt);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitBtn();
            }
        });

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceBtn();
            }
        });

        istirahatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                istirahatBtn();
            }
        });

        attendanceInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceInfoBtn();
            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveBtn();
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileBtn();
            }
        });
    }

    private void exitBtn() {
        Toast.makeText(userHome.this, "Exit Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(userHome.this, login.class);
        startActivity(login);
        finish();
    }

    private void attendanceBtn() {
        Intent attendanceBtn = new Intent(userHome.this, attendance.class);
        attendanceBtn.putExtra("username",usernameTxt);
        attendanceBtn.putExtra("clock_in_date",clock_in_date);
        attendanceBtn.putExtra("clock_out_date",clock_out_date);
        attendanceBtn.putExtra("clock_in_time",clock_in_time);
        attendanceBtn.putExtra("clock_out_time",clock_out_time);
        attendanceBtn.putExtra("break_date",break_date);
        attendanceBtn.putExtra("after_break_date",after_break_date);
        attendanceBtn.putExtra("break_time",break_time);
        attendanceBtn.putExtra("after_break_time",after_break_time);
        attendanceBtn.putExtra("present_intent",present_intent);
        startActivity(attendanceBtn);
        finish();
    }

    private void istirahatBtn() {
        Intent istirahatBtn = new Intent(userHome.this, istirahat.class);
        istirahatBtn.putExtra("username",usernameTxt);
        istirahatBtn.putExtra("clock_in_date",clock_in_date);
        istirahatBtn.putExtra("clock_out_date",clock_out_date);
        istirahatBtn.putExtra("clock_in_time",clock_in_time);
        istirahatBtn.putExtra("clock_out_time",clock_out_time);
        istirahatBtn.putExtra("break_date",break_date);
        istirahatBtn.putExtra("after_break_date",after_break_date);
        istirahatBtn.putExtra("break_time",break_time);
        istirahatBtn.putExtra("after_break_time",after_break_time);
        istirahatBtn.putExtra("present_intent",present_intent);
        startActivity(istirahatBtn);
        finish();
    }

    private void attendanceInfoBtn() {
        Intent attendanceInfoBtn = new Intent(userHome.this, attendanceInfo.class);
        attendanceInfoBtn.putExtra("username",usernameTxt);
        attendanceInfoBtn.putExtra("clock_in_date",clock_in_date);
        attendanceInfoBtn.putExtra("clock_out_date",clock_out_date);
        attendanceInfoBtn.putExtra("clock_in_time",clock_in_time);
        attendanceInfoBtn.putExtra("clock_out_time",clock_out_time);
        attendanceInfoBtn.putExtra("break_date",break_date);
        attendanceInfoBtn.putExtra("after_break_date",after_break_date);
        attendanceInfoBtn.putExtra("break_time",break_time);
        attendanceInfoBtn.putExtra("after_break_time",after_break_time);
        attendanceInfoBtn.putExtra("present_intent",present_intent);
        startActivity(attendanceInfoBtn);
        finish();
    }

    private void leaveBtn() {
        Intent leaveBtn = new Intent(userHome.this, leave.class);
        leaveBtn.putExtra("username",usernameTxt);
        leaveBtn.putExtra("clock_in_date",clock_in_date);
        leaveBtn.putExtra("clock_out_date",clock_out_date);
        leaveBtn.putExtra("clock_in_time",clock_in_time);
        leaveBtn.putExtra("clock_out_time",clock_out_time);
        leaveBtn.putExtra("break_date",break_date);
        leaveBtn.putExtra("after_break_date",after_break_date);
        leaveBtn.putExtra("break_time",break_time);
        leaveBtn.putExtra("after_break_time",after_break_time);
        leaveBtn.putExtra("present_intent",present_intent);
        startActivity(leaveBtn);
        finish();
    }

    private void profileBtn() {
        Intent profileBtn = new Intent(userHome.this, profile.class);
        profileBtn.putExtra("username",usernameTxt);
        profileBtn.putExtra("clock_in_date",clock_in_date);
        profileBtn.putExtra("clock_out_date",clock_out_date);
        profileBtn.putExtra("clock_in_time",clock_in_time);
        profileBtn.putExtra("clock_out_time",clock_out_time);
        profileBtn.putExtra("break_date",break_date);
        profileBtn.putExtra("after_break_date",after_break_date);
        profileBtn.putExtra("break_time",break_time);
        profileBtn.putExtra("after_break_time",after_break_time);
        profileBtn.putExtra("present_intent",present_intent);
        startActivity(profileBtn);
        finish();
    }
}