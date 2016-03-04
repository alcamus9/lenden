package com.example.srinivas.lenden;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.srinivas.testlogin.R;

import java.lang.reflect.Array;

/**
 * Created by srinivas on 3/2/2016.
 */
public class ReceiveActivity extends Activity implements AdapterView.OnItemSelectedListener{
    String[] balances;
    Spinner account_spinner;
    TextView balance_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        account_spinner = (Spinner) findViewById(R.id.rec_account_spinner);
        balance_view = (TextView) findViewById(R.id.rec_balance_view);

        ArrayAdapter array_adapter = ArrayAdapter.createFromResource(this,
                                                            R.array.accounts,
                                                            android.R.layout.simple_spinner_dropdown_item);

        account_spinner.setAdapter(array_adapter);
        account_spinner.setOnItemSelectedListener(this);

        balances = getResources().getStringArray(R.array.balances);
        balance_view.setText((balances[0] + " $"));
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
