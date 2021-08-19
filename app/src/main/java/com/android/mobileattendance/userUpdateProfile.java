package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class userUpdateProfile extends AppCompatActivity {

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/UserUpdateProfile.php";

    private Button exitBtn;
    private Button backBtn;

    private Button update;

    private String usernoteTxt;
    private String addressTxt;
    private String phonenumberTxt;
    private String emailTxt;

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

    private EditText fullnameET,usernote,phonenumber,alamat,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_profile);

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

        usernoteTxt = getIntent().getStringExtra("usernote");
        addressTxt = getIntent().getStringExtra("address");
        phonenumberTxt = getIntent().getStringExtra("phonenumber");
        emailTxt = getIntent().getStringExtra("email");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);

        update = findViewById(R.id.update);

        fullnameET = findViewById(R.id.fullname);
        usernote = findViewById(R.id.usernote);
        phonenumber = findViewById(R.id.phonenumber);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);

        fullnameET.setText(fullname);
        usernote.setText(usernoteTxt);
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
                        Toast.makeText(userUpdateProfile.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(userUpdateProfile.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(userUpdateProfile.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent userHome = new Intent(userUpdateProfile.this, profile.class);
        userHome.putExtra("clock_in_date", clock_in_date);
        userHome.putExtra("clock_out_date", clock_out_date);
        userHome.putExtra("clock_in_time", clock_in_time);
        userHome.putExtra("clock_out_time", clock_out_time);
        userHome.putExtra("break_date", break_date);
        userHome.putExtra("after_break_date", after_break_date);
        userHome.putExtra("break_time", break_time);
        userHome.putExtra("after_break_time", after_break_time);
        userHome.putExtra("present_intent",present_intent);
        userHome.putExtra("id", id);
        userHome.putExtra("fullname",fullname);
        startActivity(userHome);
        finish();
    }

    private boolean isValidate(){
        String fullnameinput = fullnameET.getText().toString();
        String usernoteinput = usernote.getText().toString();
        String addressinput = alamat.getText().toString();
        String phonenumberinput = phonenumber.getText().toString();
        String emailinput = email.getText().toString();

        Integer flag = 0;

        if(fullnameinput.isEmpty()){
            fullnameET.setError("Fullname must be filled");
            flag = 1;
        }
        if(usernoteinput.isEmpty()){
            usernote.setError("Usernote must be filled");
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
                            String Fullname = fullnameET.getText().toString();
                            String Usernote = usernote.getText().toString();
                            String Phonenumber = phonenumber.getText().toString();
                            String Alamat = alamat.getText().toString();
                            String Email = email.getText().toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String success = jsonObject.getString("success");
                                                String message = jsonObject.getString("message");
                                                if(success.equals("0")){
                                                    Toast.makeText(userUpdateProfile.this,message,Toast.LENGTH_SHORT).show();
                                                }
                                                if(success.equals("1")){
                                                    Toast.makeText(userUpdateProfile.this,message,Toast.LENGTH_SHORT).show();
                                                    Intent adminHome = new Intent(userUpdateProfile.this, profile.class);
                                                    adminHome.putExtra("id", id);
                                                    adminHome.putExtra("fullname", fullname);
                                                    startActivity(adminHome);
                                                    finish();
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                                Toast.makeText(userUpdateProfile.this,"Registration Failed !"+e,Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),"Volley Error !"+error,Toast.LENGTH_LONG).show();
                                }
                            })
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("staffid", id);
                                    params.put("fullname", Fullname);
                                    params.put("usernote", Usernote);
                                    params.put("alamat", Alamat);
                                    params.put("email", Email);
                                    params.put("phonenumber", Phonenumber);
                                    params.put("photo", " ");
                                    return params;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(userUpdateProfile.this);
                            queue.add(stringRequest);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(userUpdateProfile.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}