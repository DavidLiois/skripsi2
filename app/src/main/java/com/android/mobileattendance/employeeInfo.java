package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class employeeInfo extends AppCompatActivity {

    private Button exitBtn;
    private Button backBtn;
    private LinearLayout linearLayout;
    private LinearLayout userLinearLayout;
    private TextView currentDateTxt;
    private TextView clockIn;
    private TextView clockOut;
    private TextView istirahat;
    private TextView afterBreak;
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
        setContentView(R.layout.activity_employee_info);

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
        backBtn = findViewById(R.id.backBtn);
        linearLayout = findViewById(R.id.linearLayout);
        userLinearLayout = findViewById(R.id.userLinearLayout);
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

        userLinearLayout.setVisibility(View.INVISIBLE);

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

    private void exitBtn() {
        Toast.makeText(employeeInfo.this, "Exit Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(employeeInfo.this, login.class);
        startActivity(login);
        finish();
    }

    private void backBtn() {
        if (usernameTxt.equals("Admin")) {
            Intent adminHome = new Intent(employeeInfo.this, adminHome.class);
            adminHome.putExtra("username", usernameTxt);
            adminHome.putExtra("clock_in_date", clock_in_date);
            adminHome.putExtra("clock_out_date", clock_out_date);
            adminHome.putExtra("clock_in_time", clock_in_time);
            adminHome.putExtra("clock_out_time", clock_out_time);
            adminHome.putExtra("break_date", break_date);
            adminHome.putExtra("after_break_date", after_break_date);
            adminHome.putExtra("break_time", break_time);
            adminHome.putExtra("after_break_time", after_break_time);
            adminHome.putExtra("present_intent",present_intent);
            startActivity(adminHome);
            finish();
        } else {
            Intent userHome = new Intent(employeeInfo.this, userHome.class);
            userHome.putExtra("username", usernameTxt);
            userHome.putExtra("clock_in_date", clock_in_date);
            userHome.putExtra("clock_out_date", clock_out_date);
            userHome.putExtra("clock_in_time", clock_in_time);
            userHome.putExtra("clock_out_time", clock_out_time);
            userHome.putExtra("break_date", break_date);
            userHome.putExtra("after_break_date", after_break_date);
            userHome.putExtra("break_time", break_time);
            userHome.putExtra("after_break_time", after_break_time);
            userHome.putExtra("present_intent",present_intent);
            startActivity(userHome);
            finish();
        }
    }
}