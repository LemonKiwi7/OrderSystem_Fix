package com.example.ordersystem;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Connection;

public class ListActivity extends AppCompatActivity {

    LinearLayout LinearAdd, LinearEdit, LinearPro, LinearRe;
    ConnectionClass connectionClass;
    String message ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        connectionClass = new ConnectionClass();

        LinearAdd = findViewById(R.id.linearAdd);
        LinearEdit = findViewById(R.id.linearEdit);
        LinearPro = findViewById(R.id.linearProfile);
        LinearRe = findViewById(R.id.linearReport);

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

        LinearRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRe();
            }
        });


    }
    public void openAdd() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    public void openEdit() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
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
            }
            else if(strdata.equals("Employee")) {
                Toast.makeText(ListActivity.this, "หน้านี้สำหรับเจ้าของร้านเท่านั้น", Toast.LENGTH_SHORT).show();
            }
        }
    }//openPro

    public void openRe() {
        Connection con = connectionClass.CONN();
        Intent intent = this.getIntent();
        if(intent !=null)
        {
            String strdata = intent.getExtras().getString("Rank");
            if(strdata.equals("Boss"))
            {
                intent.setClass(this,ReportActivity.class);
                startActivity(intent);
            }
            else if(strdata.equals("Employee")) {
                Toast.makeText(ListActivity.this, "หน้านี้สำหรับเจ้าของร้านเท่านั้น", Toast.LENGTH_SHORT).show();
            }
        }
    }//openRe

}//main
