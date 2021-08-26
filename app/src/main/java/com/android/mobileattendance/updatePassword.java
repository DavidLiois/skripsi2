package com.android.mobileattendance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

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
    private SearchView search_bar;

    private Button exitBtn;
    private Button backBtn;
    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        fullname = getIntent().getStringExtra("fullname");

        update_pw_list = (ListView) findViewById(R.id.update_pw_list);
        search_bar = (SearchView) findViewById(R.id.search_bar);

        adapter = new updatePwAdapter(updatePassword.this, itemList);
        update_pw_list.setAdapter(adapter);

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);

        callVolley();

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search_bar.clearFocus();
                search(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ImageView closeBtn = (ImageView) this.search_bar.findViewById(R.id.search_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_bar.setQuery("", false);
                search_bar.clearFocus();
                callVolley();
            }
        });

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

    private void search(String s) {
        String search_url = "https://shivaistic-casualti.000webhostapp.com/Searching.php?search_query="+s;

        final ProgressDialog loading = new ProgressDialog(updatePassword.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        itemList.clear();
        adapter.notifyDataSetChanged();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(search_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loading.dismiss();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        User user = new User();
                        user.setUsername(jsonObject.getString("Username"));
                        user.setFullname(jsonObject.getString("Fullname"));
                        user.setJabatan(jsonObject.getString("Jabatan"));
                        user.setDivisi(jsonObject.getString("Divisi"));
                        user.setTemp(fullname);

                        itemList.add(user);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Log.e("error", String.valueOf(e));
                    Toast.makeText(getApplicationContext(),"Data not found !",Toast.LENGTH_LONG).show();
                }
                finally {
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley-error", String.valueOf(error));
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Server Offline !",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void callVolley(){

        final ProgressDialog loading = new ProgressDialog(updatePassword.this);
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

                        User user = new User();
                        user.setUsername(jsonObject.getString("Username"));
                        user.setFullname(jsonObject.getString("Fullname"));
                        user.setJabatan(jsonObject.getString("Jabatan"));
                        user.setDivisi(jsonObject.getString("Divisi"));
                        user.setTemp(fullname);

                        itemList.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("error", String.valueOf(e));
                        Toast.makeText(updatePassword.this,"Data not found !",Toast.LENGTH_LONG).show();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", String.valueOf(error));
                loading.dismiss();
                Toast.makeText(updatePassword.this,"Server Offline !",Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        backBtn();
    }
}