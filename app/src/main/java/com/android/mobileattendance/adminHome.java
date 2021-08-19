package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        exitBtn = findViewById(R.id.exitBtn);
        addEmployeeBtn = findViewById(R.id.addEmployeeBtn);
        employeeInfoBtn = findViewById(R.id.employeeInfoBtn);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        deleteProfileBtn = findViewById(R.id.deleteProfileBtn);
        updatePasswordBtn = findViewById(R.id.updatePasswordBtn);
        name = findViewById(R.id.name);

        fullname = getIntent().getStringExtra("fullname");

        name.setText("Hello, "+fullname);

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

    @Override
    public void onBackPressed() {
        exitBtn();
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(adminHome.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(adminHome.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(adminHome.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void addEmployeeBtn() {
        Intent addEmployee = new Intent(adminHome.this, addEmployee.class);
        addEmployee.putExtra("fullname",fullname);
        startActivity(addEmployee);
        finish();
    }

    private void employeeInfoBtn() {
        Intent employeeInfoBtn = new Intent(adminHome.this, employeeInfo.class);
        employeeInfoBtn.putExtra("fullname",fullname);
        startActivity(employeeInfoBtn);
        finish();
    }

    private void updateProfileBtn() {
        Intent updateProfileBtn = new Intent(adminHome.this, updateProfile.class);
        updateProfileBtn.putExtra("fullname",fullname);
        startActivity(updateProfileBtn);
        finish();
    }

    private void deleteProfileBtn() {
        Intent deleteProfileBtn = new Intent(adminHome.this, deleteProfile.class);
        deleteProfileBtn.putExtra("fullname",fullname);
        startActivity(deleteProfileBtn);
        finish();
    }

    private void updatePasswordBtn() {
        Intent updatePasswordAdminBtn = new Intent(adminHome.this, updatePassword.class);
        updatePasswordAdminBtn.putExtra("fullname",fullname);
        startActivity(updatePasswordAdminBtn);
        finish();
    }
}