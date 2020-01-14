package com.example.ordersystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
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
import android.net.Uri;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.provider.MediaStore.Images.Media;


public class EditActivity extends AppCompatActivity {
    public static final int REQUEST_GALLERY = 1;
    ConnectionClass connectionClass;
    String message = "";
    Bitmap bitmap;
    ImageView imgpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        connectionClass = new ConnectionClass();

        imgpic = (ImageView) findViewById(R.id.imgpic);
        final RadioButton radiofoods = (RadioButton) findViewById(R.id.radio_foods);
        final RadioButton radionoodles = (RadioButton) findViewById(R.id.radio_noodles);
        final RadioButton radiodrinks = (RadioButton) findViewById(R.id.radio_drinks);
        final EditText edtid = (EditText) findViewById(R.id.edtid);
        final EditText edtname = (EditText) findViewById(R.id.edtname);
        final EditText edtprice = (EditText) findViewById(R.id.edtprice);
        final Button btnchoose = (Button) findViewById(R.id.btnchoose);
        final Button btndata = (Button) findViewById(R.id.btndata);
        final Button btncancel = (Button) findViewById(R.id.btncancel);
        final Button btnsave = (Button) findViewById(R.id.btnsave);
        final Button btndelete = (Button) findViewById(R.id.btndelete);

        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), REQUEST_GALLERY);
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
                        String id = edtid.getText().toString();
                        Statement stmt = con.createStatement();
                        if(id.equals("")){
                            Toast.makeText(EditActivity.this, "กรุณากรอกไอดี", Toast.LENGTH_SHORT).show();
                        }
                        else if (radiofoods.isChecked() == true) {
                            String query = "Select f_name, f_price From Foods where f_id = '" + id + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                message = "พบข้อมูล";
                                edtname.setText(rs.getString("f_name"));
                                edtprice.setText(rs.getString("f_price"));
                            }else{
                                message = "ไม่พบข้อมูล";
                                edtname.setText("");
                                edtprice.setText("");}
                        }
                        else if (radionoodles.isChecked() == true) {
                            String query = "Select n_name, n_price From Noodles where n_id = '" + id + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                message = "พบข้อมูล";
                                edtname.setText(rs.getString("n_name"));
                                edtprice.setText(rs.getString("n_price"));
                            }else{
                                message = "ไม่พบข้อมูล";
                                edtname.setText("");
                                edtprice.setText(""); }
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String query = "Select d_name, d_price From Drinks where d_id = '" + id + "'";
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                message = "พบข้อมูล";
                                edtname.setText(rs.getString("d_name"));
                                edtprice.setText(rs.getString("d_price"));
                            }else{
                                message = "ไม่พบข้อมูล";
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
                    } else {
                        String id = edtid.getText().toString();
                        String name = edtname.getText().toString();
                        int price = Integer.parseInt(edtprice.getText().toString());
                        Bitmap image = ((BitmapDrawable) imgpic.getDrawable()).getBitmap();
                        Statement stmt1 = con.createStatement();
                        if (radiofoods.isChecked() == true) {
                            String strUpdate = "Update Foods Set f_name = '" + name + "', " + "f_price = " + price +  "Where f_id = '" + id + "'";
                            stmt1.executeUpdate(strUpdate);
                            message = "แก้ไขข้อมูลเรียบร้อย";
                        }

                        else if (radionoodles.isChecked() == true) {
                            String strUpdate = "Update Noodles Set n_name = '" + name + "', " + "n_price = " + price +  "Where n_id = '" + id + "'";
                            stmt1.executeUpdate(strUpdate);
                            message = "แก้ไขข้อมูลเรียบร้อย";
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String strUpdate = "Update Drinks Set d_name = '" + name + "', " + "d_price = " + price +  "Where d_id = '" + id + "'";
                            stmt1.executeUpdate(strUpdate);
                            message = "แก้ไขข้อมูลเรียบร้อย";
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
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
                    } else {
                        String id = edtid.getText().toString();
                        Statement stmt1 = con.createStatement();
                        if (radiofoods.isChecked() == true) {
                            String strDelete = "Delete From Foods Where f_id = '" + id + "'";
                            stmt1.executeUpdate(strDelete);
                            message = "ลบข้อมูลเรียบร้อย";
                        }
                        else if (radionoodles.isChecked() == true) {
                            String strDelete = "Delete From Noodles Where n_id = '" + id + "'";
                            stmt1.executeUpdate(strDelete);
                            message = "ลบข้อมูลเรียบร้อย";
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String strDelete = "Delete From Drinks Where d_id = '" + id + "'";
                            stmt1.executeUpdate(strDelete);
                            message = "ลบข้อมูลเรียบร้อย";
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(EditActivity.this, message, Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = Media.getBitmap(this.getContentResolver(), uri);
                imgpic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
