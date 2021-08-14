package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updatepassword_a extends AppCompatActivity {

    private Button exitBtn;
    private Button backBtn;
    private Button searchBtn;
    private Button updatepasswordbtn;
    private EditText search;
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
        setContentView(R.layout.activity_updatepassword_a);

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
        searchBtn = findViewById(R.id.searchBtn);
        updatepasswordbtn = findViewById(R.id.updatepasswordbtn);
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

        updatepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepasswordbtn();
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
        Toast.makeText(updatepassword_a.this, "Exit Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(updatepassword_a.this, login.class);
        startActivity(login);
        finish();
    }

    private void updatepasswordbtn() {
        Intent login = new Intent(updatepassword_a.this, updatepassword_b.class);
        login.putExtra("username", usernameTxt);
        login.putExtra("clock_in_date", clock_in_date);
        login.putExtra("clock_out_date", clock_out_date);
        login.putExtra("clock_in_time", clock_in_time);
        login.putExtra("clock_out_time", clock_out_time);
        login.putExtra("break_date", break_date);
        login.putExtra("after_break_date", after_break_date);
        login.putExtra("break_time", break_time);
        login.putExtra("after_break_time", after_break_time);
        login.putExtra("present_intent",present_intent);
        startActivity(login);
        finish();
    }

    private void searchBtn() {
        String strsearch = search.getText().toString();
        if (strsearch.equals("user2")){
            Intent login = new Intent(updatepassword_a.this, updatepassword_a.class);
            login.putExtra("username", usernameTxt);
            login.putExtra("clock_in_date", clock_in_date);
            login.putExtra("clock_out_date", clock_out_date);
            login.putExtra("clock_in_time", clock_in_time);
            login.putExtra("clock_out_time", clock_out_time);
            login.putExtra("break_date", break_date);
            login.putExtra("after_break_date", after_break_date);
            login.putExtra("break_time", break_time);
            login.putExtra("after_break_time", after_break_time);
            login.putExtra("present_intent",present_intent);
            startActivity(login);
            finish();
        }else{
            Intent login = new Intent(updatepassword_a.this, updatepassword_c.class);
            login.putExtra("username", usernameTxt);
            login.putExtra("clock_in_date", clock_in_date);
            login.putExtra("clock_out_date", clock_out_date);
            login.putExtra("clock_in_time", clock_in_time);
            login.putExtra("clock_out_time", clock_out_time);
            login.putExtra("break_date", break_date);
            login.putExtra("after_break_date", after_break_date);
            login.putExtra("break_time", break_time);
            login.putExtra("after_break_time", after_break_time);
            login.putExtra("present_intent",present_intent);
            startActivity(login);
            finish();
        }
    }

    private void backBtn() {
        if (usernameTxt.equals("Admin")) {
            Intent adminHome = new Intent(updatepassword_a.this, adminHome.class);
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
            Intent userHome = new Intent(updatepassword_a.this, userHome.class);
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