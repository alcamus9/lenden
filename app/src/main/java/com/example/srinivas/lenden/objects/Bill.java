package com.example.srinivas.lenden.objects;

import com.example.srinivas.lenden.HomePageActivity;
import com.example.srinivas.lenden.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sushantc on 3/18/16.
 */
public class Bill implements Serializable {

    ArrayList<User> participants;
    private long id;
    public User currentUser = HomePageActivity.currentUser;
    private double amount;
    private String name;
    private Map<Long, Double> contributions;//<user id, contribution>
    private HashMap<User, Double> surpluses; //<user id, surplus>



    public String getName() {
        return name;
    }

    public Map addContributions() {

        return contributions;
    }

    public double getAmount(){

        return amount;
    }

    public ArrayList<User> addParticipants(User user) {
        participants.add(user);
        return participants;
    }

    public ArrayList<User> removeParticipants(User user) {
        participants.remove(user);
        return participants;
    }

    public Bill(String name){
        this.id=utilities.randBetween(1,100000000);
        this.name=name;
    }

    public void setSurpluses(HashMap s){
        this.surpluses=s;
    }

    public HashMap<User,Double> getSurpluses() {
        return surpluses;
    }

}


//static Scanner userinput = new Scanner(System.in)