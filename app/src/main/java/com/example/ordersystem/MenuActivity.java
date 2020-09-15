package com.example.ordersystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private ArrayList<ClassListItems> itemsArrayList;
    private MyAppAdapter myAppAdapter;
    public ListView listView;
    private boolean success = false;
    ConnectionClass connectionClass;
    public LinearLayout Linear1, Linear2, Linear3, Linear4, Linear5;
    public Button Btnorder, Btnexit;
    public int i = 0;
    boolean clicked=false;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        listView = findViewById(R.id.listview);
        connectionClass = new ConnectionClass();
        itemsArrayList = new ArrayList<ClassListItems>();
        Btnorder = findViewById(R.id.btnorder);
        Btnexit = findViewById(R.id.btnexit);
        imageView = findViewById(R.id.imageView);
        Linear1 = findViewById(R.id.Linear1);
        Linear2 = findViewById(R.id.Linear2);
        Linear3 = findViewById(R.id.Linear3);
        Linear4 = findViewById(R.id.Linear4);
        Linear5 = findViewById(R.id.Linear5);

        Linear1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clicked =true;
                if (clicked=true){
                v.setBackgroundResource(R.color.cornflowerblue);
                    Linear2.setBackgroundResource(R.color.midnightblue);
                    Linear3.setBackgroundResource(R.color.midnightblue);
                    Linear4.setBackgroundResource(R.color.midnightblue);
                    Linear5.setBackgroundResource(R.color.midnightblue);
                    i = 1;
                    SyncData orderData = new SyncData();
                    orderData.execute("");
                    itemsArrayList.clear();

                }
            }
        });

        Linear2.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                clicked =true;
                if (clicked=true){
                    v.setBackgroundResource(R.color.cornflowerblue);
                    Linear1.setBackgroundResource(R.color.midnightblue);
                    Linear3.setBackgroundResource(R.color.midnightblue);
                    Linear4.setBackgroundResource(R.color.midnightblue);
                    Linear5.setBackgroundResource(R.color.midnightblue);
                    i = 2;
                    SyncData orderData = new SyncData();
                    orderData.execute("");
                    itemsArrayList.clear();
                }
            }
        });

        Linear3.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                clicked =true;
                if (clicked=true){
                    v.setBackgroundResource(R.color.cornflowerblue);
                    Linear1.setBackgroundResource(R.color.midnightblue);
                    Linear2.setBackgroundResource(R.color.midnightblue);
                    Linear4.setBackgroundResource(R.color.midnightblue);
                    Linear5.setBackgroundResource(R.color.midnightblue);
                    i = 3;
                    SyncData orderData = new SyncData();
                    orderData.execute("");
                    itemsArrayList.clear();
                }
            }
        });

        Linear4.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                clicked =true;
                if (clicked=true){
                    v.setBackgroundResource(R.color.cornflowerblue);
                    Linear1.setBackgroundResource(R.color.midnightblue);
                    Linear2.setBackgroundResource(R.color.midnightblue);
                    Linear3.setBackgroundResource(R.color.midnightblue);
                    Linear5.setBackgroundResource(R.color.midnightblue);
                    clicked =false;
                    openBill();
                }
            }
        });

        Linear5.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                clicked =true;
                if (clicked=true){
                    v.setBackgroundResource(R.color.cornflowerblue);
                    Linear1.setBackgroundResource(R.color.midnightblue);
                    Linear2.setBackgroundResource(R.color.midnightblue);
                    Linear3.setBackgroundResource(R.color.midnightblue);
                    Linear4.setBackgroundResource(R.color.midnightblue);
                    clicked =false;
                    openList();
                }
            }
        });

        Btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 4;
                SyncData orderData = new SyncData();
                orderData.execute("");
            }
        });

        Btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adb.setTitle("กล่องข้อความ");
                adb.setMessage("ต้องการออกจากระบบ?");
                adb.setPositiveButton("ยกเลิก", null);
                adb.setNegativeButton("ตกลง", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        openLogin();
                        Toast.makeText(MenuActivity.this,"ออกจากระบบเรียบร้อย" ,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                adb.show();
            }
        });
    } // Public onCreate


    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg = "Fail";
        ProgressDialog progress;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MenuActivity.this,"Synchronising"
                    ,"Listview loading!", true);
        }

        @Override
        protected String doInBackground(String... strings) {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        success = false;
                        // show foods
                    } else if (i == 1) {
                        String query = "SELECT f_name,f_price, f_pic FROM Foods";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    itemsArrayList.add(new ClassListItems(rs.getString("f_name"), rs.getString("f_price")
                                            ,rs.getString("f_pic")));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            msg = "พบข้อมูลอาหาร";
                            success = true;

                        } else {
                            msg = "ไม่พบข้อมูลอาหาร";
                            success = false;
                        }
                    }// else if i1

                    // show noodles
                    else if (i == 2) {
                        String query = "SELECT n_name,n_price,n_pic FROM Noodles";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    itemsArrayList.add(new ClassListItems(rs.getString("n_name"), rs.getString("n_price")
                                            ,rs.getString("n_pic")));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            msg = "พบข้อมูลอาหาร";
                            success = true;

                        } else {
                            msg = "ไม่พบข้อมูลอาหาร";
                            success = false;
                        }
                    }// else if i2

                    // show drinks
                    else if (i == 3) {
                        String query = "SELECT d_name,d_price,d_pic FROM Drinks";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    itemsArrayList.add(new ClassListItems(rs.getString("d_name"), rs.getString("d_price")
                                            ,rs.getString("d_pic")));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            msg = "พบข้อมูลอาหาร";
                            success = true;

                        } else {
                            msg = "ไม่พบข้อมูลอาหาร";
                            success = false;
                        }
                    }// else if i3

                    //get item checked
                    else if (i == 4) {
                        EditText textTable = findViewById(R.id.txttable);
                        EditText textCount2 = findViewById(R.id.txtcount);
                        int count = listView.getAdapter().getCount();
                        int cntForComma = 1;
                        int total = 0;
                        int resultall = 0;
                        int status = 0;
                        int payment = 0;
                        String t = textTable.getText().toString();
                        String c = textCount2.getText().toString();
                        //time and str
                        String pattern = "MM/dd/yyyy HH:mm:ss";
                        DateFormat df = new SimpleDateFormat(pattern);
                        Date today = Calendar.getInstance().getTime();
                        String day = df.format(today);
                        String strInsert = "Insert Into Orders_Details (ord_table, ord_date, ord_name, ord_price, ord_count) Values('";
                                    for (int i = 0; i < count; i++) {
                                        try{
                                        ConstraintLayout itemLayout = (ConstraintLayout) listView.getChildAt(i);
                                        CheckBox checkBox = itemLayout.findViewById(R.id.checkbox);
                                        TextView textName = itemLayout.findViewById(R.id.txtname);
                                        TextView textPrice = itemLayout.findViewById(R.id.txtprice);
                                        EditText textCount = itemLayout.findViewById(R.id.txtcount);
                                        String price = textPrice.getText().toString();
                                        String count2 = textCount.getText().toString();
                                        int result = Integer.valueOf(price.trim());
                                        int result2 = Integer.valueOf(count2.trim());
                                        if (checkBox.isChecked()) {
                                            //    Log.d("Item "+String.valueOf(i), checkBox.getTag().toString());
                                            if (cntForComma > 1) {
                                                strInsert = strInsert + ",('";
                                            }
                                            strInsert = strInsert + textTable.getText() + "','" + day + "','" + textName.getText() + "'," + textPrice.getText() + "," + textCount.getText() + ")";
                                            cntForComma += 1;
                                            resultall = result * result2;
                                            total += resultall;
                                            Statement stmt1 = con.createStatement();
                                            stmt1.executeUpdate(strInsert);
                                            String strInsert2 = "Insert Into Orders (or_table, or_date, or_total, or_status, or_payment) Values('" + textTable.getText() + "','" + day + "'," + total + "," + status + "," + payment + ")";
                                            Statement stmt2 = con.createStatement();
                                            stmt2.executeUpdate(strInsert2);
                                        }//if checkbox
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                    }
                                } // for
                                     msg = "บันทึกลงบิลเรียบร้อย";
                                     success = true;
                    }// else if i4

               } catch (Exception e) {
                    e.printStackTrace();
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    msg = writer.toString();
                    success = false;
                }
                return msg;
        } //doInBackground

        @Override
        protected void onPostExecute(String msg)
        {
            progress.dismiss();
            Toast.makeText(MenuActivity.this, msg + "", Toast.LENGTH_SHORT).show();
            if (success == false)
            {
            }
            else {
                try {
                    myAppAdapter = new MyAppAdapter(itemsArrayList, MenuActivity.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }
            }
        } //onPostExecute
    } // SyncData

        public class MyAppAdapter extends BaseAdapter
        {
            public List<ClassListItems> parkingList;
            public Context context;
            ArrayList<ClassListItems> arrayList;
            String[] mainMenuItems;
            ArrayList positionArray;

            public class ViewHolder
            {
                TextView textName, textPrice;
                EditText textCount;
                ImageView imageView;
                CheckBox checkBox;
            }

            private  MyAppAdapter(List<ClassListItems> apps, Context context)
            {
                this.parkingList = apps;
                this.context = context;
                arrayList = new ArrayList<ClassListItems>();
                arrayList.addAll(parkingList);
            }

            @Override
            public int getCount() {
                return parkingList.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View rowView = convertView;
                ViewHolder viewHolder= null;
                if (rowView == null)
                {
                    LayoutInflater inflater = getLayoutInflater();
                    rowView = inflater.inflate(R.layout.list_foods,parent,false);
                    viewHolder = new ViewHolder();
                    viewHolder.textName = rowView.findViewById(R.id.txtname);
                    viewHolder.textPrice = rowView.findViewById(R.id.txtprice);
                    viewHolder.imageView = rowView.findViewById(R.id.imageView);
                    viewHolder.textCount = rowView.findViewById(R.id.txtcount);
                    viewHolder.checkBox = rowView.findViewById(R.id.checkbox);
                    rowView.setTag(viewHolder);
                }
                else
                {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.textName.setText(parkingList.get(position).getName()+"");
                viewHolder.textPrice.setText(parkingList.get(position).getPrice() +"");
                Picasso.get().load(parkingList.get(position).getImage())
                        .resize(600,400)
                        .centerInside()
                        .into(viewHolder.imageView);

             /*   viewHolder.checkBox.setChecked(false);
                viewHolder.checkBox.setTag(position);
                int selected = viewHolder.checkBox.isChecked(i);
                if(selected.indexOf(parkingList.get(position).toString()) >= 0)) // <-- ตัวโค้ดนี้ไม่เข้าใจ
                {
                    viewHolder.checkBox.setChecked(true);
                }  //แก้ Scroll Bar กด Checked ที่เลือกเมนู แล้วไม่เลื่อนไปกดโดนตัวอื่น (ยังไม่สามารถแก้ได้)*/

             /*   viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) { // ตำแหน่งนี้ถูกคลิกที่หน้าจอ
                            positionArray.set(position, true);  // ปรับค่าตำแหน่งนี้ (ที่จะใช้อ้างถึง checkbox ให้เป็น true
                        } else {
                            positionArray.set(position, false);
                        }
                    }
                });*/

                return rowView;
            } // View getView
        } // MyAppAdapter

    public void openLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    } // เปลี่ยนหน้าไปบิล

    public void openList() {
        Intent intent = new Intent(this, ListActivity.class);
        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("Rank");
        if (text.equals("Boss")){
            intent.putExtra("Rank","Boss");
        }else{
        intent.putExtra("Rank","Employee");}
        startActivity(intent);
    } // เปลี่ยนหน้าไปรายการ

    public void openBill() {
        Intent intent = new Intent(this, BillActivity.class);
        startActivity(intent);
    } // เปลี่ยนหน้าไปบิล



} //main
