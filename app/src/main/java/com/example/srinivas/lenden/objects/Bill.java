package com.example.srinivas.lenden.objects;

import com.example.srinivas.lenden.HomePageActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sushantc on 3/18/16.
 */
public class Bill implements Serializable {

    ArrayList<User> participants;
    public User currentUser = HomePageActivity.currentUser;
    private double amount;
    private String description;
    private Map<Long, Double> contributions;//<user id, contribution>
    private Map<Long, Double> surpluses; //<user id, surplus>


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

    public Bill(String desc, User user) {
        this.description=desc;
        this.participants.add(currentUser);
        this.addParticipants(user);
        this.addContributions();
        this.getSurplusesFromContributions();
    }


    public Map getSurplusesFromContributions() {

        for (int i=0; i<participants.size(); i++){
            long userId=participants.get(i).getId();

            double surplus= contributions.get(userId) - (amount/participants.size());
            surpluses.put(userId, surplus);

        }
        return surpluses;
    }
}


//static Scanner userinput = new Scanner(System.in)