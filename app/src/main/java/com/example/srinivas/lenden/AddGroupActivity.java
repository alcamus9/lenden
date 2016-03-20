package com.example.srinivas.lenden;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RCAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.memberlist);
        rcAdapter= new RCAdapter(this, getData());
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static List<Info> getData() {
        List<Info> data = new ArrayList<>();
        int[] icons = {R.drawable.smile, R.drawable.smile,R.drawable.smile};
        String[] titles = {"Varun", "Karan", "Arun" };

        for (int i = 0; i<titles.length && i<icons.length; i++) {
            Info current = new Info();
            current.iconId = icons[i];
            current.title=titles[i];
            data.add(current);
        }

        return data;
    }

}
