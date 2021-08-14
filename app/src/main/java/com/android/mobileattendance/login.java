package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginBtn;
    private String usernameTxt;
    private String passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn();
            }
        });
    }

    public static final String url = "http://192.168.1.7/CRUDVolley/Login.php";

    private void loginBtn(){

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        usernameTxt = username.getText().toString();
        passwordTxt = password.getText().toString();

        if (!usernameTxt.equals("") && !passwordTxt.equals("")){
            if (usernameTxt.equals("Admin") && passwordTxt.equals("Admin")){
                Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent adminHome = new Intent(login.this, adminHome.class);
                adminHome.putExtra("username",usernameTxt);
                startActivity(adminHome);
                finish();
            }else if (usernameTxt.equals("User") && passwordTxt.equals("User")){
                Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent userHome = new Intent(login.this, userHome.class);
                userHome.putExtra("username",usernameTxt);
                startActivity(userHome);
            }
            else{
                Toast.makeText(login.this,"Please check your username and password",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(login.this,"Please input your username and password",Toast.LENGTH_SHORT).show();
        }

        String Username = username.getText().toString();
        String Password = password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            String id= jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String username = jsonObject.getString("username");
                            String jabatan = jsonObject.getString("jabatan");

                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Logged In  Success",Toast.LENGTH_LONG).show();
                                if(jabatan == "admin"){
                                    Intent i = new Intent(login.this,adminHome.class);
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    Intent i = new Intent(login.this,userHome.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                            if(success.equals("0")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                            if(success.equals("2")){
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Registration Error !"+e,Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(),"Registration Error !",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this,"all field required",Toast.LENGTH_SHORT).show();
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

        Toast.makeText(login.this,"Login",Toast.LENGTH_SHORT).show();
    }
}