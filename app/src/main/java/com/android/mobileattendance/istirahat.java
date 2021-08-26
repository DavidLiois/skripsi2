package com.android.mobileattendance;

import android.Manifest;
import android.app.ProgressDialog;
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

public class istirahat extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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
    private String id;
    private String fullname;
    private String Istirahat2;
    private Double latitudeCurrentLocation;
    private Double longitudeCurrentLocation;
    private Boolean validationDistance;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istirahat);

        executor = ContextCompat.getMainExecutor(this);
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Break Authentication")
                .setAllowedAuthenticators(DEVICE_CREDENTIAL | BIOMETRIC_WEAK)
                .build();

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
                    while (!isInterrupted()) {
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
                } catch (Exception e) {
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

    private void callVolley() {
        final ProgressDialog loading = new ProgressDialog(istirahat.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        String url = "https://shivaistic-casualti.000webhostapp.com/GetAttendanceData.php?data="+id;

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Date1 = jsonObject.getString("CreatedDate");
                            String ClockIntimeStr = jsonObject.getString("JamDatang");
                            String breakStart = jsonObject.getString("MulaiIstirahat");
                            String breakEnd = jsonObject.getString("SelesaiIstirahat");

                            istirahat.setEnabled(false);
                            afterBreak.setEnabled(false);
                            breakDate.setText("-");
                            breakTime.setText("-");
                            afterBreakDate.setText("-");
                            afterBreakTime.setText("-");

                            if (!Date1.equals("null")) {
                                if (!ClockIntimeStr.equals("null")) {
                                    if (!breakStart.equals("null")) {
                                        istirahat.setEnabled(false);
                                        breakDate.setText(Date1);
                                        breakTime.setText(breakStart);
                                        if (!breakEnd.equals("null")) {
                                            afterBreak.setEnabled(false);
                                            afterBreakDate.setText(Date1);
                                            afterBreakTime.setText(breakEnd);
                                        } else {
                                            afterBreakDate.setText("-");
                                            afterBreakTime.setText("-");
                                            afterBreak.setEnabled(true);
                                        }
                                    } else {
                                        breakTime.setText("-");
                                        breakDate.setText("-");
                                        istirahat.setEnabled(true);
                                    }
                                } else {
                                    istirahat.setEnabled(false);
                                    afterBreak.setEnabled(false);
                                    breakDate.setText("-");
                                    breakTime.setText("-");
                                    afterBreakDate.setText("-");
                                    afterBreakTime.setText("-");
                                }
                            } else {
                                istirahat.setEnabled(false);
                                afterBreak.setEnabled(false);
                                breakDate.setText("-");
                                breakTime.setText("-");
                                afterBreakDate.setText("-");
                                afterBreakTime.setText("-");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            istirahat.setEnabled(false);
                            afterBreak.setEnabled(false);
                            breakDate.setText("-");
                            breakTime.setText("-");
                            afterBreakDate.setText("-");
                            afterBreakTime.setText("-");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Server Offline !",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
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
        userHome.putExtra("id", id);
        userHome.putExtra("fullname", fullname);
        startActivity(userHome);
        finish();
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }/*else {
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    public void biometric(String s){
        biometricPrompt = new BiometricPrompt(istirahat.this,
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
                if(s.equals("istirahat_success")){
                    istirahatSuccess();
                }
                else{
                    afterBreakSuccess();
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

    private void istirahatSuccess(){
        String url = "https://shivaistic-casualti.000webhostapp.com/UpdatePresensi.php";

        final ProgressDialog loading = new ProgressDialog(istirahat.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

        String dateTime = simpleDateFormat.format(calendar.getTime());
        String dateTime2 = simpleDateFormat2.format(calendar.getTime());

        breakDate.setText(dateTime);
        breakTime.setText(dateTime2);

        istirahat.setEnabled(false);

        Istirahat2 = dateTime2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(istirahat.this,"Server Offline !",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("StaffId", id);
                params.put("mulaiistirahat", Istirahat2);
                params.put("key", "istirahat_mulai");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(istirahat.this);
        queue.add(stringRequest);
        afterBreak.setEnabled(true);
    }

    private void afterBreakSuccess(){
        String url = "https://shivaistic-casualti.000webhostapp.com/UpdatePresensi.php";

        final ProgressDialog loading = new ProgressDialog(istirahat.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh:mm:ss");

        String dateTime = simpleDateFormat.format(calendar.getTime());
        String dateTime2 = simpleDateFormat2.format(calendar.getTime());

        afterBreakDate.setText(dateTime);
        afterBreakTime.setText(dateTime2);

        afterBreak.setEnabled(false);

        String SelesaiIstirahat = dateTime2;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(istirahat.this,"Server Offline !",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("StaffId", id);
                params.put("selesaiistirahat", SelesaiIstirahat);
                params.put("key", "istirahat_selesai");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(istirahat.this);
        queue.add(stringRequest);
    }

    private void istirahat(){
        distance();
        if (validationDistance){
            biometric("istirahat_success");
        }else{
            Toast.makeText(istirahat.this,"Out of range",Toast.LENGTH_SHORT).show();
        }
    }

    private void afterBreak(){
        distance();
        if (validationDistance){
            biometric("afterBreak_success");
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
        double latitudeSaya = latitudeCurrentLocation;
        double longitudeSaya = longitudeCurrentLocation;
        //double latitudeTujuan = -6.201367328854578;
        //double longitudeTujuan = 106.7803736970193;
        /*double latitudeTujuan = -6.1548457;
        double longitudeTujuan = 106.8723808;*/
        double latitudeTujuan = latitudeCurrentLocation;
        double longitudeTujuan = longitudeCurrentLocation;

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