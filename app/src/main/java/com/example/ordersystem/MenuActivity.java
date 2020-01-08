package com.example.ordersystem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import org.w3c.dom.Text;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private ArrayList<ClassListItems> itemsArrayList;
    private MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean success = false;
    ConnectionClass connectionClass;
    public LinearLayout Linear1, Linear2, Linear3, Linear4, Linear5;
    public Button Btnorder;
    public int i = 0;
    boolean clicked=false;

    int[] listviewImage = new int[]{
            R.drawable.f2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = (ListView) findViewById(R.id.listview);
        connectionClass = new ConnectionClass();
        itemsArrayList = new ArrayList<ClassListItems>();
        Btnorder = (Button) findViewById(R.id.btnorder);
        Linear1 = (LinearLayout) findViewById(R.id.Linear1);
        Linear2 = (LinearLayout) findViewById(R.id.Linear2);
        Linear3 = (LinearLayout) findViewById(R.id.Linear3);
        Linear4 = (LinearLayout) findViewById(R.id.Linear4);
        Linear5 = (LinearLayout) findViewById(R.id.Linear5);

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
                        String query = "SELECT f_pic,f_name,f_price FROM Foods";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    itemsArrayList.add(new ClassListItems(rs.getString("f_pic")
                                            , rs.getString("f_name"), rs.getString("f_price")));
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
                    }
                    // show noodles
                    else if (i == 2) {
                        String query = "SELECT n_pic,n_name,n_price FROM Noodles";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    itemsArrayList.add(new ClassListItems(rs.getString("n_pic")
                                            , rs.getString("n_name"), rs.getString("n_price")));
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
                    }
                    // show drinks
                    else if (i == 3) {
                        String query = "SELECT d_pic,d_name,d_price FROM Drinks";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    itemsArrayList.add(new ClassListItems(rs.getString("d_pic")
                                            , rs.getString("d_name"), rs.getString("d_price")));
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
                    }
                    //get item checked
                    else if (i == 4) {
                        TextView txtview = (TextView) findViewById(R.id.textView3);
                        int count = listView.getAdapter().getCount();
                        int cntForComma = 1;

                        // time
                        String pattern = "MM/dd/yyyy HH:mm:ss";
                        DateFormat df = new SimpleDateFormat(pattern);
                        Date today = Calendar.getInstance().getTime();
                        String day = df.format(today);


                        String strInsert = "Insert Into Orders_Details (ord_date, ord_table, ord_name, ord_price, ord_count) Values('";
                        txtview.setText(strInsert);
                        for (int i = 0; i < count; i++) {
                            LinearLayout itemLayout = (LinearLayout) listView.getChildAt(i);
                            EditText textTable = (EditText) findViewById(R.id.txttable);
                            CheckBox checkBox = (CheckBox) itemLayout.findViewById(R.id.checkbox);
                            TextView textName = (TextView) itemLayout.findViewById(R.id.txtname);
                            TextView textPrice = (TextView) itemLayout.findViewById(R.id.txtprice);
                            EditText textCount = (EditText) itemLayout.findViewById(R.id.txtcount);
                            txtview.setText("b 4 if1");
                            if (checkBox.isChecked())
                            {
                                if (cntForComma > 1) {
                                    strInsert = strInsert.toString() + ",('";
                                    txtview.setText("ifcom1");
                                };
                                txtview.setText("af com if1 str1 = " + strInsert.toString());
                            //    Log.d("Item "+String.valueOf(i), checkBox.getTag().toString());
                                strInsert = strInsert.toString()+ day +"','" + textTable.getText()+"','" + textName.getText()+"',"+ textPrice.getText()+"," + textCount.getText()+")";
                                txtview.setText("af com if1 str2 = " + strInsert.toString());
                                cntForComma += 1;
                                Statement stmt1 = con.createStatement();
                                stmt1.executeUpdate(strInsert);

                            }
                        } //for
                        txtview.setText("af 4");
                    }
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

         public List<ClassListItems> parkingList;
         public Context context;
         ArrayList<ClassListItems> arrayList;

        public class MyAppAdapter extends BaseAdapter
        {
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
                Picasso.with(context).load(parkingList.get(position).getImg()).into(viewHolder.imageView);

             /*   viewHolder.checkBox.setChecked(false);
                viewHolder.checkBox.setTag(position);
                int selected = viewHolder.checkBox.isChecked(s);
                if(selected.indexOf(parkingList.get(position).toString()) >= 0)) // <-- ตัวโค้ดนี้ไม่เข้าใจ
                {
                    viewHolder.checkBox.setChecked(true);
                }  //แก้ Scroll Bar กด Checked ที่เลือกเมนู แล้วไม่เลื่อนไปกดโดนตัวอื่น (ยังไม่สามารถแก้ได้)*/

                return rowView;
            } // View getView



            public class ViewHolder
            {
              TextView textName, textPrice;
              EditText textCount;
              ImageView imageView;
              CheckBox checkBox;
            }


            public List<ClassListItems> parkingList;
            public Context context;
            ArrayList<ClassListItems> arrayList;


            private  MyAppAdapter(List<ClassListItems> apps, Context context)
            {
                this.parkingList = apps;
                this.context = context;
                arrayList = new ArrayList<ClassListItems>();
                arrayList.addAll(parkingList);
            }
        }

    public void openList() {
        Intent intent = new Intent(this, ListActivity.class);
        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("Uniqid");
        if (text.equals("From_Activity_A")){
            intent.putExtra("Uniqid","From_Activity_A");
        }else{
        intent.putExtra("Uniqid","From_Activity_B");}
        startActivity(intent);
    } // เปลี่ยนหน้าไปรายการ

    public void openBill() {
        Intent intent = new Intent(this, BillActivity.class);
        startActivity(intent);
    } // เปลี่ยนหน้าไปบิล

} //main
