package com.example.srinivas.lenden;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.srinivas.testlogin.R;

/**
 * Created by srinivas on 3/2/2016.
 */
public class PayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Spinner account_spinner = (Spinner) findViewById(R.id.pay_account_spinner);

        ArrayAdapter array_adapter = ArrayAdapter.createFromResource(this,
                R.array.accounts,
                android.R.layout.simple_spinner_dropdown_item);

        account_spinner.setAdapter(array_adapter);
    }
}
