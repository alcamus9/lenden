package com.example.srinivas.lenden.objects;

import java.util.ArrayList;

/**
 * Created by srinivas on 3/3/2016.
 */
public class User {

    private String name;
    private long id;
    private String email;
    private String user_name;
    private ArrayList<Integer> contacts;
    private ArrayList<Integer> groups;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public ArrayList<Integer> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Integer> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Integer> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Integer> groups) {
        this.groups = groups;
    }

    public void addContact(int id) {
        this.contacts.add(id);
    }

    public void addGroup(int id) {
        this.groups.add(id);
    }
}
