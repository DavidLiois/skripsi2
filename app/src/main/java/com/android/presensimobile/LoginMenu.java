package com.android.presensimobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginMenu extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private Button LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);
        LOGIN = findViewById(R.id.LOGIN);
        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOGIN();
            }
        });
    }

    private void LOGIN(){
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        String UsernameTxt = Username.getText().toString();
        String PasswordTxt = Password.getText().toString();

        if (UsernameTxt.equals("Admin") && PasswordTxt.equals("Admin")){
            Intent ADMINHOMEMENU = new Intent(LoginMenu.this, AdminHomeMenu.class);
            startActivity(ADMINHOMEMENU);
        }else if(UsernameTxt.equals("User") && PasswordTxt.equals("User")){
            Intent USERHOMEMENU = new Intent(LoginMenu.this, UserHomeMenu.class);
            startActivity(USERHOMEMENU);
        }else{
            Intent ERRORMENU = new Intent(LoginMenu.this, ErrorMenu.class);
            startActivity(ERRORMENU);
        }
    }
}