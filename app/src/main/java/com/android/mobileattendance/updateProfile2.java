package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class updateProfile2 extends AppCompatActivity {

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/UpdateProfile.php";

    private Button exitBtn;
    private Button backBtn;
    private Button update;
    private TextView from;
    DatePickerDialog.OnDateSetListener setListenerFrom;
    private String fromDate;
    private String jabatanTxt;
    private String divisiTxt;
    private String fullnameTxt;
    private String pobTxt;
    private String dobTxt;
    private String addressTxt;
    private String phonenumberTxt;
    private String emailTxt;
    private String username;
    private String temp;

    private EditText jabatan,divisi,fullname,pob,phonenumber,alamat,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile2);

        username = getIntent().getStringExtra("username");
        jabatanTxt = getIntent().getStringExtra("jabatan");
        divisiTxt = getIntent().getStringExtra("divisi");
        fullnameTxt = getIntent().getStringExtra("fullname");
        pobTxt = getIntent().getStringExtra("pob");
        dobTxt = getIntent().getStringExtra("dob");
        addressTxt = getIntent().getStringExtra("address");
        phonenumberTxt = getIntent().getStringExtra("phonenumber");
        emailTxt = getIntent().getStringExtra("email");
        temp = getIntent().getStringExtra("temp");

        from = findViewById(R.id.from);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        updateProfile2.this, android.R.style.Theme_Holo_Dialog_MinWidth
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

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        update = findViewById(R.id.update);
        jabatan = findViewById(R.id.jabatan);
        divisi = findViewById(R.id.divisi);
        fullname = findViewById(R.id.fullname);
        pob = findViewById(R.id.pob);
        phonenumber = findViewById(R.id.phonenumber);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);

        jabatan.setText(jabatanTxt);
        divisi.setText(divisiTxt);
        fullname.setText(fullnameTxt);
        pob.setText(pobTxt);
        from.setText(dobTxt);
        alamat.setText(addressTxt);
        phonenumber.setText(phonenumberTxt);
        email.setText(emailTxt);

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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(updateProfile2.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(updateProfile2.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(updateProfile2.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent adminHome = new Intent(updateProfile2.this, updateProfile.class);
        adminHome.putExtra("fullname",temp);
        startActivity(adminHome);
        finish();
    }

    private boolean isValidate(){
        String positioninput = jabatan.getText().toString();
        String divisioninput = divisi.getText().toString();
        String fullnameinput = fullname.getText().toString();
        String pobinput = pob.getText().toString();
        String dobinput = from.getText().toString();
        String addressinput = alamat.getText().toString();
        String phonenumberinput = phonenumber.getText().toString();
        String emailinput = email.getText().toString();
        Integer flag = 0;

        if(positioninput.isEmpty()){
            jabatan.setError("Position must be filled");
            flag = 1;
        }
        if(divisioninput.isEmpty()){
            divisi.setError("Division must be filled");
            flag = 1;
        }
        if(fullnameinput.isEmpty()){
            fullname.setError("Fullname must be filled");
            flag = 1;
        }
        if(pobinput.isEmpty()){
            pob.setError("Place of birth must be filled");
            flag = 1;
        }
        if(dobinput.isEmpty()){
            from.setError("Date of birth must be filled");
            flag = 1;
        }
        if(addressinput.isEmpty()){
            alamat.setError("Address must be filled");
            flag = 1;
        }
        if(!phonenumberinput.matches("[0-9]{10,13}")){
            phonenumber.setError("Enter number 10-13 digit");
            flag = 1;
        }
        if(emailinput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){
            email.setError("Please Enter Valid Mail");
            flag = 1;
        }
        if(flag == 1){
            return false;
        }
        else{
            return true;
        }
    }

    private void update() {
        if(isValidate()){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            String Jabatan = jabatan.getText().toString();
                            String Divisi = divisi.getText().toString();
                            String Fullname = fullname.getText().toString();
                            String Pob = pob.getText().toString();
                            String Phonenumber = phonenumber.getText().toString();
                            String Alamat = alamat.getText().toString();
                            String Email = email.getText().toString();
                            String Dob = from.getText().toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String success = jsonObject.getString("success");
                                                String message = jsonObject.getString("message");
                                                if(success.equals("0")){
                                                    Toast.makeText(updateProfile2.this,message,Toast.LENGTH_SHORT).show();
                                                }
                                                if(success.equals("1")){
                                                    Toast.makeText(updateProfile2.this,message,Toast.LENGTH_SHORT).show();
                                                    Intent adminHome = new Intent(updateProfile2.this, updateProfile.class);
                                                    adminHome.putExtra("fullname",temp);
                                                    startActivity(adminHome);
                                                    finish();
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                                Toast.makeText(updateProfile2.this,"Registration Error !"+e,Toast.LENGTH_LONG).show();
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
                                    params.put("username", username);
                                    params.put("jabatan", Jabatan);
                                    params.put("divisi", Divisi);
                                    params.put("fullname", Fullname);
                                    params.put("pob", Pob);
                                    params.put("dob", Dob);
                                    params.put("alamat", Alamat);
                                    params.put("email", Email);
                                    params.put("phonenumber", Phonenumber);
                                    return params;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(updateProfile2.this);
                            queue.add(stringRequest);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(updateProfile2.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}