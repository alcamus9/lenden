package com.example.srinivas.lenden.objects;

import com.example.srinivas.lenden.HomePageActivity;
import com.example.srinivas.lenden.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Group(String name) {
        this.id = utilities.randBetween(1, 100000000);
        this.name = name;
        this.userObjects = new ArrayList<>();
        this.userObjects.add(HomePageActivity.currentUser);
    }

    public Group addMember(User user) {
        this.userObjects.add(user);
        return this;
    }

    public Group addBill(Bill bill) {
        if (this.bills == null)
            this.bills = new ArrayList<>();
        this.bills.add(bill);
        return this;
    }

    public ArrayList<User> getUserObjects() {
        return userObjects;
    }

    public void setBill(Bill b, int index) {
        this.bills.set(index, b);
    }

    public HashMap<User,Double> groupSplit() {
        Map<User, Double> groupSurpluses = new HashMap<>();
        for (int i = 0; i < this.userObjects.size(); i++) {
            groupSurpluses.put(this.userObjects.get(i), 0.0);
        }

        for (int i = 0; i < this.bills.size(); i++) {
            Map<User, Double> map = this.getBills().get(i).getSurpluses();

            for (User u : map.keySet()) {
                Double bal = groupSurpluses.get(u);
                bal += map.get(u);
                groupSurpluses.put(u, bal);
            }
        }
        // display groupSurpluses here if you want

        // use groupSurpluses to give settlement instructions
        Map<User, Double> surplusMap = new HashMap<>();
        Map<User, Double> deficitMap = new HashMap<>();
        for (User u : groupSurpluses.keySet()) {
            if (groupSurpluses.get(u) >= 0) {
                surplusMap.put(u, groupSurpluses.get(u));
            } else {
                deficitMap.put(u, groupSurpluses.get(u));
            }
        }

        //now we have 2 more maps, surplusMap and deficitMap

        ArrayList<HashMap<User,Double>> userBalanceMaps = new ArrayList<>();
        for(int i=0; i<this.userObjects.size(); i++){
            HashMap<User,Double> map = new HashMap<>();
            for (int j=0; j<this.userObjects.size(); j++){
                map.put(this.userObjects.get(j),0.0);
            }
            userBalanceMaps.add(map);
        }

/*        for (User u:surplusMap.keySet()){
            for(User v:deficitMap.keySet()){
                    if (Math.abs(surplusMap.get(u)) < Math.abs(deficitMap.get(v))) {
                        int uIndex=this.userObjects.indexOf(u);
                        int i=uIndex;
                        while(deficitMap.get(v)<0.0) {
                            userBalanceMaps.get(i).put(v, surplusMap.get(u));
                            userBalanceMaps.get(this.userObjects.indexOf(v)).put(u, -surplusMap.get(u));
                            deficitMap.put(v, deficitMap.get(v) + surplusMap.get(u));
                            surplusMap.put(u, 0.0);
                            i++;
                        }
                    }
                    else {
                        userBalanceMaps.get(this.userObjects.indexOf(u)).put(v, -deficitMap.get(v));
                        userBalanceMaps.get(this.userObjects.indexOf(v)).put(u, deficitMap.get(v));
                        surplusMap.put(u, surplusMap.get(u) + deficitMap.get(v));
                        deficitMap.put(v, 0.0);
                    }
            }
        }*/

        for (User u:surplusMap.keySet()) {
            while(surplusMap.get(u) > 0.0) {
                Collection<Double> values = deficitMap.values();
                int index = 0;
                User user=null;
                for (;index<values.size(); index++) {
                    user = (User) deficitMap.keySet().toArray()[index];
                    if(deficitMap.get(user) < 0.0) {
                        break;
                    }
                }
                int u_index = this.userObjects.indexOf(u);
                int v_index = this.userObjects.indexOf(user);
                if (Math.abs(surplusMap.get(u)) < Math.abs(deficitMap.get(user))) {
                        userBalanceMaps.get(u_index).put(user, surplusMap.get(u));
                        userBalanceMaps.get(v_index).put(u, -surplusMap.get(u));
                        deficitMap.put(user, deficitMap.get(user) + surplusMap.get(u));
                        surplusMap.put(u, 0.0);
                }
                else {
                    userBalanceMaps.get(u_index).put(user, -deficitMap.get(user));
                    userBalanceMaps.get(v_index).put(u, deficitMap.get(user));
                    surplusMap.put(u, surplusMap.get(u) + deficitMap.get(user));
                    deficitMap.put(user, 0.0);
                }

            }
        }
        return userBalanceMaps.get(this.userObjects.indexOf(HomePageActivity.currentUser));
    }

}



