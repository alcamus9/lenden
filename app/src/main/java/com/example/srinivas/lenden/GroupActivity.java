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

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private ArrayList<User> groupMembers= new ArrayList<User>();//make sure that if we arrived at this activity from groupsactivity+CREATEGROUP button, then we take vals

    private RecyclerView recyclerView, recyclerView2;

    private RCAdapter rcAdapter, rcAdapter2;

    ArrayList<Info> membersListData, billsListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        int[] icons = {R.drawable.smile, R.drawable.smile,R.drawable.smile};
        String[] titles = {"Varun", "Karan", "Arun" };

        for (int i = 0; i<titles.length && i<icons.length; i++) {
            Info current = new Info();
            current.iconId = icons[i];
            current.title=titles[i];
            data.add(current);
        }
        this.membersListData=data;
        return data;

    }

    public void addMember(View v){
        EditText editText = (EditText) findViewById(R.id.newmembername);
        String newname= editText.toString();
        Info newRow = new Info();
        newRow.iconId=R.drawable.smile;
        newRow.title=newname;
        membersListData.add(newRow);
        rcAdapter.notifyDataSetChanged();

    }


    public List<Info> getBillsListData() {
        ArrayList<Info> data = new ArrayList<>(); //remember this syntax
        int[] icons = {R.drawable.smile, R.drawable.smile,R.drawable.smile};
        String[] titles = {"Broadway show", "Lunch at cozi", "Gas and drinks" };

        for (int i = 0; i<titles.length && i<icons.length; i++) {
            Info current = new Info();
            current.iconId = icons[i];
            current.title=titles[i];
            data.add(current);
        }
        this.billsListData=data;
        return data;
    }




    //this intent has to send across a string[] or arrayList<String>  with all the members names
    //the AddBillActivity will fill in these member names, and fill in fictional (0) values for its
    //other guys like paidAmt, owedAmt, includeMember
    public void goToAddBill(View v){
        Intent send =new Intent(this, AddBillActivity.class);
        startActivity(send);
        //send.putExtra("mykey",value);


        //startActivityForResult(send, 1);
    }

}
