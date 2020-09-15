package com.example.ordersystem;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.squareup.picasso.Picasso;


public class EditActivity extends AppCompatActivity {
  //  public static final int REQUEST_GALLERY = 1;
    ConnectionClass connectionClass;
    String message = "";
  //  Bitmap bitmap;
    ImageView imgpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        connectionClass = new ConnectionClass();

        imgpic = findViewById(R.id.imgpic);
        final RadioButton radiofoods = findViewById(R.id.radio_foods);
        final RadioButton radionoodles = findViewById(R.id.radio_noodles);
        final RadioButton radiodrinks = findViewById(R.id.radio_drinks);
        final EditText edturl = findViewById(R.id.edturl);
        final EditText edtid = findViewById(R.id.edtid);
        final EditText edtname = findViewById(R.id.edtname);
        final EditText edtprice = findViewById(R.id.edtprice);
        final Button btnupload = findViewById(R.id.btnupload);
        final Button btndata = findViewById(R.id.btndata);
        final Button btncancel = findViewById(R.id.btncancel);
        final Button btnsave = findViewById(R.id.btnsave);
        final Button btndelete = findViewById(R.id.btndelete);

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edturl.getText().toString();
                if (url.equals("")){
                    Toast.makeText(EditActivity.this, "กรุณากรอก URL รูปภาพ", Toast.LENGTH_SHORT).show();
                }else {
                    Picasso.get().load(url)
                            .resize(200, 200)
                            .centerInside()
                            .into(imgpic);
                }
            }
        });

        btndata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        message = "ไม่พบฐานข้อมูล";
                    } else {
                        String id = edtid.getText().toString().trim();
                        Statement stmt = con.createStatement();
                        if(id.equals("")){
                            message = "กรุณากรอกไอดี";
                        }
                        else if (radiofoods.isChecked() == true) {
                            String query = "Select f_name, f_price, f_pic From Foods where f_id = '" + id + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                message = "พบข้อมูล";
                                edturl.setText(rs.getString("f_pic"));
                                edtname.setText(rs.getString("f_name"));
                                edtprice.setText(rs.getString("f_price"));
                            }else{
                                message = "ไม่พบข้อมูล";
                                edturl.setText("");
                                edtname.setText("");
                                edtprice.setText("");}
                        }
                        else if (radionoodles.isChecked() == true) {
                            String query = "Select n_name, n_price, n_pic From Noodles where n_id = '" + id + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                message = "พบข้อมูล";
                                edturl.setText(rs.getString("n_pic"));
                                edtname.setText(rs.getString("n_name"));
                                edtprice.setText(rs.getString("n_price"));
                            }else{
                                message = "ไม่พบข้อมูล";
                                edturl.setText("");
                                edtname.setText("");
                                edtprice.setText(""); }
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String query = "Select d_name, d_price, d_pic From Drinks where d_id = '" + id + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                message = "พบข้อมูล";
                                edturl.setText(rs.getString("d_pic"));
                                edtname.setText(rs.getString("d_name"));
                                edtprice.setText(rs.getString("d_price"));
                            }else{
                                message = "ไม่พบข้อมูล";
                                edturl.setText("");
                                edtname.setText("");
                                edtprice.setText("");}
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edturl.setText("");
                edtid.setText("");
                edtname.setText("");
                edtprice.setText("");
                finish();
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
                        String url = edturl.getText().toString();
                        String id = edtid.getText().toString().trim();
                        String name = edtname.getText().toString().trim();
                        String price1 = edtprice.getText().toString().trim();
                        if (id.equals("")){
                            message = "กรุณากรอกไอดี";
                        } else if (name.equals("")){
                            message = "กรุณากรอกชื่ออาหาร";
                        } else if (price1.equals("")){
                            message = "กรุณากรอกราคา";
                        }else {
                            int price = Integer.parseInt(edtprice.getText().toString().trim());
                          //  Bitmap image = ((BitmapDrawable) imgpic.getDrawable()).getBitmap();
                            Statement stmt1 = con.createStatement();
                            if (radiofoods.isChecked() == true) {
                                String strUpdate = "Update Foods Set f_name = '" + name + "', " + "f_price = " + price + "," + "f_pic ='" + url + "'Where f_id = '" + id + "'";
                                stmt1.executeUpdate(strUpdate);
                                message = "แก้ไขข้อมูลเรียบร้อย";
                            } else if (radionoodles.isChecked() == true) {
                                String strUpdate = "Update Noodles Set n_name = '" + name + "', " + "n_price = " + price + "," + "n_pic ='" + url +"'Where n_id = '" + id + "'";
                                stmt1.executeUpdate(strUpdate);
                                message = "แก้ไขข้อมูลเรียบร้อย";
                            } else if (radiodrinks.isChecked() == true) {
                                String strUpdate = "Update Drinks Set d_name = '" + name + "', " + "d_price = " + price + "," + "d_pic ='" + url +"'Where d_id = '" + id + "'";
                                stmt1.executeUpdate(strUpdate);
                                message = "แก้ไขข้อมูลเรียบร้อย";
                            }
                        }
                        imgpic.setImageResource(R.drawable.diet);
                        edturl.setText("");
                        edtid.setText("");
                        edtname.setText("");
                        edtprice.setText("");
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Connection con = connectionClass.CONN();
                    if(con == null) {
                        message = "ไม่พบฐานข้อมูล";
                    } else {
                        String id = edtid.getText().toString();
                        if (id.equals("")){
                            message = "กรุณากรอกไอดี";
                        }else {
                            Statement stmt1 = con.createStatement();
                            if (radiofoods.isChecked() == true) {
                                String strDelete = "Delete From Foods Where f_id = '" + id + "'";
                                stmt1.executeUpdate(strDelete);
                                message = "ลบข้อมูลเรียบร้อย";
                            } else if (radionoodles.isChecked() == true) {
                                String strDelete = "Delete From Noodles Where n_id = '" + id + "'";
                                stmt1.executeUpdate(strDelete);
                                message = "ลบข้อมูลเรียบร้อย";
                            } else if (radiodrinks.isChecked() == true) {
                                String strDelete = "Delete From Drinks Where d_id = '" + id + "'";
                                stmt1.executeUpdate(strDelete);
                                message = "ลบข้อมูลเรียบร้อย";
                            }
                        }
                        imgpic.setImageResource(R.drawable.diet);
                        edturl.setText("");
                        edtid.setText("");
                        edtname.setText("");
                        edtprice.setText("");
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
