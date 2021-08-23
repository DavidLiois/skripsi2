package com.android.mobileattendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    private Button exitBtn;
    private Button backBtn;
    private Button updateProfileBtn;
    private Button updatePasswordBtn;

    private String id;
    private String fullname;
    private String username;

    private String usernote2,email2,address2,phonenumber2;

    private TextView fullnames, position, division, usernotes, gender, pobs, dobs, address, phonenumbers, emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        id = getIntent().getStringExtra("id");
        fullname = getIntent().getStringExtra("fullname");

        fullnames = findViewById(R.id.fullname);
        position = findViewById(R.id.jabatan);
        division = findViewById(R.id.divisi);
        usernotes = findViewById(R.id.usernote);
        gender = findViewById(R.id.gender);
        pobs = findViewById(R.id.pob);
        dobs = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        phonenumbers = findViewById(R.id.phonenumber);
        emails = findViewById(R.id.email);

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        updatePasswordBtn = findViewById(R.id.updatePasswordBtn);

        callVolley(id);

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

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileBtn();
            }
        });

        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePasswordBtn();
            }
        });
    }

    private void callVolley(String s){
        String url = "https://shivaistic-casualti.000webhostapp.com/Profile.php?data="+s;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String fullname = jsonObject.getString("Fullname");
                            username = jsonObject.getString("Username");
                            String jabatan = jsonObject.getString("Jabatan");
                            String divisi = jsonObject.getString("Divisi");
                            usernote2 = jsonObject.getString("UserNote");
                            String jeniskelamin = jsonObject.getString("JenisKelamin");
                            String pob = jsonObject.getString("PlaceOfBirth");
                            String dob = jsonObject.getString("DateOfBirth");
                            address2 = jsonObject.getString("Alamat");
                            phonenumber2 = jsonObject.getString("PhoneNumber");
                            String email = jsonObject.getString("Email");

                            fullnames.setText(fullname);
                            position.setText(jabatan);
                            division.setText(divisi);
                            if (usernote2.isEmpty()){
                                usernotes.setText("-");
                            }else {
                                usernotes.setText(usernote2);
                            }
                            gender.setText(jeniskelamin);
                            pobs.setText(pob);
                            dobs.setText(dob);
                            address.setText(address2);
                            phonenumbers.setText(phonenumber2);
                            emails.setText(email);

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(profile.this,"Error ! "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error ! "+error,Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(profile.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(profile.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(profile.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent userHome = new Intent(profile.this, userHome.class);
        userHome.putExtra("id", id);
        userHome.putExtra("fullname", fullname);
        startActivity(userHome);
        finish();
    }

    private void updateProfileBtn() {
        email2 = emails.getText().toString();
        Intent userHome = new Intent(profile.this, userUpdateProfile.class);
        userHome.putExtra("fullname", fullname);
        userHome.putExtra("id", id);
        if (usernote2.isEmpty()){
            userHome.putExtra("usernote", "-");
        }else {
            userHome.putExtra("usernote", usernote2);
        }
        userHome.putExtra("address", address2);
        userHome.putExtra("phonenumber", phonenumber2);
        userHome.putExtra("email", email2);
        startActivity(userHome);
        finish();
    }

    private void updatePasswordBtn() {
        Intent userHome = new Intent(profile.this, userUpdatePassword.class);
        userHome.putExtra("fullname", fullname);
        userHome.putExtra("id", id);
        userHome.putExtra("username", username);
        startActivity(userHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        backBtn();
    }
}