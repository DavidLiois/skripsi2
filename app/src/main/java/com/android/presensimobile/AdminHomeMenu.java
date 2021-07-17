package com.android.presensimobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeMenu extends AppCompatActivity {

    private Button ADDEMPLOYEE;
    private Button EXIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_menu);

        ADDEMPLOYEE = findViewById(R.id.ADDEMPLOYEE);
        ADDEMPLOYEE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDEMPLOYEE();
            }
        });

        EXIT = findViewById(R.id.EXIT);
        EXIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void ADDEMPLOYEE(){
        Intent ADDEMPLOYEEMENU = new Intent(AdminHomeMenu.this, AddEmployeeMenu.class);
        startActivity(ADDEMPLOYEEMENU);
    }
}