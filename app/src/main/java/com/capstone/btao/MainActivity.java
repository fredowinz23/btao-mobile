package com.capstone.btao;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.capstone.btao.auth.LoginActivity;

public class MainActivity extends AppCompatActivity {

    CardView cvSearchDriver;
    CardView cvLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvSearchDriver = (CardView)findViewById(R.id.cvSearchDriver);
        cvLogout = (CardView)findViewById(R.id.cvLogout);

        cvSearchDriver.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchDriverActivity.class);
            startActivity(intent);
            }
        );

        cvLogout.setOnClickListener(v ->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }
}