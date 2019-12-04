package com.example.ordersystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class ProfileActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        connectionClass = new ConnectionClass();
        Button btncancel = (Button) findViewById(R.id.btncancel2);
        Button btnsave = (Button) findViewById(R.id.btnsave2);
        final EditText edtid = (EditText) findViewById(R.id.edtid);
        final EditText edtfname = (EditText) findViewById(R.id.edtfname);
        final EditText edtlname = (EditText) findViewById(R.id.edtlname);
        final EditText edtadd = (EditText) findViewById(R.id.edtadd);
        final EditText edttel = (EditText) findViewById(R.id.edttel);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtid.setText("");
                edtfname.setText("");
                edtlname.setText("");
                edtadd.setText("");
                edttel.setText("");
                openMenu();
            }
        });//btncancel

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null) {
                        message = "ไม่พบฐานข้อมูล";
                        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        String id = edtid.getText().toString();
                        String fname = edtfname.getText().toString();
                        String lname = edtlname.getText().toString();
                        String add = edtadd.getText().toString();
                        String tel = edttel.getText().toString();

                        String strUpdate = "Update Employees Set em_fname = '" + fname + "', " + "em_lname = '" + lname + "',"
                                + "em_address = '" + add + "'," + "em_tel = '" + tel +  "Where em_id = '" + id + "'";

                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(strUpdate);
                        message = "แก้ไขข้อมูลเรียบร้อย";
                        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                    Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                edtid.setText("");
                edtfname.setText("");
                edtlname.setText("");
                edtadd.setText("");
                edttel.setText("");
            }
        });





    }

    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
