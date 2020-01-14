package com.example.ordersystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private ArrayList<ClassListPayment> paymentArrayList;
    private PaymentActivity.MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean success = false;
    public int i;
    private ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        listView = (ListView) findViewById(R.id.listviewpayment);
        connectionClass = new ConnectionClass();
        paymentArrayList = new ArrayList<ClassListPayment>();
        i = 1;
        SyncData orderData = new SyncData();
        orderData.execute("");

    }//onCreate

    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg = "Fail";
        ProgressDialog progress;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(PaymentActivity.this,"Synchronising"
                    ,"Listview loading!", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                final Connection con = connectionClass.CONN();
                if (con == null) {
                    success = false;
                    // show bills
                } else if (i==1) {
                    String query = "SELECT ord_table, ord_date, ord_name, ord_price, ord_count FROM Orders_Details";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                paymentArrayList.add(new ClassListPayment(rs.getString("ord_table")
                                        , rs.getString("ord_date"), rs.getString("ord_name"), rs.getString("ord_price")
                                        , rs.getString("ord_count")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "พบรายการสั่งอาหาร";
                        success = true;

                    } else {
                        msg = "ไม่พบรายการสั่งอาหาร";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg)
        {
            progress.dismiss();
            Toast.makeText(PaymentActivity.this, msg + "", Toast.LENGTH_SHORT).show();
            if (success == false)
            {
            }
            else {
                try {
                    myAppAdapter = new PaymentActivity.MyAppAdapter(paymentArrayList, PaymentActivity.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public List<ClassListPayment> parkingList;
    public Context context;
    ArrayList<ClassListPayment> arrayList;

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
        public View getView(final int position, final View convertView, ViewGroup parent) {

            View rowView = convertView;
            PaymentActivity.MyAppAdapter.ViewHolder viewHolder= null;
            if (rowView == null)
            {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_payment,parent,false);
                viewHolder = new PaymentActivity.MyAppAdapter.ViewHolder();
                viewHolder.textName = rowView.findViewById(R.id.txtname);
                viewHolder.textPrice = rowView.findViewById(R.id.txtprice);
                viewHolder.textCount = rowView.findViewById(R.id.txtcount);
                rowView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (PaymentActivity.MyAppAdapter.ViewHolder) convertView.getTag();
            }
            viewHolder.textTable.setText("โต๊ะที่ " + parkingList.get(position).getTable());
            viewHolder.textDate.setText("เวลา " + parkingList.get(position).getDate());
            viewHolder.textName.setText(parkingList.get(position).getName()+"");
            viewHolder.textPrice.setText("ราคา " + parkingList.get(position).getPrice() +" บาท");
            viewHolder.textCount.setText(parkingList.get(position).getCount() +" จำนวน");
            return rowView;
        }

        public class ViewHolder
        {
            TextView textTable, textDate, textName, textPrice, textCount;

        }


        public List<ClassListPayment> parkingList;
        public Context context;
        ArrayList<ClassListPayment> arrayList;

        private  MyAppAdapter(List<ClassListPayment> apps, Context context)
        {
            this.parkingList = apps;
            this.context = context;
            arrayList = new ArrayList<ClassListPayment>();
            arrayList.addAll(parkingList);
        }
    }//MyappAdapter

}
