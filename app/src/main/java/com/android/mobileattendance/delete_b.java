package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class delete_b extends AppCompatActivity {

    private Button yesBtn;
    private Button noBtn;
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
        setContentView(R.layout.activity_delete_b);

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

        yesBtn = findViewById(R.id.yes);
        noBtn = findViewById(R.id.no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesBtn();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noBtn();
            }
        });
    }

    private void yesBtn() {
        Toast.makeText(delete_b.this, "Delete Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(delete_b.this, deleteProfile.class);
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

    private void noBtn() {
        Intent login = new Intent(delete_b.this, delete_a.class);
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