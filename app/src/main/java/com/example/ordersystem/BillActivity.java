package com.example.ordersystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private ArrayList<ClassListBills> billsArrayList;
    private BillActivity.MyAppAdapter myAppAdapter;
    private ListView listView;
    public  int i;
    private boolean success = false;
    private ConnectionClass connectionClass;
    String table,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        listView = findViewById(R.id.listviewbill);
        connectionClass = new ConnectionClass();
        billsArrayList = new ArrayList<ClassListBills>();

        // show listview
        i = 1;
        SyncData billData = new SyncData();
        billData.execute("");
        billsArrayList.clear();


    } //onCreate
    private class SyncData extends AsyncTask<String, String, String>
    {
        String msg = "Fail";
        ProgressDialog progress;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(BillActivity.this,"Synchronising"
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
                    String query = "SELECT or_table,or_date, or_total, or_status, or_payment FROM Orders Where or_status= 'False' and or_payment= 'False'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                billsArrayList.add(new ClassListBills(rs.getString("or_table")
                                        , rs.getString("or_date"), rs.getString("or_total")
                                        , rs.getBoolean("or_status"),rs.getBoolean("or_payment")));
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
            Toast.makeText(BillActivity.this, msg + "", Toast.LENGTH_SHORT).show();
            if (success == false)
            {
            }
            else {
                try {
                    myAppAdapter = new BillActivity.MyAppAdapter(billsArrayList, BillActivity.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {

                }

            }
        }
    }

    public List<ClassListBills> parkingList;
    public Context context;
    ArrayList<ClassListBills> arrayList;

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
            BillActivity.MyAppAdapter.ViewHolder viewHolder= null;
            if (rowView == null)
            {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_bills,parent,false);
                viewHolder = new BillActivity.MyAppAdapter.ViewHolder();
                viewHolder.btnBill = rowView.findViewById(R.id.btnbill);
                viewHolder.textTable = rowView.findViewById(R.id.txttable);
                viewHolder.textDate = rowView.findViewById(R.id.txtdate);
                viewHolder.textTotal = rowView.findViewById(R.id.txttotal);
                viewHolder.textStatus = rowView.findViewById(R.id.txtstatus);
                viewHolder.textPayment = rowView.findViewById(R.id.txtpayment);
                viewHolder.checkBox = rowView.findViewById(R.id.checkbox);
                rowView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (BillActivity.MyAppAdapter.ViewHolder) convertView.getTag();
            }
            viewHolder.textTable.setText(parkingList.get(position).getTable());
            viewHolder.textDate.setText(parkingList.get(position).getDate()+"");
            viewHolder.textTotal.setText("ราคารวม " +parkingList.get(position).getTotal()+" บาท");
            viewHolder.textStatus.setText(parkingList.get(position).getStatus()+"");
            viewHolder.textPayment.setText(parkingList.get(position).getPayment()+"");


            if (viewHolder.textStatus.getText().toString().equals("false")){
            viewHolder.textStatus.setText("สถานะ: ยังไม่เสร็จ");
            }else {viewHolder.textStatus.setText("สถานะ: เสร็จสิ้น");}

            if (viewHolder.textPayment.getText().toString().equals("false")){
                viewHolder.textPayment.setText("สถานะ: ยังไม่ชำระเงิน");
            }else {viewHolder.textPayment.setText("สถานะ: ชำระเงินแล้ว");}

            final ViewHolder finalViewHolder = viewHolder;

            viewHolder.btnBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,PaymentActivity.class);
                    intent.putExtra("Table", finalViewHolder.textTable.getText().toString());
                    intent.putExtra("Date",finalViewHolder.textDate.getText().toString());
                    intent.putExtra("Total",finalViewHolder.textTotal.getText().toString());
                    intent.putExtra("Status",finalViewHolder.textStatus.getText().toString());
                    intent.putExtra("Payment",finalViewHolder.textPayment.getText().toString());
                    startActivity(intent);
                    finish();
                }
            });
            return rowView;
        }

        public class ViewHolder
        {
            TextView textTable, textDate, textTotal, textStatus, textPayment;
            Button btnBill;
            CheckBox checkBox;
        }


        public List<ClassListBills> parkingList;
        public Context context;
        ArrayList<ClassListBills> arrayList;

        private  MyAppAdapter(List<ClassListBills> apps, Context context)
        {
            this.parkingList = apps;
            this.context = context;
            arrayList = new ArrayList<ClassListBills>();
            arrayList.addAll(parkingList);
        }
    }//MyappAdapter
}
