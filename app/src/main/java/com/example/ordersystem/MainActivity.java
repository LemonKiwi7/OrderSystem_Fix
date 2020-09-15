package com.example.ordersystem;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText edtuserid, edtpass;
    Button btnlogin;
    ProgressBar pbbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass();
        edtuserid = findViewById(R.id.editUser);
        edtpass = findViewById(R.id.editPw);
        btnlogin = findViewById(R.id.btn_login);
        pbbar = findViewById(R.id.progressBar);
        pbbar.setVisibility(View.GONE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");
            }
        });
    }//onCreate

    public void openActivity1() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("Rank","Boss");
        startActivity(intent);
        finish();
    }

    public void openActivity2() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("Rank","Employee");
        startActivity(intent);
        finish();
    }

    public class DoLogin extends AsyncTask<String, String, String> {
        String message ="";
        String rank ="";
        Boolean isSuccess = false;
        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();


        @Override
        protected  void onPreExecute(){
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r){
            pbbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();

            if(isSuccess){
                Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("")||password.trim().equals("")) {
                message = "กรุณาใส่ User ID และ Password ให้ถูกต้อง";
            } else {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        message = "Error in connection with SQL Sever";
                    } else {
                        String query = "select * from Employees where em_id = '" + userid + "' and em_password='" + password + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {
                            String username = rs.getString("em_fname") + " " + rs.getString("em_lname");
                            rank = rs.getString("em_rank");
                            message = "ล็อคอินสำเร็จ ยินดีต้อนรับคุณ" + username + "";
                            isSuccess = true;
                            edtuserid.setText("");
                            edtpass.setText("");
                            if (rank.trim().equals("1")){
                                openActivity1();
                            }else{
                            openActivity2();}
                        } else {
                            edtpass.setText("");
                            message = "ไม่สามารถเข้าสู่ระบบได้";
                            isSuccess = false;
                        }
                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    message = ex.toString() + "Exceptions";
                }
            }
            return message;
        } //doInBackground
    }//DoLogin
}//main
