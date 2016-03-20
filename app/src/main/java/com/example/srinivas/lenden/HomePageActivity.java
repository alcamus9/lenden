package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.srinivas.testlogin.R;

/**
 * Created by srinivas on 3/2/2016.
 */
public class HomePageActivity extends AppCompatActivity {

    ListView feedView;
    ListView reminderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        feedView = (ListView) findViewById(R.id.feed_view);
//        TextView feed_header = new TextView(this);
//        feed_header.setText("Recent Activity");
//        feedView.addHeaderView(feed_header);

        reminderView = (ListView) findViewById(R.id.reminder_view);
//        TextView reminder_header = new TextView(this);
//        reminder_header.setText("Reminders");
//        reminderView.addHeaderView(reminder_header);

        ArrayAdapter feedAdapter = ArrayAdapter.createFromResource(this,
                                                R.array.recent_transactions,
                                                android.R.layout.simple_list_item_activated_1);
        feedView.setAdapter(feedAdapter);

        ArrayAdapter reminderAdapter = ArrayAdapter.createFromResource(this,
                                                R.array.reminders,
                                                android.R.layout.simple_list_item_activated_1);
        reminderView.setAdapter(reminderAdapter);

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
}
