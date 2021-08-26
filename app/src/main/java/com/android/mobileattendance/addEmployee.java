package com.android.mobileattendance;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class addEmployee extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/AddEmployee.php";

    private Button exitBtn;
    private Button backBtn;
    private Button addEmployeeBtn;
    private TextView from;
    DatePickerDialog.OnDateSetListener setListenerFrom;
    private String fromDate;

    private EditText jabatan,divisi,username,fullname,pob,phonenumber,alamat,email;
    private EditText password,passwordconfirmation;

    private String fullname2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        fullname2 = getIntent().getStringExtra("fullname");

        from = findViewById(R.id.from);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        setDate(from);

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        addEmployee.this, android.R.style.Theme_Holo_Dialog_MinWidth
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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

    private void setDate(TextView view){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("d/M/Y");
        String date = formatter.format(today);
        view.setText(date);
    }

    private void exitBtn() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(addEmployee.this, "Exit Success", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(addEmployee.this, login.class);
                        startActivity(login);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(addEmployee.this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void backBtn() {
        Intent adminHome = new Intent(addEmployee.this, adminHome.class);
        adminHome.putExtra("fullname",fullname2);
        startActivity(adminHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        backBtn();
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
        if(positioninput.startsWith(" ") || positioninput.endsWith(" ")){
            jabatan.setError("Invalid input");
            flag = 1;
        }
        if(divisioninput.isEmpty()){
            divisi.setError("Division must be filled");
            flag = 1;
        }
        if(divisioninput.startsWith(" ") || divisioninput.endsWith(" ")){
            divisi.setError("Invalid input");
            flag = 1;
        }
        if(usernameinput.isEmpty()){
            username.setError("Fullname must be filled");
            flag = 1;
        }
        if(usernameinput.contains(" ")){
            username.setError("Space is not allowed");
            flag = 1;
        }
        if(usernameinput.startsWith(" ") || usernameinput.endsWith(" ")){
            username.setError("Invalid input");
            flag = 1;
        }
        if(fullnameinput.isEmpty()){
            fullname.setError("Fullname must be filled");
            flag = 1;
        }
        if(fullnameinput.startsWith(" ") || fullnameinput.endsWith(" ")){
            fullname.setError("Invalid input");
            flag = 1;
        }
        if(Password.isEmpty()){
            password.setError("Password must be filled");
            flag = 1;
        }
        if(Password.contains(" ")){
            password.setError("Space is not allowed");
            flag = 1;
        }
        if(Password.startsWith(" ") || Password.endsWith(" ")){
            password.setError("Invalid input");
            flag = 1;
        }
        if(PasswordC.isEmpty()){
            passwordconfirmation.setError("Password confirmation must be filled");
            flag = 1;
        }
        if(PasswordC.contains(" ")){
            passwordconfirmation.setError("Space is not allowed");
            flag = 1;
        }
        if(PasswordC.startsWith(" ") || PasswordC.endsWith(" ")){
            passwordconfirmation.setError("Invalid input");
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
        if(pobinput.startsWith(" ") || pobinput.endsWith(" ")){
            pob.setError("Invalid input");
            flag = 1;
        }
        if(addressinput.isEmpty()){
            alamat.setError("Address must be filled");
            flag = 1;
        }
        if(addressinput.startsWith(" ") || addressinput.endsWith(" ")){
            alamat.setError("Invalid input");
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

    private void addEmployeeBtn() {
        if(isValidate()){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            final ProgressDialog loading = new ProgressDialog(addEmployee.this);
                            loading.setMessage("Please Wait...");
                            loading.show();
                            loading.setCanceledOnTouchOutside(false);
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
                            Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
                            String JenisKelamin = spinner.getSelectedItem().toString();

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
                                                    Toast.makeText(addEmployee.this,message,Toast.LENGTH_SHORT).show();
                                                }
                                                if(success.equals("1")){
                                                    Toast.makeText(addEmployee.this,message,Toast.LENGTH_SHORT).show();
                                                    Intent adminHome = new Intent(addEmployee.this, adminHome.class);
                                                    adminHome.putExtra("fullname",fullname2);
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
                                                Toast.makeText(addEmployee.this,"Failed to add new employee !",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    loading.dismiss();
                                    Toast.makeText(getApplicationContext(),"Server Offline !",Toast.LENGTH_LONG).show();
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
                            RequestQueue queue = Volley.newRequestQueue(addEmployee.this);
                            queue.add(stringRequest);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(addEmployee.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }
}