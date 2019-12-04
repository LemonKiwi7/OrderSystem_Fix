package com.example.ordersystem;

public class ClassListBills {

    public String table, date, total;
    public boolean status;

    public ClassListBills(String table, String date, String total, boolean status)
    {
        this.table = table;
        this.date = date;
        this.total = total;
        this.status = status;
    }
    public String getTable() {return table;}
    public String getDate() { return date; }
    public String getTotal() {return total;}
    public boolean getStatus() {return status;}

}
