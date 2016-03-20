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

    private RecyclerView recyclerView;

    private RCAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.memberlist);
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

    public ArrayList<User> addMember(){
        EditText editText = (EditText) findViewById(R.id.newmembername);
        String newname= editText.toString();


    }


    //this intent has to send across a string[] or arrayList<String>  with all the members names
    //the AddBillActivity will fill in these member names, and fill in fictional (0) values for its
    //other guys like paidAmt, owedAmt, includeMember
    public void goToBill(){
        Intent send =new Intent(this, AddBillActivity.class);
        startActivity(send);
        send.putExtra("mykey",value);


        startActivityForResult(send, 1);
    }

}
