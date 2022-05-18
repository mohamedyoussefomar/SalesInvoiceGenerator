package com.SIG.model;
public class Line {
   private int num;
   private String item;
   private double price;
   private int count;
   private Invoice invoice;
   
    public Line() {
    }

    public Line(int num, String item, double price, int count, Invoice invoice) {
        this.num = num;
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }
    
    public double getLineTotal() {
        return price * count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + num + ", item=" + item + ", price=" + price + ", count=" + count + '}';
    }
}
