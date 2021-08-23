package com.android.mobileattendance;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class attendance extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private Button exitBtn;
    private Button backBtn;
    private Button clockIn;
    private Button clockOut;
    private TextView currentDateTxt;
    private TextView currentTimeTxt;
    private TextView clockInDate;
    private TextView clockInTime;
    private TextView clockOutDate;
    private TextView clockOutTime;
    private String id;
    private String fullname;
    private Double latitudeCurrentLocation;
    private Double longitudeCurrentLocation;
    private Boolean validationDistance;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        executor = ContextCompat.getMainExecutor(this);
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Attendance Authentication")
                .setAllowedAuthenticators(DEVICE_CREDENTIAL | BIOMETRIC_WEAK)
                .build();

        id = getIntent().getStringExtra("id");
        fullname = getIntent().getStringExtra("fullname");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        currentDateTxt = findViewById(R.id.currentDateTxt);
        currentTimeTxt = findViewById(R.id.currentTimeTxt);
        clockInDate = findViewById(R.id.clockInDate);
        clockInTime = findViewById(R.id.clockInTime);
        clockOutDate = findViewById(R.id.clockOutDate);
        clockOutTime = findViewById(R.id.clockOutTime);
        clockIn = findViewById(R.id.clockIn);
        clockOut = findViewById(R.id.clockOut);

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

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar calendar = Calendar.getInstance();

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

                                String dateTime = simpleDateFormat.format(calendar.getTime());
                                String dateTime2 = simpleDateFormat2.format(calendar.getTime());

                                currentDateTxt.setText(dateTime);
                                currentTimeTxt.setText(dateTime2);
                            }
                        });
                    }
                }
                catch (Exception e){
                    currentDateTxt.setText(R.string.current_date);
                    currentTimeTxt.setText(R.string.current_time);
                }
            }
        };
        thread.start();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(attendance.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(attendance.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        clockIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockIn();
            }
        });

        clockOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clockOut();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }

    private void callVolley(){
        String url = "https://shivaistic-casualti.000webhostapp.com/GetAttendanceData.php?data="+id;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Date1 = jsonObject.getString("CreatedDate");
                            String ClockIntimeStr = jsonObject.getString("JamDatang");
                            String ClockOuttimeStr = jsonObject.getString("JamPulang");

                            clockIn.setEnabled(false);
                            clockOut.setEnabled(false);
                            clockInDate.setText("-");
                            clockInTime.setText("-");
                            clockOutDate.setText("-");
                            clockOutTime.setText("-");

                            if(!Date1.equals("null")) {
                                if(!ClockIntimeStr.equals("null")){
                                    clockIn.setEnabled(false);
                                    clockInDate.setText(Date1);
                                    clockInTime.setText(ClockIntimeStr);
                                    if(!ClockOuttimeStr.equals("null")){
                                        clockOutDate.setText(Date1);
                                        clockOutTime.setText(ClockOuttimeStr);
                                    }else {
                                        clockOutDate.setText("-");
                                        clockOutTime.setText("-");
                                        clockOut.setEnabled(true);
                                    }
                                }
                                else{
                                    clockInDate.setText("-");
                                    clockInTime.setText("-");
                                    clockIn.setEnabled(true);
                                }
                            }else {
                                clockIn.setEnabled(true);
                                clockOut.setEnabled(false);
                                clockInDate.setText("-");
                                clockInTime.setText("-");
                                clockOutDate.setText("-");
                                clockOutTime.setText("-");
                            }
                        }catch (Exception e){
                            clockIn.setEnabled(true);
                            clockOut.setEnabled(false);
                            clockInDate.setText("-");
                            clockInTime.setText("-");
                            clockOutDate.setText("-");
                            clockOutTime.setText("-");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error ! "+error,Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(attendance.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(attendance.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(attendance.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent userHome = new Intent(attendance.this, userHome.class);
        userHome.putExtra("id",id);
        userHome.putExtra("fullname",fullname);
        startActivity(userHome);
        finish();
    }

    private void getCurrentLocation() {
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            latitudeCurrentLocation = location.getLatitude();
                            longitudeCurrentLocation = location.getLongitude();
                            LatLng latLng = new LatLng(location.getLatitude()
                                    ,location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng)
                                    .title("I am there");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,50));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    public void biometric(String s){
        biometricPrompt = new BiometricPrompt(attendance.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                if(s.equals("clock_in")){
                    clockInSuccess();
                }
                else{
                    clockOutSuccess();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        biometricPrompt.authenticate(promptInfo);
    }

    private void clockIn(){
        distance();
        if (validationDistance){
            biometric("clock_in");
        }else{
            Toast.makeText(attendance.this,"Out of range",Toast.LENGTH_SHORT).show();
        }
    }

    private void clockInSuccess(){

        String url = "https://shivaistic-casualti.000webhostapp.com/AddPresensi.php";

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

        String dateTime = simpleDateFormat.format(calendar.getTime());
        String dateTime2 = simpleDateFormat2.format(calendar.getTime());

        String JamMasuk = dateTime2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("0")){
                                Toast.makeText(attendance.this,message,Toast.LENGTH_SHORT).show();
                            }
                            if(success.equals("1")){
                                Toast.makeText(attendance.this,message,Toast.LENGTH_SHORT).show();
                                clockInDate.setText(dateTime);
                                clockInTime.setText(dateTime2);

                                clockIn.setEnabled(false);
                                clockOut.setEnabled(true);

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(attendance.this,"Registration Error !"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Registration Error !"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("StaffId", id);
                params.put("JamDatang", JamMasuk);
                params.put("JamTerlambat", "");
                params.put("AlasanTerlambat", "");
                params.put("Longtitude", longitudeCurrentLocation.toString());
                params.put("Latitude", latitudeCurrentLocation.toString());
                params.put("key", "masuk");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(attendance.this);
        queue.add(stringRequest);
    }

    private void clockOutSuccess(){

        String url = "https://shivaistic-casualti.000webhostapp.com/UpdatePresensi.php";

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

        String dateTime = simpleDateFormat.format(calendar.getTime());
        String dateTime2 = simpleDateFormat2.format(calendar.getTime());

        clockOutDate.setText(dateTime);
        clockOutTime.setText(dateTime2);

        clockOut.setEnabled(false);

        String JamPulang = dateTime2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Registration Error !"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("StaffId", id);
                params.put("jampulang", JamPulang);
                params.put("key", "pulang");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(attendance.this);
        queue.add(stringRequest);
    }

    private void clockOut(){
        distance();
        if (validationDistance){
            biometric("clock_out");
        }else{
            Toast.makeText(attendance.this,"Out of range",Toast.LENGTH_SHORT).show();
        }
    }

    private Double getDistance(Double latitudeTujuan, Double longitudeTujuan, Double latitudeUser, Double longitudeUser) {

        Double pi =  3.14159265358979;
        Double lat1 = latitudeTujuan;
        Double lon1 = longitudeTujuan;
        Double lat2 = latitudeUser;
        Double lon2 = longitudeUser;
        Double R = 6371e3;

        Double lat1Rad = lat1 * (pi/180);
        Double lat2Rad = lat2 * (pi/180);
        Double deltaLatRad = (lat2 - lat1) * (pi/180);
        Double deltaLonRad = (lon2 - lon1) * (pi/180);

        /* RUMUS HAVERSINE */
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double s = R * c; // hasil jarak dalam meter
        return s;
    }

    private void distance(){
        double latitudeSaya = latitudeCurrentLocation;
        double longitudeSaya = longitudeCurrentLocation;
        //double latitudeTujuan = -6.201367328854578; binus
        //double longitudeTujuan = 106.7803736970193;
        //double latitudeTujuan = -6.1548457; rumah david
        //double longitudeTujuan = 106.8723808;
        double latitudeTujuan = latitudeCurrentLocation;
        double longitudeTujuan = longitudeCurrentLocation;

        double distance = getDistance(latitudeTujuan, longitudeTujuan, latitudeSaya, longitudeSaya);

        if (distance < 100){
            validationDistance = true;
        }else {
            validationDistance = false;
        }
    }

}