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

public class attendanceInfo extends AppCompatActivity {

    ListView list;
    ArrayList<attendanceinfomodel> itemList = new ArrayList<attendanceinfomodel>();
    attendanceinfoAdapter adapter;

    private Button exitBtn;
    private Button backBtn;
    private String id;
    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_info);

        list = (ListView) findViewById(R.id.list);

        adapter = new attendanceinfoAdapter(attendanceInfo.this, itemList);
        list.setAdapter(adapter);

        id = getIntent().getStringExtra("id");
        fullname = getIntent().getStringExtra("fullname");

        callVolley();

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);

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

    private void callVolley(){
        final ProgressDialog loading = new ProgressDialog(attendanceInfo.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        itemList.clear();
        adapter.notifyDataSetChanged();

        String url = "https://shivaistic-casualti.000webhostapp.com/AttendanceInfo.php?data="+id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                try {
                    for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            attendanceinfomodel presensi = new attendanceinfomodel();
                            presensi.setDate2(jsonObject.getString("CreatedDate"));
                            presensi.setJamDatang(jsonObject.getString("JamDatang"));
                            presensi.setJamPulang(jsonObject.getString("JamPulang"));
                            presensi.setMulaiIstirahat(jsonObject.getString("MulaiIstirahat"));
                            presensi.setSelesaiIstirahat(jsonObject.getString("SelesaiIstirahat"));

                            itemList.add(presensi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(attendanceInfo.this,"Data not found !",Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(attendanceInfo.this,"Server Offline !",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(attendanceInfo.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(attendanceInfo.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(attendanceInfo.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent userHome = new Intent(attendanceInfo.this, userHome.class);
        userHome.putExtra("id",id);
        userHome.putExtra("fullname",fullname);
        startActivity(userHome);
        finish();
    }
}