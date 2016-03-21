package com.example.srinivas.lenden;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;

/**
 * Created by srinivas on 3/2/2016.
 */
public class PayActivity extends Activity implements AdapterView.OnItemSelectedListener{
    String[] balances;
    Spinner account_spinner;
    TextView balance_view;
    ArrayList<User> contacts;
    User user;
    AutoCompleteTextView payTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        this.contacts = (ArrayList<User>) getIntent().getSerializableExtra("contacts");
        this.user = (User) getIntent().getSerializableExtra("user");


        balance_view = (TextView) findViewById(R.id.pay_balance_view);
        balances = getResources().getStringArray(R.array.balances);
        balance_view.setText((balances[0] + " $"));

        account_spinner = (Spinner) findViewById(R.id.pay_account_spinner);
        ArrayAdapter array_adapter = ArrayAdapter.createFromResource(this,
                R.array.accounts,
                android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(array_adapter);

        this.payTextView = (AutoCompleteTextView) findViewById(R.id.pay_auto_complete);
        ArrayList<String> contactNames = new ArrayList<>();
        for(int i=0; i < this.contacts.size(); i++) {
            contactNames.add(this.contacts.get(i).get_user_name());
        }
        ArrayAdapter<String> contact_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                contactNames);
        this.payTextView.setAdapter(contact_adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String balance_selected = balances[position];
        balance_view.setText((balance_selected + " $"));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
