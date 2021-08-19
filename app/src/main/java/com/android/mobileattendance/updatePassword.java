package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class updatePassword extends AppCompatActivity {

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/GetAllData.php";
    ListView update_pw_list;
    ArrayList<User> itemList = new ArrayList<User>();
    updatePwAdapter adapter;

    private Button exitBtn;
    private Button backBtn;
    private Button searchBtn;
    private EditText search;
    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        fullname = getIntent().getStringExtra("fullname");

        update_pw_list = (ListView) findViewById(R.id.update_pw_list);

        adapter = new updatePwAdapter(updatePassword.this, itemList);
        update_pw_list.setAdapter(adapter);

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        searchBtn = findViewById(R.id.searchBtn);
        search = findViewById(R.id.search);

        callVolley();

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

    private void callVolley(){
        itemList.clear();
        adapter.notifyDataSetChanged();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        User user = new User();
                        user.setUsername(jsonObject.getString("Username"));
                        user.setFullname(jsonObject.getString("Fullname"));
                        user.setJabatan(jsonObject.getString("Jabatan"));
                        user.setDivisi(jsonObject.getString("Divisi"));
                        user.setTemp(fullname);

                        itemList.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                        Toast.makeText(updatePassword.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(updatePassword.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(updatePassword.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent adminHome = new Intent(updatePassword.this, adminHome.class);
        adminHome.putExtra("fullname",fullname);
        startActivity(adminHome);
        finish();
    }

    private void searchBtn() {

    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}