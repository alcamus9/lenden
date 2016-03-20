package com.example.srinivas.lenden;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RCAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //View layout=inflater.inflate(R.layout.activity_recycler, container, false);

        recyclerView = (RecyclerView) findViewById(R.id.rclist);
        rcAdapter= new RCAdapter(this, getData());
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static List<Info> getData() {
        List<Info> data = new ArrayList<>();
        int[] icons = {R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out};
        String[] titles = {"You received 10 Rupees from Varun", "You paid 10 rupees to Karan", "You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan"};

        for (int i = 0; i<titles.length && i<icons.length; i++) {
            Info current = new Info();
            current.iconId = icons[i];
            current.title=titles[i];
            data.add(current);
        }

        return data;
    }

}
