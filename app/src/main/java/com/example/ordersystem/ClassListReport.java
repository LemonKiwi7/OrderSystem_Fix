package com.example.ordersystem;

public class ClassListReport {
    public String name, price, count;

    public ClassListReport(String name,String price,String count)
    {
        this.name = name;
        this.price = price;
        this.count = count;
    }
    public String getName() {return name;}
    public String getPrice() { return price; }
    public String getCount() {return count;}
}
