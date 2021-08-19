package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
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

public class updatePassword2 extends AppCompatActivity {
    private static final String url = "https://shivaistic-casualti.000webhostapp.com/UpdatePassword.php";

    private Button exitBtn;
    private Button backBtn;
    private Button updateBtn;
    private TextView tv_username;
    private EditText password,passwordconfirmation;
    private String usernameTxt;
    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password2);

        usernameTxt = getIntent().getStringExtra("username");
        fullname = getIntent().getStringExtra("fullname");

        tv_username = findViewById(R.id.username);
        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
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
                        Toast.makeText(updatePassword2.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(updatePassword2.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(updatePassword2.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent adminHome = new Intent(updatePassword2.this, updatePassword.class);
        adminHome.putExtra("fullname",fullname);
        startActivity(adminHome);
        finish();
    }

    private void updateBtn() {
        if(isValidate()){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
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
                                                    Toast.makeText(updatePassword2.this,message,Toast.LENGTH_LONG).show();
                                                }
                                                if(success.equals("1")){
                                                    Toast.makeText(updatePassword2.this,message,Toast.LENGTH_LONG).show();
                                                    Intent adminHome = new Intent(updatePassword2.this, updatePassword.class);
                                                    adminHome.putExtra("fullname",fullname);
                                                    startActivity(adminHome);
                                                    finish();
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                                Toast.makeText(updatePassword2.this,"Error !"+e,Toast.LENGTH_LONG).show();
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
                                    params.put("username", usernameTxt);
                                    params.put("password", Password);
                                    return params;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(updatePassword2.this);
                            queue.add(stringRequest);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(updatePassword2.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}