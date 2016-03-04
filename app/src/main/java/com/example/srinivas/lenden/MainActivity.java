package com.example.srinivas.lenden;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srinivas.testlogin.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String, String> password_ref = new HashMap<String, String>();
    EditText username_edittext, password_edittext;

    private void initialize() {
        password_ref.put("sus", "shirl");
        password_ref.put("srin", "weim");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username_edittext = (EditText) findViewById(R.id.username_text);
        password_edittext = (EditText) findViewById(R.id.password_text);
    }

    protected boolean valid_login(String username, String password_ent) {
        // check if credentials are valid
        return password_ref.get(username).equals(password_ent);
    }


    public void submit_clicked(View view) {
        String username = username_edittext.getText().toString();
        String password = password_edittext.getText().toString();

        if (valid_login(username, password)) {
            // take to landing page
            Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
            Intent home_page = new Intent(this, HomePageActivity.class);
            startActivity(home_page);
        } else {
            // show incorrect username message
            Toast.makeText(getApplicationContext(), "Incorrect username / password", Toast.LENGTH_LONG).show();
        }
    }

    public void register_clicked(View view) {
        // start registration activity
        Intent reg_activity = new Intent(this, RegistrationActivity.class);
        startActivity(reg_activity);
    }

}
