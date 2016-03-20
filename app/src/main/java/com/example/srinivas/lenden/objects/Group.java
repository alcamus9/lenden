package com.example.srinivas.lenden.objects;

import com.example.srinivas.lenden.HomePageActivity;
import com.example.srinivas.lenden.utilities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by srinivas on 3/3/2016.
 */
public class Group implements Serializable {

    private long id;
    private String name;
    private ArrayList<Long> users;
    private ArrayList<User> userObjects;
    private ArrayList<Bill> bills;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = utilities.randBetween(1, 100000000);
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
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

    public Group(long id, String name, ArrayList<Long> users) {
        this.id=id;
        this.name = name;
        this.users = users;
    }

    public Group(String name){
        this.id=utilities.randBetween(1, 100000000);
        this.name=name;
        this.userObjects= new ArrayList<>();
        this.userObjects.add(HomePageActivity.currentUser);
    }

    public Group addMember(User user){
        this.userObjects.add(user);
        return this;
    }

    public ArrayList<User> getUserObjects(){
        return userObjects;
    }


}
