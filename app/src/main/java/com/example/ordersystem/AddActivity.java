package com.example.ordersystem;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class AddActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    public static final int REQUEST_GALLERY = 1;
    String message = "";
    Bitmap bitmap;
    ImageView imgpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        connectionClass = new ConnectionClass();
        imgpic = (ImageView) findViewById(R.id.imgpic);
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

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), REQUEST_GALLERY);
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
                        if(radiofoods.isChecked() == true) {
                            String strInsert = "Insert Into Foods (f_id, f_name, f_price, f_pic) Values('" + id + "','" +
                                    name + "','" + price + "','" + image + "')";
                            stmt1.executeUpdate(strInsert);
                            message = "ลงฐานข้อมูลเรียบร้อย";
                        }
                        else if (radionoodles.isChecked() == true) {
                            String strInsert = "Insert Into Noodles (n_id, n_name, n_price, n_pic) Values('" + id + "','" +
                                    name + "','" + price +"', '" + image + "')";
                            stmt1.executeUpdate(strInsert);
                            message = "ลงฐานข้อมูลเรียบร้อย";
                        }
                        else if (radiodrinks.isChecked() == true) {
                            String strInsert = "Insert Into Drinks (d_id, d_name, d_price, d_pic) Values('" + id + "','" +
                                    name + "','" + price + "','" + image + "')";
                            stmt1.executeUpdate(strInsert);
                            message = "ลงฐานข้อมูลเรียบร้อย";
                        }
                    }
                } catch (Exception ex) {
                    message = "Exceptions \n" + ex;
                }
                Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                edtid.setText("");
                edtname.setText("");
                edtprice.setText("");
            }
        }); //btnsave*/

    }//onCreate

    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
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
    }

}//main
