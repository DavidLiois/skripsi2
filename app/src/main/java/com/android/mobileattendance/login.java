package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    }
}