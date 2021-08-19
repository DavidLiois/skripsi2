package com.android.mobileattendance;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class employeeInfoAttendance extends AppCompatActivity {

    ListView list;
    ArrayList<presensi> itemList = new ArrayList<presensi>();
    EIattendanceAdapter adapter;

    private Button exitBtn;
    private Button backBtn;
    private String StaffId;

    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info_attendance);

        StaffId = getIntent().getStringExtra("StaffId");
        fullname = getIntent().getStringExtra("fullname");

        list = (ListView) findViewById(R.id.list);

        adapter = new EIattendanceAdapter(employeeInfoAttendance.this, itemList);
        list.setAdapter(adapter);

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);

        callVolley(StaffId);

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

    private void callVolley(String s){
        String url = "https://shivaistic-casualti.000webhostapp.com/AttendanceInfo.php?data="+s;
        itemList.clear();
        adapter.notifyDataSetChanged();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        presensi presensi = new presensi();
                        presensi.setDate(jsonObject.getString("CreatedDate"));
                        presensi.setClockIn(jsonObject.getString("JamDatang"));
                        presensi.setClockOut(jsonObject.getString("JamPulang"));
                        presensi.setIstirahat(jsonObject.getString("MulaiIstirahat"));
                        presensi.setAfterBreak(jsonObject.getString("SelesaiIstirahat"));

                        itemList.add(presensi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(employeeInfoAttendance.this, "Error : "+e, Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(employeeInfoAttendance.this, "Error : "+error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(employeeInfoAttendance.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(employeeInfoAttendance.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(employeeInfoAttendance.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent adminHome = new Intent(employeeInfoAttendance.this, employeeInfo.class);
        adminHome.putExtra("fullname",fullname);
        startActivity(adminHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}