package com.android.mobileattendance;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginBtn;
    private Double latitude, longtitude;

    private CheckBox rememberme;
    private SharedPreferences loginpreferences;
    private SharedPreferences.Editor loginprefseditor;
    private Boolean saveLogin;

    private String sUsername;
    private String sPassword;

    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        rememberme = findViewById(R.id.rememberMe);

        loginpreferences = getSharedPreferences("loginPref", MODE_PRIVATE);
        loginprefseditor = loginpreferences.edit();
        saveLogin = loginpreferences.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            username.setText(loginpreferences.getString("username", ""));
            password.setText(loginpreferences.getString("password", ""));
            rememberme.setChecked(true);
        }

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(login.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(login.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn();
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    latitude = location.getLatitude();
                    longtitude = location.getLongitude();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    public boolean isValid(){
        Integer Flag = 0;

        if (username.getText().toString().isEmpty()){
            username.setError("Username must be filled !");
            Flag = 1;
        }

        if (password.getText().toString().isEmpty()){
            password.setError("Password must be filled !");
            Flag = 1;
        }

        if (Flag == 1){
            return false;
        }
        else{
            return true;
        }
    }

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/Login.php";

    private void loginBtn(){
        final ProgressDialog loading = new ProgressDialog(login.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);
        String Username = username.getText().toString();
        String Password = password.getText().toString();

        InputMethodManager imm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

        sUsername = username.getText().toString();
        sPassword = password.getText().toString();

        if (rememberme.isChecked()) {
            loginprefseditor.putBoolean("saveLogin", true);
            loginprefseditor.putString("username", sUsername);
            loginprefseditor.putString("password", sPassword);
            loginprefseditor.commit();
        } else {
            loginprefseditor.clear();
            loginprefseditor.commit();
        }

        if(isValid()){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                String message = jsonObject.getString("message");
                                if(success.equals("1")){
                                    String fullname = jsonObject.getString("Fullname");
                                    String jabatan = jsonObject.getString("Jabatan");
                                    String id = jsonObject.getString("StaffId");
                                    Toast.makeText(login.this,"Logged In Success",Toast.LENGTH_LONG).show();
                                    if(jabatan.equals("admin")){
                                        Intent adminHome = new Intent(login.this, adminHome.class);
                                        adminHome.putExtra("fullname",fullname);
                                        startActivity(adminHome);
                                        finish();
                                    }
                                    else{
                                        Intent userHome = new Intent(login.this, userHome.class);
                                        userHome.putExtra("fullname",fullname);
                                        userHome.putExtra("id", id);
                                        startActivity(userHome);
                                        finish();
                                    }
                                }
                                if(success.equals("0")){
                                    failedLoginAttempt(Username, Password, latitude, longtitude);
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                }
                                if(success.equals("2")){
                                    failedLoginAttempt(Username, Password, latitude, longtitude);
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"Login Error !",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(login.this,"Server Offline",Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", Username);
                    params.put("password", Password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        }
    }

    private void failedLoginAttempt(String failed_username, String failed_password, Double failed_latitude, Double failed_longtitude){
        String url_failed_attempt = "https://shivaistic-casualti.000webhostapp.com/LoginAttempt.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_failed_attempt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this,"Error ! "+error,Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", failed_username);
                params.put("password", failed_password);
                params.put("ipnumber", Utils.getIPAddress(true));
                params.put("lat", failed_latitude.toString());
                params.put("long", failed_longtitude.toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}