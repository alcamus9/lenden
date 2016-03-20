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

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private ArrayList<User> groupMembers;//make sure that if we arrived at this activity from groupsactivity+CREATEGROUP button, then we take vals
    private RecyclerView recyclerView, recyclerView2;
    private RCAdapter rcAdapter, rcAdapter2;
    ArrayList<Info> membersListData, billsListData;
    public Group receivedGroup;
    ArrayList<Group> tGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        receivedGroup= (Group) getIntent().getSerializableExtra("mykey");

        groupMembers = new ArrayList<User>();
        groupMembers=receivedGroup.getUserObjects();

        tGroups = (ArrayList<Group>) getIntent().getSerializableExtra("travelingGroups");

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

        for (int i=0; i<groupMembers.size(); i++){
            Info current = new Info();
            current.iconId=R.drawable.smile;
            current.title=groupMembers.get(i).getName();
            data.add(current);
        }
        this.membersListData=data;
        return data;
    }

    public void addMember(View v){

        EditText editText = (EditText) findViewById(R.id.newmembername);
        String newname= editText.getText().toString();
        User newUser = new User(newname); //creating a new 'User' object with name as specified by
                                        // user (later we will modify this so as not to create new users
                                        // this way(i.e. just by name), but only search among existing contacts of
                                        // the current user in db, or, the currentuser can 'invite'
                                        // a new user, but that constructor will also have email,
                                        // unlike this one which only has name)

        //groupMembers.add(newUser); //adding the new 'User' object to our list of all members for this
                                    // 'group'. This is important as the goToAddBill method needs an
                                    //up-to-date version of groupMembers. Although not really; merely updating
                                    //receivedGroup should be enough, but lets do this for consistency even
                                    //though we can always run getUserObjects() on the updated receivedGroup and get this.
        receivedGroup.addMember(newUser); //which we update here!
        Info newRow = new Info();
        newRow.iconId=R.drawable.smile;
        newRow.title=newUser.getName();//could have directly written newRow.title=newname also
                                        // but just wanted to drive home the point that while we are
                                        // displaying just a name, what we have added is a 'User' object
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
        send.putExtra("listofmembers",groupMembers);
        startActivityForResult(send, 1);//as yet just sending simple intent , need to enhnace
    }

    public void goToGroups(View v){
        //yet to finish. we recd & modified receivedGroup throughout this activity. now send the modified version to 'groups'.
        Intent send = new Intent(this, GroupsActivity.class);
        send.putExtra("keyfornewgroup", receivedGroup);
        send.putExtra("source", "fromGroupPage");
        send.putExtra("travelingGroups",tGroups);
        startActivityForResult(send,1);
    }


}
