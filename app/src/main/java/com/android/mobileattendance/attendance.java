package com.android.mobileattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class attendance extends AppCompatActivity {

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
    private String usernameTxt;
    private String clock_in_date;
    private String clock_out_date;
    private String clock_in_time;
    private String clock_out_time;
    private String break_date;
    private String break_time;
    private String after_break_time;
    private String after_break_date;
    private String present;
    private String present_intent;
    private Double latitudeCurrentLocation;
    private Double longitudeCurrentLocation;
    private Boolean validationDistance;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

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

        clockOut.setEnabled(false);

        clockInTime.setText(clock_in_time);

        if (clockInTime.getText().equals("") | clockInTime.getText().equals("-")){
            clockIn.setEnabled(true);
            clockInTime.setText("-");
        }else if (clockOutTime.getText().equals("-")){
            clockIn.setEnabled(false);
            clockOut.setEnabled(true);
            clockInDate.setText(clock_in_date);
            clockOutDate.setText(clock_out_date);
            clockOutTime.setText(clock_out_time);
        }
        else{
            clockIn.setEnabled(false);
            clockInDate.setText(clock_in_date);
            clockOutDate.setText(clock_out_date);
            clockOutTime.setText(clock_out_time);
        }

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

    private void exitBtn() {
        Toast.makeText(attendance.this, "Exit Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(attendance.this, login.class);
        startActivity(login);
        finish();
    }

    private void backBtn() {
        if (usernameTxt.equals("Admin")){
            Intent adminHome = new Intent(attendance.this, adminHome.class);
            adminHome.putExtra("username",usernameTxt);
            adminHome.putExtra("clock_in_date",clockInDate.getText().toString());
            adminHome.putExtra("clock_out_date",clockOutDate.getText().toString());
            adminHome.putExtra("clock_in_time",clockInTime.getText().toString());
            adminHome.putExtra("clock_out_time",clockOutTime.getText().toString());
            adminHome.putExtra("break_date",break_date);
            adminHome.putExtra("after_break_date",after_break_date);
            adminHome.putExtra("break_time",break_time);
            adminHome.putExtra("after_break_time",after_break_time);
            adminHome.putExtra("present_intent",present);
            startActivity(adminHome);
            finish();
        }
        else{
            Intent userHome = new Intent(attendance.this, userHome.class);
            userHome.putExtra("username",usernameTxt);
            userHome.putExtra("clock_in_date",clockInDate.getText().toString());
            userHome.putExtra("clock_out_date",clockOutDate.getText().toString());
            userHome.putExtra("clock_in_time",clockInTime.getText().toString());
            userHome.putExtra("clock_out_time",clockOutTime.getText().toString());
            userHome.putExtra("break_date",break_date);
            userHome.putExtra("after_break_date",after_break_date);
            userHome.putExtra("break_time",break_time);
            userHome.putExtra("after_break_time",after_break_time);
            userHome.putExtra("present_intent",present);
            startActivity(userHome);
            finish();
        }
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
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
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

    private void clockIn(){
        if (usernameTxt.equals("Admin")){
            distance();
            if (validationDistance){
                Toast.makeText(attendance.this,"Clock-In success",Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

                String dateTime = simpleDateFormat.format(calendar.getTime());
                String dateTime2 = simpleDateFormat2.format(calendar.getTime());

                clockInDate.setText(dateTime);
                clockInTime.setText(dateTime2);

                clockIn.setEnabled(false);
                clockOut.setEnabled(true);

                present = "present";
            }else{
                Toast.makeText(attendance.this,"Out of range",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            distance();
            if (validationDistance){
                Toast.makeText(attendance.this,"Clock-In success",Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

                String dateTime = simpleDateFormat.format(calendar.getTime());
                String dateTime2 = simpleDateFormat2.format(calendar.getTime());

                clockInDate.setText(dateTime);
                clockInTime.setText(dateTime2);

                clockIn.setEnabled(false);
                clockOut.setEnabled(true);

                present = "present";
            }else{
                Toast.makeText(attendance.this,"Out of range",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clockOut(){
        if (usernameTxt.equals("Admin")){
            distance();
            if (validationDistance){
                Toast.makeText(attendance.this,"Clock-In success",Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

                String dateTime = simpleDateFormat.format(calendar.getTime());
                String dateTime2 = simpleDateFormat2.format(calendar.getTime());

                clockOutDate.setText(dateTime);
                clockOutTime.setText(dateTime2);

                clockOut.setEnabled(false);
            }else{
                Toast.makeText(attendance.this,"Out of range",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            distance();
            if (validationDistance){
                Toast.makeText(attendance.this,"Clock-Out success",Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

                String dateTime = simpleDateFormat.format(calendar.getTime());
                String dateTime2 = simpleDateFormat2.format(calendar.getTime());

                clockOutDate.setText(dateTime);
                clockOutTime.setText(dateTime2);

                clockOut.setEnabled(false);
            }else{
                Toast.makeText(attendance.this,"Out of range",Toast.LENGTH_SHORT).show();
            }
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
        //double latitudeSaya = latitudeCurrentLocation;
        //double longitudeSaya = longitudeCurrentLocation;
        double latitudeSaya = -6.201367328854578;
        double longitudeSaya = 106.7803736970193;
        double latitudeTujuan = -6.201367328854578;
        double longitudeTujuan = 106.7803736970193;

        double distance = getDistance(latitudeTujuan, longitudeTujuan, latitudeSaya, longitudeSaya);

        if (distance < 100){
            validationDistance = true;
        }else {
            validationDistance = false;
        }
    }

}