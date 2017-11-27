package com.example.android.dummyrestaurant;

import java.text.DecimalFormat;

/**
 * Created by chandan on 25-11-2017.
 */

public class Transaction {
    String name,phoneNo,dateOfTransaction,amountPaid;

    public Transaction() {
    }

    public Transaction(String name, String phoneNo, String dateOfTransaction, String amountPaid) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.dateOfTransaction = dateOfTransaction;
        this.amountPaid = amountPaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }
}
