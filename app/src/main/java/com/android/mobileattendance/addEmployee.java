package com.android.mobileattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class addEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String url = "http://192.168.1.7/CRUDVolley/AddEmployee.php";

    private Button exitBtn;
    private Button backBtn;
    private Button addEmployeeBtn;
    private TextView from;
    DatePickerDialog.OnDateSetListener setListenerFrom;
    private String fromDate;
    private String usernameTxt;
    private String text;

    private EditText jabatan,divisi,username,fullname,pob,phonenumber,alamat,email;
    private EditText password,passwordconfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        usernameTxt = getIntent().getStringExtra("username");

        from = findViewById(R.id.from);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        addEmployee.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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

        Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        text = spinner.getSelectedItem().toString();

        usernameTxt = getIntent().getStringExtra("username");

        exitBtn = findViewById(R.id.exitBtn);
        backBtn = findViewById(R.id.backBtn);
        addEmployeeBtn = findViewById(R.id.addEmployeeBtn);
        jabatan = findViewById(R.id.jabatan);
        divisi = findViewById(R.id.divisi);
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        pob = findViewById(R.id.pob);
        phonenumber = findViewById(R.id.phonenumber);
        alamat = findViewById(R.id.alamat);
        email = findViewById(R.id.email);

        password = findViewById(R.id.password);
        passwordconfirmation = findViewById(R.id.passwordconfirmation);

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

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployeeBtn();
            }
        });
    }

    private void exitBtn() {
        Toast.makeText(addEmployee.this, "Exit Success", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(addEmployee.this, login.class);
        startActivity(login);
        finish();
    }

    private void backBtn() {
        if (usernameTxt.equals("Admin")){
            Intent adminHome = new Intent(addEmployee.this, adminHome.class);
            adminHome.putExtra("username",usernameTxt);
            startActivity(adminHome);
            finish();
        }
        else{
            Intent userHome = new Intent(addEmployee.this, userHome.class);
            userHome.putExtra("username",usernameTxt);
            startActivity(userHome);
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean isValidate(){
        String positioninput = jabatan.getText().toString();
        String divisioninput = divisi.getText().toString();
        String Password = password.getText().toString();
        String PasswordC = passwordconfirmation.getText().toString();
        String usernameinput = username.getText().toString();
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
        if(usernameinput.isEmpty()){
            username.setError("Fullname must be filled");
            flag = 1;
        }
        if(fullnameinput.isEmpty()){
            fullname.setError("Fullname must be filled");
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
        if(emailinput.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()){
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

    private void addEmployeeBtn() {
        if(isValidate()){
            String Jabatan = jabatan.getText().toString();
            String Divisi = divisi.getText().toString();
            String Username = username.getText().toString();
            String Password = password.getText().toString();
            String Fullname = fullname.getText().toString();
            String Pob = pob.getText().toString();
            String Phonenumber = phonenumber.getText().toString();
            String Alamat = alamat.getText().toString();
            String Email = email.getText().toString();
            String Dob = from.getText().toString();
            String JenisKelamin = text;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                String message = jsonObject.getString("message");
                                if(success.equals("0")){
                                    Toast.makeText(addEmployee.this,message,Toast.LENGTH_SHORT).show();
                                }
                                if(success.equals("1")){
                                    Toast.makeText(addEmployee.this,message,Toast.LENGTH_SHORT).show();
                                    Intent adminHome = new Intent(addEmployee.this, adminHome.class);
                                    adminHome.putExtra("username",usernameTxt);
                                    startActivity(adminHome);
                                    finish();
                                }
                                if(success.equals("2")){
                                    Toast.makeText(addEmployee.this,message,Toast.LENGTH_SHORT).show();
                                }
                                if(success.equals("3")){
                                    Toast.makeText(addEmployee.this,message,Toast.LENGTH_SHORT).show();
                                }
                                if(success.equals("4")){
                                    Toast.makeText(addEmployee.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(addEmployee.this,"Registration Error !"+e,Toast.LENGTH_LONG).show();
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

                    params.put("jabatan", Jabatan);
                    params.put("divisi", Divisi);
                    params.put("username", Username);
                    params.put("fullname", Fullname);
                    params.put("jeniskelamin", JenisKelamin);
                    params.put("pob", Pob);
                    params.put("dob", Dob);
                    params.put("alamat", Alamat);
                    params.put("email", Email);
                    params.put("phonenumber", Phonenumber);
                    params.put("password", Password);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        }
    }
}