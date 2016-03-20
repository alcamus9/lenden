package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.srinivas.lenden.dbrequests.ContactDetailsRequest;
import com.example.srinivas.lenden.dbrequests.GroupDetailRequest;
import com.example.srinivas.lenden.dbrequests.TransactionsRequest;
import com.example.srinivas.lenden.dbrequests.UserDetailsRequest;
import com.example.srinivas.lenden.objects.Group;
import com.example.srinivas.lenden.objects.Transaction;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.lenden.requests.AsyncRequestListener;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by srinivas on 3/2/2016.
 */
public class HomePageActivity extends AppCompatActivity implements AsyncRequestListener {

    ListView feedView;
    ListView reminderView;
    public static User currentUser;
    private Long userId;
    private User user;
    private ArrayList<User> contacts;
    private ArrayList<Transaction> transactions;
    private ArrayList<Group> groups;
    private HashMap<Long, User> user_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.userId = getIntent().getLongExtra("userId", 0);
        this.getUserDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void open_pay_window(View view) {
        Intent pay_activity = new Intent(this, PayReceiveActivity.class);
        startActivity(pay_activity);
    }

    public void open_contacts_window(View view) {
        Intent contacts_activity = new Intent(this, ContactsActivity.class);
        startActivity(contacts_activity);
    }

    public void open_groups_window(View view) {
        Intent group_activity = new Intent(this, GroupsActivity.class);
        startActivity(group_activity);
    }

    @Override
    public void onResponseReceived(String name, HashMap response) {
        if(name.equals("UserDetailsRequest")) {
            this.userDetailsResponseReceived(response);
        } else if(name.equals("ContactsDetailsRequest")) {
            this.contactDetailsReceived(response);
        } else if(name.equals("TransactionsRequest")) {
            this.transactionDetailsReceived(response);
        } else if(name.equals("GroupsRequest")) {
            this.groupsReceived(response);
        }
    }

    private void getUserDetails() {
        UserDetailsRequest user_req = new UserDetailsRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user_id", this.userId);
        user_req.sendRequest(payload);
    }

    private void getContacts() {
        ContactDetailsRequest contacts_req = new ContactDetailsRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("contact_ids", this.user.getContacts());
        contacts_req.sendRequest(payload);
    }

    private void getGroups() {
        GroupDetailRequest group_req = new GroupDetailRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user_id", this.user.getId());
        group_req.sendRequest(payload);
    }

    private void getTransactions() {
        TransactionsRequest trans_req = new TransactionsRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user_id", this.user.getId());
        trans_req.sendRequest(payload);
    }

    private void userDetailsResponseReceived(HashMap response) {
        this.user = (User) response.get("user");
        currentUser = this.user;
        Toast.makeText(getApplicationContext(), "Welcome " + this.user.getName(), Toast.LENGTH_LONG).show();
        this.getContacts();
    }

    private void contactDetailsReceived(HashMap response) {
        this.contacts = (ArrayList<User>) response.get("contacts");
//        Toast.makeText(getApplicationContext(), "User " + this.user.getName() + " has " +
//                ((Integer) this.contacts.size()).toString() + " contacts", Toast.LENGTH_LONG).show();
        this.getGroups();
    }

    private void transactionDetailsReceived(HashMap response) {
        this.transactions = (ArrayList<Transaction>) response.get("transactions");
        this.setAdapters();
//        Toast.makeText(getApplicationContext(), "User " + this.user.getName() + " has " +
//                ((Integer) this.transactions.size()).toString() + " transactions", Toast.LENGTH_LONG).show();
    }

    private void groupsReceived(HashMap response) {
        this.groups = (ArrayList<Group>) response.get("groups");
//        Toast.makeText(getApplicationContext(), "User " + this.user.getName() + " has " +
//                ((Integer) this.groups.size()).toString() + " groups", Toast.LENGTH_LONG).show();
        this.getTransactions();
    }

    private void setAdapters() {
        feedView = (ListView) findViewById(R.id.feed_view);
        reminderView = (ListView) findViewById(R.id.reminder_view);

        ArrayAdapter feedAdapter = ArrayAdapter.createFromResource(this,
                R.array.recent_transactions,
                android.R.layout.simple_list_item_activated_1);
        feedView.setAdapter(feedAdapter);

        ArrayAdapter reminderAdapter = ArrayAdapter.createFromResource(this,
                R.array.reminders,
                android.R.layout.simple_list_item_activated_1);
        reminderView.setAdapter(reminderAdapter);
    }

    private ArrayList<String> getTransactionsSummary() {
        ArrayList<String> result = new ArrayList<>();
        String conj = "";
        for(int i=0; i < this.transactions.size(); i++) {
            String s="";
            Transaction t = this.transactions.get(i);
            if(t.getSourceId().equals(this.userId)) {
                s += "You paid ";
                conj = "to ";
            } else {
                s += "You received ";
                conj = "from";
            }
        }
        return result;
    }

    private void formUserMap() {
        HashMap<Long, User> user_map = new HashMap<>();
        for (int i=0; i < this.contacts.size(); i++) {
            User u = this.contacts.get(i);
            user_map.put(u.getId(), u);
        }
        this.user_map = user_map;
    }

}
