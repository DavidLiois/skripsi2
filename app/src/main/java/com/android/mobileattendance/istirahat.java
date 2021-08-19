package com.android.mobileattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class istirahat extends AppCompatActivity {

    private Button exitBtn;
    private Button backBtn;
    private Button istirahat;
    private Button afterBreak;
    private TextView currentDateTxt;
    private TextView currentTimeTxt;
    private TextView breakDate;
    private TextView breakTime;
    private TextView afterBreakDate;
    private TextView afterBreakTime;
    private TextView present;
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
    private Boolean validationDistance;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istirahat);

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
        backBtn = findViewById(R.id.backBtn);
        currentDateTxt = findViewById(R.id.currentDateTxt);
        currentTimeTxt = findViewById(R.id.currentTimeTxt);
        breakDate = findViewById(R.id.breakDate);
        breakTime = findViewById(R.id.breakTime);
        afterBreakDate = findViewById(R.id.afterBreakDate);
        afterBreakTime = findViewById(R.id.afterBreakTime);
        istirahat = findViewById(R.id.istirahat);
        afterBreak = findViewById(R.id.afterBreak);
        present = findViewById(R.id.present);

        afterBreak.setEnabled(false);

        breakTime.setText(break_time);
        present.setText(present_intent);

        if (present.getText().equals("present")){
            if (breakTime.getText().equals("") | breakTime.getText().equals("-")){
                istirahat.setEnabled(true);
                breakTime.setText("-");
            }else if (afterBreakTime.getText().equals("-")){
                istirahat.setEnabled(false);
                afterBreak.setEnabled(true);
                breakDate.setText(break_date);
                afterBreakDate.setText(after_break_date);
                afterBreakTime.setText(after_break_time);
            }
            else{
                istirahat.setEnabled(false);
                breakDate.setText(break_date);
                afterBreakDate.setText(after_break_date);
                afterBreakTime.setText(after_break_time);
            }
        }
        else {
            istirahat.setEnabled(false);
            breakTime.setText("-");
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

        if (ActivityCompat.checkSelfPermission(istirahat.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(istirahat.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        istirahat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                istirahat();
            }
        });

        afterBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterBreak();
            }
        });
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(istirahat.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(istirahat.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(istirahat.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent userHome = new Intent(istirahat.this, userHome.class);
        userHome.putExtra("clock_in_date", clock_in_date);
        userHome.putExtra("clock_out_date", clock_out_date);
        userHome.putExtra("clock_in_time", clock_in_time);
        userHome.putExtra("clock_out_time", clock_out_time);
        userHome.putExtra("break_date", breakDate.getText().toString());
        userHome.putExtra("after_break_date", afterBreakDate.getText().toString());
        userHome.putExtra("break_time", breakTime.getText().toString());
        userHome.putExtra("after_break_time", afterBreakTime.getText().toString());
        userHome.putExtra("present_intent",present_intent);
        userHome.putExtra("id", id);
        userHome.putExtra("fullname", fullname);
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

    private void istirahat(){
        distance();
        if (validationDistance){
            Toast.makeText(istirahat.this,"Break success",Toast.LENGTH_SHORT).show();

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

            String dateTime = simpleDateFormat.format(calendar.getTime());
            String dateTime2 = simpleDateFormat2.format(calendar.getTime());

            breakDate.setText(dateTime);
            breakTime.setText(dateTime2);

            istirahat.setEnabled(false);
            afterBreak.setEnabled(true);
        }else{
            Toast.makeText(istirahat.this,"Out of range",Toast.LENGTH_SHORT).show();
        }
    }

    private void afterBreak(){
        distance();
        if (validationDistance){
            Toast.makeText(istirahat.this,"After break success",Toast.LENGTH_SHORT).show();

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

            String dateTime = simpleDateFormat.format(calendar.getTime());
            String dateTime2 = simpleDateFormat2.format(calendar.getTime());

            afterBreakDate.setText(dateTime);
            afterBreakTime.setText(dateTime2);

            afterBreak.setEnabled(false);
        }else{
            Toast.makeText(istirahat.this,"Out of range",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        backBtn();
    }
}