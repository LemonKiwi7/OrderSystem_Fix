package com.example.ordersystem;

public class ClassListItems {

    public String name, price, img;
    public boolean isChecked;



    public ClassListItems(String name, String price, String img)
    {
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public String getName() { return name; }
    public String getPrice() {return price;}
    public String getImage() {return img;}



}
