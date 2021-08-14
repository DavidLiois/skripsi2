package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class employeeinfo_b extends AppCompatActivity {

    private Button exitBtn;
    private Button backBtn;
    private LinearLayout linearLayout;
    private TextView currentDateTxt;
    private TextView clockIn;
    private TextView clockOut;
    private TextView istirahat;
    private TextView afterBreak;
    private String usernameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeeinfo_b);

        usernameTxt = getIntent().getStringExtra("username");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        linearLayout = findViewById(R.id.linearLayout);
        currentDateTxt = findViewById(R.id.currentDateTxt);
        clockIn = findViewById(R.id.clockIn);
        clockOut = findViewById(R.id.clockOut);
        istirahat = findViewById(R.id.istirahat);
        afterBreak = findViewById(R.id.afterBreak);

        currentDateTxt.setText("30 July 2021");
        clockIn.setText("08:17");
        clockOut.setText("17:43");
        istirahat.setText("12:03");
        afterBreak.setText("12:58");

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

    private void exitBtn() {
        Toast.makeText(employeeinfo_b.this, "Exit Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(employeeinfo_b.this, login.class);
        startActivity(login);
        finish();
    }

    private void backBtn() {
        if (usernameTxt.equals("Admin")) {
            Intent adminHome = new Intent(employeeinfo_b.this, employeeinfo_a.class);
            adminHome.putExtra("username", usernameTxt);
            startActivity(adminHome);
            finish();
        } else {
            Intent userHome = new Intent(employeeinfo_b.this, employeeinfo_a.class);
            userHome.putExtra("username", usernameTxt);
            startActivity(userHome);
            finish();
        }
    }
}