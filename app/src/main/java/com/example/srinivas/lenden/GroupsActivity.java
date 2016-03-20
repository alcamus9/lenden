//GroupsActivity.java

package com.example.srinivas.lenden;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RCAdapter rcAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //View layout=inflater.inflate(R.layout.activity_recycler, container, false);

        recyclerView = (RecyclerView) findViewById(R.id.grouplist);
        rcAdapter= new RCAdapter(this, getData());
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static List<Info> getData() {
        List<Info> data = new ArrayList<>();
        int[] icons = {R.drawable.smile, R.drawable.smile,R.drawable.smile, R.drawable.smile,R.drawable.smile, R.drawable.smile,R.drawable.smile};
        String[] titles = {"Oregon Trip", "Seattle excursion", "Windham Ski", "Drew Wedding","India Holiday", "Christmas celebration","Chetan yatch party"};

        for (int i = 0; i<titles.length && i<icons.length; i++) {
            Info current = new Info();
            current.iconId = icons[i];
            current.title=titles[i];
            data.add(current);
        }

        return data;
    }

}