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

    private String id;
    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

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
        attendanceBtn.putExtra("id",id);
        attendanceBtn.putExtra("fullname",fullname);
        startActivity(attendanceBtn);
        finish();
    }

    private void istirahatBtn() {
        Intent istirahatBtn = new Intent(userHome.this, istirahat.class);
        istirahatBtn.putExtra("id",id);
        istirahatBtn.putExtra("fullname",fullname);
        startActivity(istirahatBtn);
        finish();
    }

    private void attendanceInfoBtn() {
        Intent attendanceInfoBtn = new Intent(userHome.this, attendanceInfo.class);
        attendanceInfoBtn.putExtra("id",id);
        attendanceInfoBtn.putExtra("fullname",fullname);
        startActivity(attendanceInfoBtn);
        finish();
    }

    private void leaveBtn() {
        Intent leaveBtn = new Intent(userHome.this, leave.class);
        leaveBtn.putExtra("id",id);
        leaveBtn.putExtra("fullname",fullname);
        startActivity(leaveBtn);
        finish();
    }

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/Profile.php";

    private void profileBtn() {
        Intent profile = new Intent(userHome.this, profile.class);
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