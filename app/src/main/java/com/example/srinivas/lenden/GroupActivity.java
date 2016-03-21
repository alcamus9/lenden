package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srinivas.lenden.objects.Bill;
import com.example.srinivas.lenden.objects.Group;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private ArrayList<User> groupMembers;//make sure that if we arrived at this activity from groupsactivity+CREATEGROUP button, then we take vals
    private ArrayList<Bill> groupBills;
    private RecyclerView recyclerView, recyclerView2;
    private RCAdapter rcAdapter, rcAdapter2;
    ArrayList<Info> membersListData, billsListData;
    public Group receivedGroup;
    ArrayList<Group> tGroups;
    HashMap<User, Double> receivedMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getStringExtra("source").equals("fromAddGroup")) {
            receivedGroup= (Group) getIntent().getSerializableExtra("mykey");
            tGroups = (ArrayList<Group>) getIntent().getSerializableExtra("travelingGroups");
            tGroups.add(receivedGroup);
        } else {//source="fromAddBill"
            tGroups= (ArrayList<Group>) getIntent().getSerializableExtra("travelingGroups");
            //receivedMap = (HashMap) getIntent().getSerializableExtra("surpluses");
            //newBillModified=(Bill) getIntent().getSerializableExtra("billwithsurplusesadded");
            receivedGroup=(Group) getIntent().getSerializableExtra("recdgrp");
        }

        //groupMembers = new ArrayList<User>();
        //groupMembers=receivedGroup.getUserObjects();

        recyclerView = (RecyclerView) findViewById(R.id.memberslist);
        rcAdapter= new RCAdapter(this, getMembersListData());
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView2 = (RecyclerView) findViewById(R.id.billslist);
        rcAdapter2= new RCAdapter(this, getBillsListData());
        recyclerView2.setAdapter(rcAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Info> getMembersListData() {
        ArrayList<Info> data = new ArrayList<>();
        for (int i=0; i<receivedGroup.getUserObjects().size(); i++){
            Info current = new Info();
            current.iconId=R.drawable.smile;
            current.title=receivedGroup.getUserObjects().get(i).getName();
            data.add(current);
        }
        this.membersListData=data;
        return data;
    }

    public void addMember(View v){
        EditText editText = (EditText) findViewById(R.id.newmembername);
        String newname= editText.getText().toString();
        User newUser = new User(newname); //creating a new 'User' object with name as specified by user (later we will modify this so as not to create new users
                                        // this way(i.e. just by name), but only search among existing contacts of the current user in db, or, the currentuser can 'invite'
                                        // a new user, but that constructor will also have email, unlike this one which only has name)
        receivedGroup.addMember(newUser); //which we update here!
        Info newRow = new Info();
        newRow.iconId=R.drawable.smile;
        newRow.title=newUser.getName();//could have directly written newRow.title=newname also but just wanted to drive home the point that while we are displaying just a name, what we have added is a 'User' object
        membersListData.add(newRow);
        rcAdapter.notifyDataSetChanged();
    }

    public List<Info> getBillsListData() {
        ArrayList<Info> data = new ArrayList<>(); //remember this syntax
        //int[] icons = {R.drawable.smile, R.drawable.smile,R.drawable.smile};
        //String[] titles = {"Broadway show", "Lunch at cozi", "Gas and drinks" };
        ArrayList<Bill> bills = receivedGroup.getBills();
        if (bills != null) {
            for (int i = 0; i < receivedGroup.getBills().size(); i++) {
                Info current = new Info();
                current.iconId = R.drawable.smile;
                current.title = receivedGroup.getBills().get(i).getName();
                data.add(current);
            }
            this.billsListData = data;
        }
        return data;
    }

    //this intent has to send across a string[] or arrayList<String>  with all the members names
    //the AddBillActivity will fill in these member names, and fill in fictional (0) values for its
    //other guys like paidAmt, owedAmt, includeMember
    public void goToAddBill(View v){
        EditText e = (EditText) findViewById(R.id.newbill);
        String bill_name= e.getText().toString();
        Bill newBill = new Bill(bill_name);

        //receivedGroup.addBill(newBill);
        //Info newRow = new Info();
        //newRow.iconId=R.drawable.smile;
        //newRow.title=newBill.getName();
        //billsListData.add(newRow);
        //rcAdapter.notifyDataSetChanged();

        Intent send =new Intent(this, AddBillActivity.class);
        send.putExtra("latestbill",newBill);
        send.putExtra("travelingGroups",tGroups);
        send.putExtra("keyfornewgroup", receivedGroup);
        startActivityForResult(send, 1);//as yet just sending simple intent , need to enhnace
    }

    public void goToGroups(View v){
        //yet to finish. we recd & modified receivedGroup throughout this activity. now send the modified version to 'groups'.
        Intent send = new Intent(this, GroupsActivity.class);
        send.putExtra("keyfornewgroup", receivedGroup);
        send.putExtra("source", "fromGroupPage");
        send.putExtra("travelingGroups", tGroups);
        startActivityForResult(send,1);
    }


    public void goToGroupSplit(View v){

        HashMap<User,Double> balances = new HashMap<>();
        balances=receivedGroup.groupSplit();
        String myString=" ";
        for(int i =0; i<receivedGroup.getUserObjects().size(); i++){
            Double d=balances.get(receivedGroup.getUserObjects().get(i));
            String bal = d.toString();
            String user= receivedGroup.getUserObjects().get(i).getName();
            myString= myString+" "+user+" owes "+bal+" to you ";
        }
        Toast.makeText(GroupActivity.this, myString, Toast.LENGTH_SHORT).show();

    }


}
