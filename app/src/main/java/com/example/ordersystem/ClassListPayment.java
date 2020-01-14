package com.example.ordersystem;

public class ClassListPayment {
    public String table,date,name, price, count;

    public ClassListPayment(String table, String date, String name,String price,String count)
    {
        this.table = table;
        this.date = date;
        this.name = name;
        this.price = price;
        this.count = count;
    }
    public String getTable() {return table;}
    public String getDate() {return date;}
    public String getName() {return name;}
    public String getPrice() { return price; }
    public String getCount() {return count;}
}
