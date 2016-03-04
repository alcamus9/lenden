package com.example.srinivas.lenden;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.srinivas.testlogin.R;

/**
 * Created by srinivas on 3/2/2016.
 */
public class PayReceiveActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_receive);

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec pay_tab = tabHost.newTabSpec("Pay");
        TabHost.TabSpec receive_tab = tabHost.newTabSpec("Receive");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        pay_tab.setIndicator("Pay");
        pay_tab.setContent(new Intent(this, PayActivity.class));

        receive_tab.setIndicator("Receive");
        receive_tab.setContent(new Intent(this, ReceiveActivity.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(pay_tab);
        tabHost.addTab(receive_tab);
    }
}
