package com.example.srinivas.lenden.com.example.srinivas.lenden.objects;

import java.util.ArrayList;

/**
 * Created by srinivas on 3/3/2016.
 */
public class Group {

    private long id;
    private String name;
    private ArrayList<Long> users;
    private ArrayList<Long> transactions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Long> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Long> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Long> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Long> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
