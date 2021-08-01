package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class adminHome extends AppCompatActivity {

    private Button exitBtn;
    private Button addEmployeeBtn;
    private Button employeeInfoBtn;
    private Button updateProfileBtn;
    private Button deleteProfileBtn;
    private Button updatePasswordBtn;
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
        setContentView(R.layout.activity_admin_home);

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
        addEmployeeBtn = findViewById(R.id.addEmployeeBtn);
        employeeInfoBtn = findViewById(R.id.employeeInfoBtn);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        deleteProfileBtn = findViewById(R.id.deleteProfileBtn);
        updatePasswordBtn = findViewById(R.id.updatePasswordBtn);
        name = findViewById(R.id.name);

        name.setText(usernameTxt);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitBtn();
            }
        });

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployeeBtn();
            }
        });

        employeeInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeInfoBtn();
            }
        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileBtn();
            }
        });

        deleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfileBtn();
            }
        });

        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePasswordBtn();
            }
        });
    }

    private void exitBtn() {
        Toast.makeText(adminHome.this, "Exit Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(adminHome.this, login.class);
        startActivity(login);
        finish();
    }

    private void addEmployeeBtn() {
        Intent addEmployee = new Intent(adminHome.this, addEmployee.class);
        addEmployee.putExtra("username",usernameTxt);
        addEmployee.putExtra("clock_in_date",clock_in_date);
        addEmployee.putExtra("clock_out_date",clock_out_date);
        addEmployee.putExtra("clock_in_time",clock_in_time);
        addEmployee.putExtra("clock_out_time",clock_out_time);
        addEmployee.putExtra("break_date",break_date);
        addEmployee.putExtra("after_break_date",after_break_date);
        addEmployee.putExtra("break_time",break_time);
        addEmployee.putExtra("after_break_time",after_break_time);
        addEmployee.putExtra("presen_intent",present_intent);
        startActivity(addEmployee);
        finish();
    }

    private void employeeInfoBtn() {
        Intent employeeInfoBtn = new Intent(adminHome.this, employeeInfo.class);
        employeeInfoBtn.putExtra("username",usernameTxt);
        employeeInfoBtn.putExtra("clock_in_date",clock_in_date);
        employeeInfoBtn.putExtra("clock_out_date",clock_out_date);
        employeeInfoBtn.putExtra("clock_in_time",clock_in_time);
        employeeInfoBtn.putExtra("clock_out_time",clock_out_time);
        employeeInfoBtn.putExtra("break_date",break_date);
        employeeInfoBtn.putExtra("after_break_date",after_break_date);
        employeeInfoBtn.putExtra("break_time",break_time);
        employeeInfoBtn.putExtra("after_break_time",after_break_time);
        employeeInfoBtn.putExtra("present_intent",present_intent);
        startActivity(employeeInfoBtn);
        finish();
    }

    private void updateProfileBtn() {
        Intent updateProfileBtn = new Intent(adminHome.this, updateProfile.class);
        updateProfileBtn.putExtra("username",usernameTxt);
        updateProfileBtn.putExtra("clock_in_date",clock_in_date);
        updateProfileBtn.putExtra("clock_out_date",clock_out_date);
        updateProfileBtn.putExtra("clock_in_time",clock_in_time);
        updateProfileBtn.putExtra("clock_out_time",clock_out_time);
        updateProfileBtn.putExtra("break_date",break_date);
        updateProfileBtn.putExtra("after_break_date",after_break_date);
        updateProfileBtn.putExtra("break_time",break_time);
        updateProfileBtn.putExtra("after_break_time",after_break_time);
        updateProfileBtn.putExtra("present_intent",present_intent);
        startActivity(updateProfileBtn);
        finish();
    }

    private void deleteProfileBtn() {
        Intent deleteProfileBtn = new Intent(adminHome.this, deleteProfile.class);
        deleteProfileBtn.putExtra("username",usernameTxt);
        deleteProfileBtn.putExtra("clock_in_date",clock_in_date);
        deleteProfileBtn.putExtra("clock_out_date",clock_out_date);
        deleteProfileBtn.putExtra("clock_in_time",clock_in_time);
        deleteProfileBtn.putExtra("clock_out_time",clock_out_time);
        deleteProfileBtn.putExtra("break_date",break_date);
        deleteProfileBtn.putExtra("after_break_date",after_break_date);
        deleteProfileBtn.putExtra("break_time",break_time);
        deleteProfileBtn.putExtra("after_break_time",after_break_time);
        deleteProfileBtn.putExtra("present_intent",present_intent);
        startActivity(deleteProfileBtn);
        finish();
    }

    private void updatePasswordBtn() {
        Intent updatePasswordAdminBtn = new Intent(adminHome.this, updatePassword.class);
        updatePasswordAdminBtn.putExtra("username",usernameTxt);
        updatePasswordAdminBtn.putExtra("clock_in_date",clock_in_date);
        updatePasswordAdminBtn.putExtra("clock_out_date",clock_out_date);
        updatePasswordAdminBtn.putExtra("clock_in_time",clock_in_time);
        updatePasswordAdminBtn.putExtra("clock_out_time",clock_out_time);
        updatePasswordAdminBtn.putExtra("break_date",break_date);
        updatePasswordAdminBtn.putExtra("after_break_date",after_break_date);
        updatePasswordAdminBtn.putExtra("break_time",break_time);
        updatePasswordAdminBtn.putExtra("after_break_time",after_break_time);
        updatePasswordAdminBtn.putExtra("present_intent",present_intent);
        startActivity(updatePasswordAdminBtn);
        finish();
    }
}