package com.capstone.btao;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView cvSearchDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvSearchDriver = (CardView)findViewById(R.id.cvSearchDriver);

        cvSearchDriver.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchDriverActivity.class);
            startActivity(intent);
            }
        );

    }
}