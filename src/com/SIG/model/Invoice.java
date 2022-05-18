package com.SIG.model;

import java.util.ArrayList;


public class Invoice {
    private int num;
    private String date;
    private String customer;
    private ArrayList<Line> lines;

    public Invoice() {
}
    
    public Invoice(int num, String date, String customer) {
        this.num= num;
        this.date=date;
        this.customer=customer;
}
// method for calling invoices total
    public double getInvoiceTotal() {
        double total=0.0;
        for (Line line : getLines()) {
            total +=line.getLineTotal();
        }
        return total;
    }
    public ArrayList<Line> getLines() {
        if (lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + num + ", date=" + date + ", customer=" + customer + '}';
    }
    
}
