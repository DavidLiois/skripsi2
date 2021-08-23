package com.android.mobileattendance;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

public class leave extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/AddCuti.php";

    private Button exitBtn;
    private Button backBtn;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private Button submit;

    private Date date1, date2;
    private Date today2;

    private long diffDays;
    private long diff;
    private long diffDays2;
    private long diff2;

    private String id;
    private String fullname;
    private String diffDate;
    private String diffDate2;
    private TextView reason;
    private TextView from;
    DatePickerDialog.OnDateSetListener setListenerFrom;
    private String fromDate;
    private TextView to;
    DatePickerDialog.OnDateSetListener setListenerTo;
    private String toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        executor = ContextCompat.getMainExecutor(this);
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Leave Request Authentication")
                .setAllowedAuthenticators(DEVICE_CREDENTIAL | BIOMETRIC_WEAK)
                .build();

        submit = findViewById(R.id.submit);

        id = getIntent().getStringExtra("id");
        fullname = getIntent().getStringExtra("fullname");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        reason = findViewById(R.id.reason);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setDate(from);
        setDate(to);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        leave.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        ,setListenerFrom,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                fromDate = day+"/"+month+"/"+year;
                from.setText(fromDate);
            }
        };

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        leave.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        ,setListenerTo,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                toDate = day+"/"+month+"/"+year;
                to.setText(toDate);
            }
        };

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn();
            }
        });
    }

    private void setDate(TextView view){

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("d/M/Y");
        String date = formatter.format(today);
        view.setText(date);
    }

    private Boolean isValidate(){
        String a = from.getText().toString();
        String b = to.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Integer flag = 0;
        try {
            date1 = format.parse(a);
            date2 = format.parse(b);

            if (date1.compareTo(date2) > 0){
                Toast.makeText(leave.this,"Start Date < End Date",Toast.LENGTH_SHORT).show();
                flag = 1;
            }else {
                diff = date2.getTime() - date1.getTime();
                diffDays = diff / (24 * 60 * 60 * 1000);
                diffDate = Long.toString(diffDays);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String reasoninput = reason.getText().toString();

        if(reasoninput.isEmpty() ||reasoninput.startsWith(" ") ||reasoninput.endsWith(" ")){
            reason.setError("invalid reason input");
            flag = 1;
        }

        if(diffDays > 3){
            Toast.makeText(leave.this,"Max leave 3 days",Toast.LENGTH_SHORT).show();
            flag = 1;
        }

        today2 = Calendar.getInstance().getTime();

        diff2 = today2.getTime() - date1.getTime();
        diffDays2 = diff / (24 * 60 * 60 * 1000);
        diffDate2 = Long.toString(diffDays2);

        if(diffDays2 < 0){
            Toast.makeText(leave.this,"Invalid input",Toast.LENGTH_SHORT).show();
            flag = 1;
        }

        if(flag == 1){
            return false;
        }
        else{
            return true;
        }
    }

    public void biometric(String s){
        biometricPrompt = new BiometricPrompt(leave.this,
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
                if(s.equals("leave")) {
                    leave_req();
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

    private void leave_req() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("0")){
                                Toast.makeText(leave.this,message,Toast.LENGTH_SHORT).show();
                            }
                            if(success.equals("1")){
                                Toast.makeText(leave.this,message,Toast.LENGTH_SHORT).show();
                                Intent userHome = new Intent(leave.this, userHome.class);
                                userHome.putExtra("id",id);
                                userHome.putExtra("fullname",fullname);
                                startActivity(userHome);
                                finish();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(leave.this,"Registration Error 1 !"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Registration Error 2 !"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("StaffId", id);
                params.put("MulaiCuti", from.getText().toString());
                params.put("SelesaiCuti", to.getText().toString());
                params.put("AlasanIzinCuti", reason.getText().toString());
                params.put("total", diffDate);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(leave.this);
        queue.add(stringRequest);
    }

    private void submitBtn() {
        if(isValidate()) {
            biometric("leave");
        }
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(leave.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(leave.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(leave.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void backBtn() {
        Intent userHome = new Intent(leave.this, userHome.class);
        userHome.putExtra("id", id);
        userHome.putExtra("fullname",fullname);
        startActivity(userHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}