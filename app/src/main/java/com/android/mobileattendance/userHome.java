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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class userHome extends AppCompatActivity {

    private Button exitBtn;
    private Button attendanceBtn;
    private Button istirahatBtn;
    private Button attendanceInfoBtn;
    private Button leaveBtn;
    private Button profileBtn;
    private TextView name;
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
        setContentView(R.layout.activity_user_home);

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
        attendanceBtn = findViewById(R.id.attendanceBtn);
        istirahatBtn = findViewById(R.id.istirahatBtn);
        attendanceInfoBtn = findViewById(R.id.attendanceInfoBtn);
        leaveBtn = findViewById(R.id.leaveBtn);
        profileBtn = findViewById(R.id.profileBtn);
        name = findViewById(R.id.name);

        name.setText("Hello, "+fullname);

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
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(userHome.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(userHome.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(userHome.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void attendanceBtn() {
        Intent attendanceBtn = new Intent(userHome.this, attendance.class);
        attendanceBtn.putExtra("clock_in_date",clock_in_date);
        attendanceBtn.putExtra("clock_out_date",clock_out_date);
        attendanceBtn.putExtra("clock_in_time",clock_in_time);
        attendanceBtn.putExtra("clock_out_time",clock_out_time);
        attendanceBtn.putExtra("break_date",break_date);
        attendanceBtn.putExtra("after_break_date",after_break_date);
        attendanceBtn.putExtra("break_time",break_time);
        attendanceBtn.putExtra("after_break_time",after_break_time);
        attendanceBtn.putExtra("present_intent",present_intent);
        attendanceBtn.putExtra("id",id);
        attendanceBtn.putExtra("fullname",fullname);
        startActivity(attendanceBtn);
        finish();
    }

    private void istirahatBtn() {
        Intent istirahatBtn = new Intent(userHome.this, istirahat.class);
        istirahatBtn.putExtra("clock_in_date",clock_in_date);
        istirahatBtn.putExtra("clock_out_date",clock_out_date);
        istirahatBtn.putExtra("clock_in_time",clock_in_time);
        istirahatBtn.putExtra("clock_out_time",clock_out_time);
        istirahatBtn.putExtra("break_date",break_date);
        istirahatBtn.putExtra("after_break_date",after_break_date);
        istirahatBtn.putExtra("break_time",break_time);
        istirahatBtn.putExtra("after_break_time",after_break_time);
        istirahatBtn.putExtra("present_intent",present_intent);
        istirahatBtn.putExtra("id",id);
        istirahatBtn.putExtra("fullname",fullname);
        startActivity(istirahatBtn);
        finish();
    }

    private void attendanceInfoBtn() {
        Intent attendanceInfoBtn = new Intent(userHome.this, attendanceInfo.class);
        attendanceInfoBtn.putExtra("clock_in_date",clock_in_date);
        attendanceInfoBtn.putExtra("clock_out_date",clock_out_date);
        attendanceInfoBtn.putExtra("clock_in_time",clock_in_time);
        attendanceInfoBtn.putExtra("clock_out_time",clock_out_time);
        attendanceInfoBtn.putExtra("break_date",break_date);
        attendanceInfoBtn.putExtra("after_break_date",after_break_date);
        attendanceInfoBtn.putExtra("break_time",break_time);
        attendanceInfoBtn.putExtra("after_break_time",after_break_time);
        attendanceInfoBtn.putExtra("present_intent",present_intent);
        attendanceInfoBtn.putExtra("id",id);
        attendanceInfoBtn.putExtra("fullname",fullname);
        startActivity(attendanceInfoBtn);
        finish();
    }

    private void leaveBtn() {
        Intent leaveBtn = new Intent(userHome.this, leave.class);
        leaveBtn.putExtra("clock_in_date",clock_in_date);
        leaveBtn.putExtra("clock_out_date",clock_out_date);
        leaveBtn.putExtra("clock_in_time",clock_in_time);
        leaveBtn.putExtra("clock_out_time",clock_out_time);
        leaveBtn.putExtra("break_date",break_date);
        leaveBtn.putExtra("after_break_date",after_break_date);
        leaveBtn.putExtra("break_time",break_time);
        leaveBtn.putExtra("after_break_time",after_break_time);
        leaveBtn.putExtra("present_intent",present_intent);
        leaveBtn.putExtra("id",id);
        leaveBtn.putExtra("fullname",fullname);
        startActivity(leaveBtn);
        finish();
    }

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/Profile.php";

    private void profileBtn() {
        Intent profile = new Intent(userHome.this, profile.class);
        profile.putExtra("clock_in_date",clock_in_date);
        profile.putExtra("clock_out_date",clock_out_date);
        profile.putExtra("clock_in_time",clock_in_time);
        profile.putExtra("clock_out_time",clock_out_time);
        profile.putExtra("break_date",break_date);
        profile.putExtra("after_break_date",after_break_date);
        profile.putExtra("break_time",break_time);
        profile.putExtra("after_break_time",after_break_time);
        profile.putExtra("present_intent",present_intent);
        profile.putExtra("id", id);
        profile.putExtra("fullname",fullname);
        startActivity(profile);
        finish();
    }

    @Override
    public void onBackPressed() {
        exitBtn();
    }
}