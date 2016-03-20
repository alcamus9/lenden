package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    //public ArrayList<User> getUsers(){

    //}


    //this intent has to send across a string[] or arrayList<String>  with all the members names
    //the AddBillActivity will fill in these member names, and fill in fictional (0) values for its
    //other guys like paidAmt, owedAmt, includeMember
    public void goToBill(){
        Intent send =new Intent(this, AddBillActivity.class);
        startActivity(send);
        //send.putExtra("mykey",value);


        //startActivityForResult(send, 1);
    }

}
