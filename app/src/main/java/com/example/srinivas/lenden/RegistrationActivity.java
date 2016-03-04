package com.example.srinivas.lenden;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.srinivas.testlogin.R;

/**
 * Created by srinivas on 3/1/2016.
 */
public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void reg_submit_clicked(View view) {
        // register for the user.
        // Store details in DB. Possibly take to a new page for first time set up.
        if (is_success()) {
            Toast.makeText(this, "Registration Successful.", Toast.LENGTH_LONG).show();
            Intent home_page = new Intent(this, HomePageActivity.class);
            startActivity(home_page);
        } else {
            Toast.makeText(this, "Registration Unsuccessful.", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean is_success() {
        return true;
    }
}
