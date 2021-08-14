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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        usernameTxt = getIntent().getStringExtra("username");

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
        startActivity(addEmployee);
        finish();
    }

    private void employeeInfoBtn() {
        Intent employeeInfoBtn = new Intent(adminHome.this, employeeInfo.class);
        employeeInfoBtn.putExtra("username",usernameTxt);
        startActivity(employeeInfoBtn);
        finish();
    }

    private void updateProfileBtn() {
        Intent updateProfileBtn = new Intent(adminHome.this, updateProfile.class);
        updateProfileBtn.putExtra("username",usernameTxt);
        startActivity(updateProfileBtn);
        finish();
    }

    private void deleteProfileBtn() {
        Intent deleteProfileBtn = new Intent(adminHome.this, deleteProfile.class);
        deleteProfileBtn.putExtra("username",usernameTxt);
        startActivity(deleteProfileBtn);
        finish();
    }

    private void updatePasswordBtn() {
        Intent updatePasswordAdminBtn = new Intent(adminHome.this, updatePassword.class);
        updatePasswordAdminBtn.putExtra("username",usernameTxt);
        startActivity(updatePasswordAdminBtn);
        finish();
    }
}