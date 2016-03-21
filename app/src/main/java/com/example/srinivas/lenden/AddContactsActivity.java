package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;

/**
 * Created by srinivas on 3/20/2016.
 */
public class AddContactsActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    ArrayList<User> data;
    ArrayList<String> dataNames;

    ArrayAdapter autoCompleteAdapter;
    AutoCompleteTextView contactSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        contactSearchView = (AutoCompleteTextView) findViewById(R.id.contact_auto_complete);
        this.data = (ArrayList<User>) getIntent().getSerializableExtra("otherUsers");
        this.dataNames = new ArrayList<String>();
        for (int i=0; i < data.size(); i++) {
            this.dataNames.add(data.get(i).getName());
        }

        autoCompleteAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, this.dataNames);
        contactSearchView.setAdapter(autoCompleteAdapter);
        contactSearchView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView t = (TextView) view;
        String user_name = ((TextView) view).getText().toString();
        int pos = this.dataNames.indexOf(user_name);
        User u = this.data.get(pos);
        this.data.remove(pos);
        this.dataNames.remove(pos);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("user", u);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
