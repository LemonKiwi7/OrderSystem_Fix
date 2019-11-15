package com.example.ordersystem;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.Statement;

public class AddActivity extends AppCompatActivity {
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ImageView imgpic = (ImageView) findViewById(R.id.imgpic);
        final RadioButton radiofoods = (RadioButton) findViewById(R.id.radio_foods);
        final RadioButton radionoodles = (RadioButton) findViewById(R.id.radio_noodles);
        final RadioButton radiodrinks = (RadioButton) findViewById(R.id.radio_drinks);
        final EditText edtid = (EditText) findViewById(R.id.edtid);
        final EditText edtname = (EditText) findViewById(R.id.edtname);
        final EditText edtprice = (EditText) findViewById(R.id.edtprice);
        Button btnupload = (Button) findViewById(R.id.btnupload);
        Button btncancel = (Button) findViewById(R.id.btncancel);
        Button btnsave = (Button) findViewById(R.id.btnsave);
        final ConnectionClass connectionClass;
        connectionClass = new ConnectionClass();
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
                        Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        if(radiofoods.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String name = edtname.getText().toString();
                            int price = Integer.parseInt(edtprice.getText().toString());
                            String strInsert = "Insert Into Foods (f_id, f_name, f_price) Values('" + id + "','" +
                                    name + "','" + price + "')";
                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strInsert);
                            message = "ลงฐานข้อมูลเรียบร้อย";
                            Toast.makeText(AddActivity.this, message,Toast.LENGTH_SHORT).show();
                        }
                        else if (radionoodles.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String name = edtname.getText().toString();
                            int price = Integer.parseInt(edtprice.getText().toString());
                            String strInsert = "Insert Into Noodles (n_id, n_name, n_price, n_pic) Values('" + id + "','" +
                                    name + "','" + price +"')";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strInsert);
                            message = "ลงฐานข้อมูลเรียบร้อย";
                            Toast.makeText(AddActivity.this, message,Toast.LENGTH_SHORT).show();
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String id = edtid.getText().toString();
                            String name = edtname.getText().toString();
                            int price = Integer.parseInt(edtprice.getText().toString());
                            String strInsert = "Insert Into Drinks (d_id, d_name, d_price) Values('" + id + "','" +
                                    name + "','" + price + "')";

                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strInsert);
                            message = "ลงฐานข้อมูลเรียบร้อย";
                            Toast.makeText(AddActivity.this, message,Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                    Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                edtid.setText("");
                edtname.setText("");
                edtprice.setText("");
            }
        }); //btnsave*/

    }//onCreate

    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}//main
