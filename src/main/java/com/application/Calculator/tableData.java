package com.application.Calculator;

public class tableData {
    public String date;
    public float payment, interest, balance;

    public tableData(String date, float payment, float interest, float balance){
        this.date = date;
        this.payment = payment;
        this.interest = interest;
        this.balance = balance;
    }

    public String getData(){
        return date;
    }

    public void setData(String date){
        this.date = date;
    }

    public void setInterest(float interest){
        this.interest = interest;
    }

    public float getInterest(){
        return interest;
    }

    public void setBalance(float balance){
        this.balance = balance;
    }

    public float getBalance(){
        return balance;
    }

    public void setPayment(float payment){
        this.payment = payment;
    }

    public float getPayment(){
        return payment;
    }
}
