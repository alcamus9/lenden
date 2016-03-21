package com.example.srinivas.lenden;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;

/**
 * Created by srinivas on 3/2/2016.
 */
public class ReceiveActivity extends Activity implements AdapterView.OnItemSelectedListener{
    Spinner account_spinner;
    ArrayList<User> contacts;
    User user;
    AutoCompleteTextView recTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        this.contacts = (ArrayList<User>) getIntent().getSerializableExtra("contacts");
        this.user = (User) getIntent().getSerializableExtra("user");


        account_spinner = (Spinner) findViewById(R.id.rec_account_spinner);

        ArrayAdapter array_adapter = ArrayAdapter.createFromResource(this,
                R.array.accounts,
                android.R.layout.simple_spinner_dropdown_item);

        account_spinner.setAdapter(array_adapter);
        account_spinner.setOnItemSelectedListener(this);

        this.recTextView = (AutoCompleteTextView) findViewById(R.id.rec_auto_complete);
        ArrayList<String> contactNames = new ArrayList<>();
        for(int i=0; i < this.contacts.size(); i++) {
            contactNames.add(this.contacts.get(i).get_user_name());
        }
        ArrayAdapter<String> contact_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                contactNames);
        this.recTextView.setAdapter(contact_adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
