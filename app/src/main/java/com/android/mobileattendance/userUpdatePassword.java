package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class userUpdatePassword extends AppCompatActivity {

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/UpdatePassword.php";

    private Button exitBtn;
    private Button backBtn;
    private String clock_in_date;
    private String clock_out_date;
    private String clock_in_time;
    private String clock_out_time;
    private String break_date;
    private String break_time;
    private String after_break_time;
    private String after_break_date;
    private String present_intent;
    private Button updateBtn;
    private TextView tv_username;
    private EditText password,passwordconfirmation;
    private String usernameTxt;
    private String fullname,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_password);

        clock_in_date = getIntent().getStringExtra("clock_in_date");
        clock_out_date = getIntent().getStringExtra("clock_out_date");
        clock_in_time = getIntent().getStringExtra("clock_in_time");
        clock_out_time = getIntent().getStringExtra("clock_out_time");
        break_date = getIntent().getStringExtra("break_date");
        break_time = getIntent().getStringExtra("break_time");
        after_break_time = getIntent().getStringExtra("after_break_time");
        after_break_date = getIntent().getStringExtra("after_break_date");
        present_intent = getIntent().getStringExtra("present_intent");

        fullname = getIntent().getStringExtra("fullname");
        id = getIntent().getStringExtra("id");
        usernameTxt = getIntent().getStringExtra("username");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        tv_username = findViewById(R.id.username);
        updateBtn = findViewById(R.id.updatePassword);
        password = findViewById(R.id.password);
        passwordconfirmation = findViewById(R.id.passwordconfirmation);
        tv_username.setText(usernameTxt);

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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBtn();
            }
        });
    }

    private boolean isValidate(){
        String Password = password.getText().toString();
        String PasswordC = passwordconfirmation.getText().toString();
        Integer flag = 0;

        if(Password.isEmpty()){
            password.setError("Password must be filled");
            flag = 1;
        }
        if(PasswordC.isEmpty()){
            passwordconfirmation.setError("Password confirmation must be filled");
            flag = 1;
        }
        if (!Password.equals(PasswordC)){
            passwordconfirmation.setError("Password confirmation must be same with password");
            flag = 1;
        }
        if(flag == 1){
            return false;
        }
        else{
            return true;
        }
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(userUpdatePassword.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(userUpdatePassword.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(userUpdatePassword.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent userHome = new Intent(userUpdatePassword.this, profile.class);
        userHome.putExtra("clock_in_date", clock_in_date);
        userHome.putExtra("clock_out_date", clock_out_date);
        userHome.putExtra("clock_in_time", clock_in_time);
        userHome.putExtra("clock_out_time", clock_out_time);
        userHome.putExtra("break_date", break_date);
        userHome.putExtra("after_break_date", after_break_date);
        userHome.putExtra("break_time", break_time);
        userHome.putExtra("after_break_time", after_break_time);
        userHome.putExtra("present_intent",present_intent);
        userHome.putExtra("fullname", fullname);
        userHome.putExtra("id",id);
        startActivity(userHome);
        finish();
    }

    private void update(){
        String Password = password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("0")){
                                Toast.makeText(userUpdatePassword.this,message,Toast.LENGTH_SHORT).show();
                            }
                            if(success.equals("1")){
                                Toast.makeText(userUpdatePassword.this,message,Toast.LENGTH_SHORT).show();
                                Intent adminHome = new Intent(userUpdatePassword.this, profile.class);
                                adminHome.putExtra("id", id);
                                adminHome.putExtra("fullname", fullname);
                                startActivity(adminHome);
                                finish();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(userUpdatePassword.this,"Error !"+e,Toast.LENGTH_LONG).show();
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
                params.put("username", usernameTxt);
                params.put("password", Password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void updateBtn() {
        if(isValidate()){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            update();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(userUpdatePassword.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}