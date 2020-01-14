package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ListActivity extends AppCompatActivity {

    LinearLayout LinearAdd, LinearEdit, LinearPro;
    ConnectionClass connectionClass;
    String message ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        connectionClass = new ConnectionClass();

        LinearAdd = (LinearLayout) findViewById(R.id.linearAdd);
        LinearEdit = (LinearLayout) findViewById(R.id.linearEdit);
        LinearPro = (LinearLayout) findViewById(R.id.linearProfile);

        LinearAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdd();
            }
        });

        LinearEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEdit();
            }
        });

        LinearPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPro();
            }
        });


    }
    public void openAdd() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
        finish();
    }
    public void openEdit() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
        finish();
    }
    public void openPro() {
        Connection con = connectionClass.CONN();
        Intent intent = this.getIntent();
        if(intent !=null)
        {
            String strdata = intent.getExtras().getString("Rank");
            if(strdata.equals("Boss"))
            {
                intent.setClass(this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
            else if(strdata.equals("Employee")) {
                Toast.makeText(ListActivity.this, "หน้านี้สำหรับเจ้าของร้านเท่านั้น", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
