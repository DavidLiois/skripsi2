package com.android.mobileattendance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class employeeInfoLeave extends AppCompatActivity {

    ListView list;
    ArrayList<EIleave> itemList = new ArrayList<EIleave>();
    EIleaveAdapter adapter;

    private Button exitBtn;
    private Button backBtn;
    private String StaffId;

    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info_leave);

        StaffId = getIntent().getStringExtra("StaffId");
        fullname = getIntent().getStringExtra("fullname");

        list = (ListView) findViewById(R.id.list);

        adapter = new EIleaveAdapter(employeeInfoLeave.this, itemList);
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
        String url = "https://shivaistic-casualti.000webhostapp.com/GetCutiData.php?data="+s;

        final ProgressDialog loading = new ProgressDialog(employeeInfoLeave.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        itemList.clear();
        adapter.notifyDataSetChanged();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        EIleave leave = new EIleave();
                        leave.setReason(jsonObject.getString("AlasanIzinCuti"));
                        leave.setStartDate(jsonObject.getString("MulaiCuti"));
                        leave.setEndDate(jsonObject.getString("SelesaiCuti"));
                        leave.setImage(jsonObject.getString("BuktiIzinCuti"));

                        itemList.add(leave);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(employeeInfoLeave.this, "Data not found!", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Server Offline !",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(employeeInfoLeave.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(employeeInfoLeave.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(employeeInfoLeave.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent adminHome = new Intent(employeeInfoLeave.this, employeeInfo.class);
        adminHome.putExtra("fullname",fullname);
        startActivity(adminHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}