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
import android.widget.CheckBox;
import android.widget.Toast;


import com.example.srinivas.lenden.objects.Bill;
import com.example.srinivas.lenden.objects.Group;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBillActivity extends AppCompatActivity {
    HashMap<User, Double> surplusmap;
    double amount;
    Bill billfinal;
    List<String> billparticipants;
    private RecyclerView billSpecRCView;
    private BillSpecAdapter billSpecAdapter;
    ArrayList<User> receivedMembers;
    Bill receivedBill;
    ArrayList<Group> tGroups;
    Group receivedGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        receivedGroup=(Group) getIntent().getSerializableExtra("keyfornewgroup");
        tGroups = (ArrayList<Group>) getIntent().getSerializableExtra("travelingGroups");
        receivedBill= (Bill) getIntent().getSerializableExtra("latestbill");
        receivedMembers = receivedGroup.getUserObjects();

        billSpecRCView = (RecyclerView) findViewById(R.id.billspecrcview);
        billSpecAdapter = new BillSpecAdapter(this, getData());
        billSpecRCView.setAdapter(billSpecAdapter);
        billSpecRCView.setLayoutManager(new LinearLayoutManager(this));
    }


    public List<InfoBillSpec> getData() {
        List<InfoBillSpec> data = new ArrayList<>();

        for (int i = 0; i < receivedGroup.getUserObjects().size(); i++) {
            InfoBillSpec current = new InfoBillSpec();
            current.memberName = receivedGroup.getUserObjects().get(i).getName();
            current.paidAmt = 0.00;
            current.owedAmt = 0.00;
            current.includeMember = true;
            //current.user = receivedMembers.get(i);
            data.add(current);
        }

        return data;
    }


    public void goBackToGroupActivity(View v) {

        billparticipants = new ArrayList<>();
        surplusmap = new HashMap<>();
        CheckBox checkEqualSplitBox = (CheckBox) findViewById(R.id.mycheck);
        double totalAmountPaid = 0;
        double totalAmountOwed = 0;
        if (!checkEqualSplitBox.isChecked()) {
            for (int i = 0; i < receivedMembers.size(); i++) {
                BillSpecAdapter.BillSpecViewHolder vh = (BillSpecAdapter.BillSpecViewHolder) billSpecRCView.findViewHolderForLayoutPosition(i);
                if (!vh.include.isChecked()) {
                    vh.owed.setText("0.0");
                    vh.paid.setText("0.0");
                    surplusmap.put(receivedMembers.get(i), 0.0);
                } else {
                    billparticipants.add(vh.name.getText().toString());
                    surplusmap.put(receivedMembers.get(i), Double.parseDouble(vh.paid.getText().toString())-Double.parseDouble(vh.owed.getText().toString())  );
                }

                totalAmountPaid = totalAmountPaid + Double.parseDouble(vh.paid.getText().toString());
                totalAmountOwed = totalAmountPaid + Double.parseDouble(vh.owed.getText().toString());
            }

            if (totalAmountPaid != totalAmountOwed) {
                Toast.makeText(this, "Recheck data entered. Total amount paid and Total amount owed should be equal.", Toast.LENGTH_LONG).show();
            } else {
                amount = totalAmountPaid;
            }
        }

        if (checkEqualSplitBox.isChecked()) {

            for (int i = 0; i < receivedMembers.size(); i++) {
                BillSpecAdapter.BillSpecViewHolder vh = (BillSpecAdapter.BillSpecViewHolder) billSpecRCView.findViewHolderForLayoutPosition(i);
                if (!vh.include.isChecked()) {
                    vh.owed.setText("0.0");
                    vh.paid.setText("0.0");
                    surplusmap.put(receivedMembers.get(i), 0.0);
                } else {
                    billparticipants.add(vh.name.getText().toString());
                    totalAmountPaid = totalAmountPaid + Double.parseDouble(vh.paid.getText().toString());
                }
            }

            amount=totalAmountPaid;
            double amountOwedByEachParticipant=amount/billparticipants.size();
            for (int i = 0; i < receivedMembers.size(); i++) {
                BillSpecAdapter.BillSpecViewHolder vh = (BillSpecAdapter.BillSpecViewHolder) billSpecRCView.findViewHolderForLayoutPosition(i);
                if (vh.include.isChecked()){surplusmap.put(receivedMembers.get(i), Double.parseDouble(vh.paid.getText().toString())-amountOwedByEachParticipant);}
                else ;
            }
        }

        //add the calculated surplus map as the "surpluses" element of the bill
        receivedBill.setSurpluses(surplusmap);
        receivedGroup.addBill(receivedBill);


        Intent i = new Intent(this, GroupActivity.class);
        i.putExtra("travelingGroups",tGroups);
        i.putExtra("source","fromAddBill");
        //i.putExtra("surpluses", surplusmap);
        i.putExtra("recdgrp",receivedGroup);
        startActivity(i);


    }


}


