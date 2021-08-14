package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class employeeInfo extends AppCompatActivity {

    private Button exitBtn;
    private Button backBtn;
    private Button searchBtn;
    private EditText search;
    private String usernameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        usernameTxt = getIntent().getStringExtra("username");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        searchBtn = findViewById(R.id.searchBtn);
        search = findViewById(R.id.search);

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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBtn();
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
            startActivity(adminHome);
            finish();
        } else {
            Intent userHome = new Intent(employeeInfo.this, userHome.class);
            userHome.putExtra("username", usernameTxt);
            startActivity(userHome);
            finish();
        }
    }

    private void searchBtn() {
        String strsearch = search.getText().toString();
        if (strsearch.equals("user2")){
            Intent login = new Intent(employeeInfo.this, employeeinfo_a.class);
            login.putExtra("username", usernameTxt);
            startActivity(login);
            finish();
        }else{
            Intent login = new Intent(employeeInfo.this, employeeinfo_c.class);
            login.putExtra("username", usernameTxt);
            startActivity(login);
            finish();
        }
    }
}