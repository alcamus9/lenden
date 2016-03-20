package com.example.srinivas.lenden;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

public class AddBillActivity extends AppCompatActivity {


    private RecyclerView billSpecRCView;
    private BillSpecAdapter billSpecAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        //AddBillFragment frag = new AddBillFragment();
        //FragmentManager mgr=getFragmentManager();
        //FragmentTransaction txn = mgr.beginTransaction();
        //txn.add(R.id.addbillactivity, frag,"mytag");
        //txn.commit(); */

        Intent receive = getIntent();
        //val=receive.getSerializableExtra("mykey");


        billSpecRCView = (RecyclerView) findViewById(R.id.billspecrcview);
        billSpecAdapter= new BillSpecAdapter(this, getData());
        billSpecRCView.setAdapter(billSpecAdapter);
        billSpecRCView.setLayoutManager(new LinearLayoutManager(this));

    }


    public static List<InfoBillSpec> getData() {
        List<InfoBillSpec> data = new ArrayList<>();
        int[] icons = {R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out,R.drawable.cash_in, R.drawable.cash_out};
        String[] titles = {"You received 10 Rupees from Varun", "You paid 10 rupees to Karan", "You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan","You received 10 Rupees from Varun", "You paid 10 rupees to Karan"};

        for (int i = 0; i<titles.length && i<icons.length; i++) {
            InfoBillSpec current = new InfoBillSpec();

            //in actual, will have to get them
            //current.memberName = icons[i];
            //current.owedAmt;
            //current.paidAmt;
            //current.includeMember;
            //current.title=titles[i];
            data.add(current);
        }

        return data;
    }


}
