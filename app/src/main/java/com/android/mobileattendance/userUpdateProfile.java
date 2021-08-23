package com.android.mobileattendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class userUpdateProfile extends AppCompatActivity {
    private String Document_img1="";

    private static final String url = "https://shivaistic-casualti.000webhostapp.com/UserUpdateProfile.php";
    //private static final String url = "https://shivaistic-casualti.000webhostapp.com/test.php";

    private Button exitBtn;
    private Button backBtn;
    private Button update;

    private String usernoteTxt;
    private String addressTxt;
    private String phonenumberTxt;
    private String emailTxt;

    private String id;
    private String fullname;

    private EditText fullnameET,usernote,phonenumber,alamat,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_profile);

        exitBtn = (Button) findViewById(R.id.exitBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        update = (Button) findViewById(R.id.update);

        id = getIntent().getStringExtra("id");
        fullname = getIntent().getStringExtra("fullname");

        usernoteTxt = getIntent().getStringExtra("usernote");
        addressTxt = getIntent().getStringExtra("address");
        phonenumberTxt = getIntent().getStringExtra("phonenumber");
        emailTxt = getIntent().getStringExtra("email");

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

        /*mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/
    }

    /*private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                BitmapFactory.Options options;
                options = new BitmapFactory.Options();
                options.inSampleSize = 5;

                Bitmap thumbnail = BitmapFactory.decodeFile(picturePath, options);
                mImageView.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
    }*/

    /*public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        height = maxSize;
        width = maxSize;
        *//*if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = maxSize;
            //width = (int) (height * bitmapRatio);
        }*//*
        return Bitmap.createScaledBitmap(image, width, height, true);
    }*/

    /*public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.NO_WRAP);
        Log.d("encode", Document_img1);
        return Document_img1;
    }*/

    /*private void SendDetail() {
        final ProgressDialog loading = new ProgressDialog(userUpdateProfile.this);
        loading.setMessage("Please Wait...");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        RetryPolicy mRetryPolicy = new DefaultRetryPolicy(0,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            loading.dismiss();
                            Log.d("JSON", response);

                            JSONObject eventObject = new JSONObject(response);
                            String success = eventObject.getString("success");
                            String message = eventObject.getString("message");
                            if (success.equals("0")) {
                                Toast.makeText(userUpdateProfile.this, message, Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(userUpdateProfile.this, message, Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(userUpdateProfile.this, profile.class);
                                intent.putExtra("id", id);
                                intent.putExtra("fullname", fullname);
                                startActivity(intent);
                                finish();
                            }
                        }catch(Exception e){
                            Log.d("Tag", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(userUpdateProfile.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("photo",Document_img1);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(mRetryPolicy);
        requestQueue.add(stringRequest);
    }*/

    private  void test(){

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

    /*private void update(){
        if(isValidate()){
            SendDetail();
        }
    }*/

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
                                                Toast.makeText(userUpdateProfile.this,"Error !"+e,Toast.LENGTH_LONG).show();
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
                                    params.put("photo", "");
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