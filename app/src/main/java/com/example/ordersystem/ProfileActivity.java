package com.example.ordersystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        connectionClass = new ConnectionClass();

        Button btndata = (Button) findViewById(R.id.btndata);
        Button btncancel = (Button) findViewById(R.id.btncancel);
        Button btnsave = (Button) findViewById(R.id.btnsave);
        final EditText edtid = (EditText) findViewById(R.id.edtid);
        final EditText edtfname = (EditText) findViewById(R.id.edtfname);
        final EditText edtlname = (EditText) findViewById(R.id.edtlname);
        final EditText edtadd = (EditText) findViewById(R.id.edtadd);
        final EditText edttel = (EditText) findViewById(R.id.edttel);

        btndata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        message = "ไม่พบฐานข้อมูล";
                    } else {
                        String id = edtid.getText().toString();
                        Statement stmt = con.createStatement();
                        if(id.equals("")){
                            Toast.makeText(ProfileActivity.this, "กรุณากรอกไอดี", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String query = "Select em_fname, em_lname, em_address, em_tel From Employees where em_id = '" + id + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                message = "พบข้อมูล";
                                edtfname.setText(rs.getString("em_fname"));
                                edtlname.setText(rs.getString("em_lname"));
                                edtadd.setText(rs.getString("em_address"));
                                edttel.setText(rs.getString("em_tel"));
                            }else{
                                message = "ไม่พบข้อมูล";
                                edtfname.setText("");
                                edtlname.setText("");
                                edtadd.setText("");
                                edttel.setText("");
                            }
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

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
                    } else {
                        String id = edtid.getText().toString();
                        String fname = edtfname.getText().toString();
                        String lname = edtlname.getText().toString();
                        String add = edtadd.getText().toString();
                        String tel = edttel.getText().toString();
                        if (id.equals("")){
                            message = "กรุณากรอกไอดี";
                        } else if (fname.equals("")){
                            message = "กรุณากรอกชื่อ";
                        } else if (lname.equals("")){
                            message = "กรุณากรอกนามสกุล";
                        } else if (add.equals("")){
                            message = "กรุณากรอกที่อยู่";
                        } else if (tel.equals("")){
                            message = "กรุณากรอกเบอร์โทร";
                        } else {
                            String strUpdate = "Update Employees Set em_fname = '" + fname + "', em_lname = '" + lname + "',"
                                    + "em_address = '" + add + "', em_tel = '" + tel + "' Where em_id = '" + id + "'";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strUpdate);
                            message = "แก้ไขข้อมูลเรียบร้อย";
                            edtid.setText("");
                            edtfname.setText("");
                            edtlname.setText("");
                            edtadd.setText("");
                            edttel.setText("");
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    } //onCreate

    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}//main
