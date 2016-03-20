package com.example.srinivas.lenden.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by srinivas on 3/3/2016.
 */
public class User implements Serializable {

    private String name;
    private long id;
    private String email;
    private String user_name;
    private ArrayList<Long> contacts;
    private ArrayList<Long> groups;
    private String fb_profile_id;
    private String phone_number;
    private String image_path;

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }


    public String getFb_profile_id() {
        return fb_profile_id;
    }

    public void setFb_profile_id(String fb_profile_id) {
        this.fb_profile_id = fb_profile_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

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

    public String get_user_name() {
        return user_name;
    }

    public void set_user_name(String user_name) {
        this.user_name = user_name;
    }

    public ArrayList<Long> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Long> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Long> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Long> groups) {
        this.groups = groups;
    }

    public void addContact(Integer id) {
        this.contacts.add((long)id);
    }

    public void addGroup(Integer id) {
        this.groups.add((long)id);
    }

    public User(Long id, String user_name, String password, String name, String email_d,
                String phone_number, String fb_profile_id, ArrayList<Long> contacts,
                ArrayList<Long> groups) {
        this.id = id;
        this.user_name = user_name;
        this.password = password;
        this.name = name;
        this.email = email_d;
        this.phone_number = phone_number;
        this.fb_profile_id = fb_profile_id;
        this.contacts = contacts;
        this.groups = groups;
        this.image_path = "i" + ((Long)this.id).toString();
    }

    public User(Long id, String user_name, String name, String email_d,
                String phone_number) {
        this.id = id;
        this.user_name = user_name;
        this.name = name;
        this.email = email_d;
        this.phone_number = phone_number;
        this.image_path = "i" + ((Long)this.id).toString();
    }

    public User() {

    }

}
