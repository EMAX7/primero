package com.example.ernesto.primero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    Button btnScan, btnCart, btnHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCart = ((Button)this.findViewById(R.id.btnCart));
        btnHistory = ((Button)this.findViewById(R.id.btnHistory));
        btnScan = ((Button)this.findViewById(R.id.btnScan));
        btnScan.setOnClickListener(this);
        btnCart.setOnClickListener(this);
        btnHistory.setOnClickListener(this);

    }


    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.btnScan:
                intent = new Intent(MainActivity.this,ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCart:
                intent = new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHistory:
                intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
                break;

        }

    }


}