package com.example.ordersystem;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        connectionClass = new ConnectionClass();

        final ImageView imgpic = (ImageView) findViewById(R.id.imgpic);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton radiofoods = (RadioButton) findViewById(R.id.radio_foods);
        final RadioButton radionoodles = (RadioButton) findViewById(R.id.radio_noodles);
        final RadioButton radiodrinks = (RadioButton) findViewById(R.id.radio_drinks);
        final EditText edtid = (EditText) findViewById(R.id.edtid);
        final EditText edtname = (EditText) findViewById(R.id.edtname);
        final EditText edtprice = (EditText) findViewById(R.id.edtprice);
        final Button btnchoose = (Button) findViewById(R.id.btnchoose);
        final Button btncancel = (Button) findViewById(R.id.btncancel);
        final Button btnsave = (Button) findViewById(R.id.btnsave);
        final Button btndelete = (Button) findViewById(R.id.btndelete);

        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtid.setText("");
                edtname.setText("");
                edtprice.setText("");
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
                        Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        if (radiofoods.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String name = edtname.getText().toString();
                            int price = Integer.parseInt(edtprice.getText().toString());
                            Bitmap image = ((BitmapDrawable) imgpic.getDrawable()).getBitmap();
                            String strUpdate = "Update Foods Set f_name = '" + name + "', " + "f_price = " + price +  "Where f_id = '" + id + "'";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strUpdate);
                            message = "แก้ไขข้อมูลเรียบร้อย";
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        else if (radionoodles.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String name = edtname.getText().toString();
                            int price = Integer.parseInt(edtprice.getText().toString());
                            Bitmap image = ((BitmapDrawable) imgpic.getDrawable()).getBitmap();
                            String strUpdate = "Update Noodles Set n_name = '" + name + "', " + "n_price = " + price +  "Where n_id = '" + id + "'";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strUpdate);
                            message = "แก้ไขข้อมูลเรียบร้อย";
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String name = edtname.getText().toString();
                            int price = Integer.parseInt(edtprice.getText().toString());
                            Bitmap image = ((BitmapDrawable) imgpic.getDrawable()).getBitmap();
                            String strUpdate = "Update Drinks Set d_name = '" + name + "', " + "d_price = " + price +  "Where d_id = '" + id + "'";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strUpdate);
                            message = "แก้ไขข้อมูลเรียบร้อย";
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                    Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                }
               edtid.setText("");
               edtname.setText("");
               edtprice.setText("");
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null) {
                        message = "ไม่พบฐานข้อมูล";
                        Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        if (radiofoods.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String strDelete = "Delete From Foods Where f_id = '" + id + "'";
                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strDelete);
                            message = "ลบข้อมูลเรียบร้อย";
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        else if (radionoodles.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String strDelete = "Delete From Noodles Where n_id = '" + id + "'";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strDelete);
                            message = "ลบข้อมูลเรียบร้อย";
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String strDelete = "Delete From Drinks Where d_id = '" + id + "'";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strDelete);
                            message = "ลบข้อมูลเรียบร้อย";
                            Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                    Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                edtid.setText("");
                edtname.setText("");
                edtprice.setText("");
            }
        });
    }

    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
