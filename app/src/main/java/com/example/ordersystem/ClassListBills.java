package com.example.ordersystem;

public class ClassListBills {

    public String table, date, total;
    public boolean status, payment;

    public ClassListBills(String table, String date, String total, boolean status, boolean payment)
    {
        this.table = table;
        this.date = date;
        this.total = total;
        this.status = status;
        this.payment = payment;
    }
    public String getTable() {return table;}
    public String getDate() { return date; }
    public String getTotal() {return total;}
    public boolean getStatus() {return status;}
    public boolean getPayment() {return payment;}

}
