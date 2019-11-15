package com.example.ordersystem;

public class ClassListItems {

    public String img, name, price;


    public ClassListItems(String img, String name, String price)
    {
        this.img = img;
        this.name = name;
        this.price = price;
    }
    public String getImg() {return img;}
    public String getName() { return name; }
    public String getPrice() {return price;}

}
