package com.example.srinivas.lenden;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;

/**
 * Created by srinivas on 3/2/2016.
 */
public class PayReceiveActivity extends TabActivity {
    private ArrayList<User> otherUsers;
    private User user;
    private ArrayList<User> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_receive);

        this.otherUsers = (ArrayList<User>) getIntent().getSerializableExtra("otherUsers");
        this.user = (User) getIntent().getSerializableExtra("user");
        this.contacts =  (ArrayList<User>) getIntent().getSerializableExtra("contacts");

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec pay_tab = tabHost.newTabSpec("Pay");
        TabHost.TabSpec receive_tab = tabHost.newTabSpec("Ask");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        pay_tab.setIndicator("Pay");
        Intent payIntent = new Intent(this, PayActivity.class);
        payIntent.putExtra("user", this.user);
        payIntent.putExtra("otherUsers", this.otherUsers);
        payIntent.putExtra("contacts", this.contacts);
        pay_tab.setContent(payIntent);

        receive_tab.setIndicator("Ask");
        Intent askIntent = new Intent(this, ReceiveActivity.class);
        askIntent.putExtra("user", this.user);
        askIntent.putExtra("otherUsers", this.otherUsers);
        askIntent.putExtra("contacts", this.contacts);
        receive_tab.setContent(askIntent);

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(pay_tab);
        tabHost.addTab(receive_tab);
    }
}
