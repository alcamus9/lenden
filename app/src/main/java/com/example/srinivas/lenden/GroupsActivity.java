//GroupsActivity.java

package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.srinivas.lenden.objects.Group;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RCAdapter rcAdapter;
    ArrayList<Group> groups;
    public Group receivedGroup;
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
        if(getIntent().getStringExtra("source").equals("fromHomePage")) {
            this.groups = (ArrayList<Group>) getIntent().getSerializableExtra("groups");
        } else {
            this.groups= (ArrayList<Group>) getIntent().getSerializableExtra("travelingGroups");
        }
        //View layout=inflater.inflate(R.layout.activity_recycler, container, false);

        recyclerView = (RecyclerView) findViewById(R.id.grouplist);
        rcAdapter= new RCAdapter(this, this.getData());
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<Info> getData() {
        List<Info> data = new ArrayList<>();
        //int[] icons = {R.drawable.smile, R.drawable.smile,R.drawable.smile, R.drawable.smile,R.drawable.smile, R.drawable.smile,R.drawable.smile};
        //String[] titles = {"Oregon Trip", "Seattle excursion", "Windham Ski", "Drew Wedding","India Holiday", "Christmas celebration","Chetan yatch party"};

        for (int i = 0; i<this.groups.size(); i++) {
            Info current = new Info();
            current.iconId = R.drawable.smile;
            current.title= this.groups.get(i).getName();
            data.add(current);
        }

        return data;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.add_group) {
            Intent i=new Intent(this,AddGroupActivity.class);
            i.putExtra("travelingGroups",this.groups);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}