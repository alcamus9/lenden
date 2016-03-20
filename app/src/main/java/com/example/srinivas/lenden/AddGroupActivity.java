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

import com.example.srinivas.lenden.objects.Group;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RCAdapter rcAdapter;
    ArrayList<Info> membersListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  recyclerView = (RecyclerView) findViewById(R.id.memberlist);
        rcAdapter= new RCAdapter(this, getData());
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        */
    }

    /*public List<Info> getData() {
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
    */
    //add onClick func which first creates a new 'group', then
    //thru an intent goes to GroupsActivity, & adds this 'group' to the recyclerview there
    public void createGroup(View v) {
        EditText e = (EditText) findViewById(R.id.groupname);
        String group_name= e.toString();
        Group newGroup = new Group(group_name);

        Intent send = new Intent(this, GroupActivity.class);
        send.putExtra("mykey", newGroup);
        startActivityForResult(send, 1);
    }


}
