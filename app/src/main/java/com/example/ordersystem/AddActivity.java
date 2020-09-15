package com.example.ordersystem;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.Statement;

public class AddActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
   // public static final int REQUEST_GALLERY = 1;
    String message = "";
   // Bitmap bitmap;
    ImageView imgpic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        connectionClass = new ConnectionClass();
        imgpic = findViewById(R.id.imgpic);
        final RadioButton radiofoods = findViewById(R.id.radio_foods);
        final RadioButton radionoodles = findViewById(R.id.radio_noodles);
        final RadioButton radiodrinks = findViewById(R.id.radio_drinks);
        final EditText edturl = findViewById(R.id.edturl);
        final EditText edtid = findViewById(R.id.edtid);
        final EditText edtname = findViewById(R.id.edtname);
        final EditText edtprice = findViewById(R.id.edtprice);
        Button btnupload = findViewById(R.id.btnupload);
        Button btncancel = findViewById(R.id.btncancel);
        Button btnsave = findViewById(R.id.btnsave);
        final ConnectionClass connectionClass;
        connectionClass = new ConnectionClass();

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), REQUEST_GALLERY);*/
                String url = edturl.getText().toString();
                if (url.equals("")){
                    Toast.makeText(AddActivity.this, "กรุณากรอก URL รูปภาพ", Toast.LENGTH_SHORT).show();
                }else {
                    Picasso.get().load(url)
                            .resize(200, 200)
                            .centerInside()
                            .into(imgpic);
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        String id = edtid.getText().toString().trim();
                        String name = edtname.getText().toString().trim();
                        String price1 = edtprice.getText().toString().trim();
                        if (id.equals("")){
                            message = "กรุณากรอกไอดี";
                        } else if (name.equals("")){
                            message = "กรุณากรอกชื่ออาหาร";
                        } else if (price1.equals("")){
                            message = "กรุณากรอกราคา";
                        }else{
                                int price = Integer.parseInt(edtprice.getText().toString().trim());
                              //  Bitmap image = ((BitmapDrawable) imgpic.getDrawable()).getBitmap();
                                Statement stmt1 = con.createStatement();
                                String img;
                                img = edturl.getText().toString();
                                if (radiofoods.isChecked() == true) {
                                    String strInsert = "Insert Into Foods (f_id, f_name, f_price, f_pic) Values('" + id + "','" +
                                            name + "','" + price + "','" + img + "')";
                                    stmt1.executeUpdate(strInsert);
                                    message = "ลงฐานข้อมูลเรียบร้อย";
                                } else if (radionoodles.isChecked() == true) {
                                    String strInsert = "Insert Into Noodles (n_id, n_name, n_price, n_pic) Values('" + id + "','" +
                                            name + "','" + price + "', '" + img + "')";
                                    stmt1.executeUpdate(strInsert);
                                    message = "ลงฐานข้อมูลเรียบร้อย";
                                } else if (radiodrinks.isChecked() == true) {
                                    String strInsert = "Insert Into Drinks (d_id, d_name, d_price, d_pic) Values('" + id + "','" +
                                            name + "','" + price + "','" + img + "')";
                                    stmt1.executeUpdate(strInsert);
                                    message = "ลงฐานข้อมูลเรียบร้อย";
                                }
                            }
                        imgpic.setImageResource(R.drawable.pic);
                        edturl.setText("");
                        edtid.setText("");
                        edtname.setText("");
                        edtprice.setText("");
                        }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }); //btnsave*/

    }//onCreate

  /*  public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imgpic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

}//main
