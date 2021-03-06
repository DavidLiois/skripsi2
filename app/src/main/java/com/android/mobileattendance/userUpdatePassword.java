package com.android.mobileattendance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/UserUpdatePassword.php";

    private Button exitBtn;
    private Button backBtn;
    private Button updateBtn;
    private TextView tv_username;
    private EditText password,passwordconfirmation,oldpassword;;
    private String usernameTxt;
    private String fullname,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_password);

        fullname = getIntent().getStringExtra("fullname");
        id = getIntent().getStringExtra("id");
        usernameTxt = getIntent().getStringExtra("username");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        tv_username = findViewById(R.id.username);
        updateBtn = findViewById(R.id.updatePassword);
        password = findViewById(R.id.password);
        passwordconfirmation = findViewById(R.id.passwordconfirmation);
        oldpassword = findViewById(R.id.oldPassword);
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
        String oldPw = oldpassword.getText().toString();
        Integer flag = 0;

        if(oldPw.isEmpty()){
            oldpassword.setError("Old Password must be filled");
            flag = 1;
        }
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
        userHome.putExtra("fullname", fullname);
        userHome.putExtra("id",id);
        startActivity(userHome);
        finish();
    }

    private void update(){
        String Password = password.getText().toString();

        final ProgressDialog loading = new ProgressDialog(userUpdatePassword.this);
        loading.setMessage("Loading ...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
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
                            if(success.equals("2")){
                                Toast.makeText(userUpdatePassword.this,message,Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(userUpdatePassword.this,"Error updating password !",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(userUpdatePassword.this,"Server Offline !",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameTxt);
                params.put("password", Password);
                params.put("oldpassword", oldpassword.getText().toString());
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