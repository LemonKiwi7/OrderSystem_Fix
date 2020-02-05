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
import android.widget.EditText;
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
import java.util.Timer;
import java.util.TimerTask;

public class ReportActivity extends AppCompatActivity {

    private ArrayList<ClassListReport> ReportArrayList;
    private ReportActivity.MyAppAdapter myAppAdapter;
    private ListView listView;
    private boolean success = false;
    private Button btnshow;
    private TextView textTotal;
    private ConnectionClass connectionClass;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        textTotal = (TextView) findViewById(R.id.txttotal);
        btnshow = findViewById(R.id.btnshow);
        listView = findViewById(R.id.listviewreport);
        connectionClass = new ConnectionClass();
        ReportArrayList = new ArrayList<ClassListReport>();


        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                ReportActivity.SyncData orderData = new ReportActivity.SyncData();
                orderData.execute("");
                ReportArrayList.clear();

            }
        });

    }// onCreate

        private class SyncData extends AsyncTask<String, String, String>
        {
            String msg = "Fail";
            ProgressDialog progress;
            @Override
            protected void onPreExecute()
            {
                progress = ProgressDialog.show(ReportActivity.this,"Synchronising"
                        ,"Listview loading!", true);
            }
            @Override
            protected String doInBackground(String... strings) {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        success = false;

                        // show report
                    } else if (i==1) {
                        EditText Date = (EditText) findViewById(R.id.edtdate);
                        String date = Date.getText().toString().trim();
                        String query = "SELECT ord_name, ord_price, ord_count FROM Orders_Details Where ord_date like " + "'%" + date + "%'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs != null) {
                            while (rs.next()) {
                                try {
                                    ReportArrayList.add(new ClassListReport(rs.getString("ord_name"), rs.getString("ord_price")
                                            , rs.getString("ord_count")));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            msg = "พบรายการข้อมูล";
                            success = true;

                        } else {
                            msg = "ไม่พบรายการข้อมูล";
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
                Toast.makeText(ReportActivity.this, msg + "", Toast.LENGTH_SHORT).show();
                if (success == false)
                {
                }
                else {
                    try {
                        myAppAdapter = new ReportActivity.MyAppAdapter(ReportArrayList, ReportActivity.this);
                        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listView.setAdapter(myAppAdapter);
                    } catch (Exception ex)
                    {

                    }

                }
            }
        }// SyncData

        public class MyAppAdapter extends BaseAdapter
        {
            public List<ClassListReport> ReportList;
            public Context context;
            ArrayList<ClassListReport> arrayList;

            public class ViewHolder
            {
                TextView textName, textPrice, textCount;

            }

            private  MyAppAdapter(List<ClassListReport> apps, Context context)
            {
                this.ReportList = apps;
                this.context = context;
                arrayList = new ArrayList<ClassListReport>();
                arrayList.addAll(ReportList);
            }

            @Override
            public int getCount() {
                return ReportList.size();
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
                ReportActivity.MyAppAdapter.ViewHolder viewHolder= null;
                if (rowView == null)
                {
                    LayoutInflater inflater = getLayoutInflater();
                    rowView = inflater.inflate(R.layout.list_report,parent,false);
                    viewHolder = new ReportActivity.MyAppAdapter.ViewHolder();
                    viewHolder.textName = rowView.findViewById(R.id.txtname);
                    viewHolder.textPrice = rowView.findViewById(R.id.txtprice);
                    viewHolder.textCount = rowView.findViewById(R.id.txtcount);
                    rowView.setTag(viewHolder);
                }
                else
                {
                    viewHolder = (ReportActivity.MyAppAdapter.ViewHolder) convertView.getTag();
                }
                int p, c, total, result = 0;
                String t = "";
                for (int i = 0; i < ReportArrayList.size(); i++) {
                    p = Integer.parseInt((ReportList.get(i).getPrice()));
                    c = Integer.parseInt((ReportList.get(i).getCount()));
                    total = p * c;
                    result += total;
                }
                t = String.valueOf(result);
                textTotal.setText("ยอดขายทั้งหมด " + t + " บาท");

                viewHolder.textName.setText(ReportList.get(position).getName()+"");
                viewHolder.textPrice.setText("ราคา " + ReportList.get(position).getPrice() +" บาท");
                viewHolder.textCount.setText(ReportList.get(position).getCount() +" จำนวน");
                return rowView;
            }
        }//MyappAdapter
}//main ReportActivity
