package com.example.srinivas.lenden;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

/**
 * Created by srinivas on 3/1/2016.
 */
public class RegistrationActivity extends Activity {

    EditText password_view;
    EditText confirm_pwd_view;
    EditText name_view;
    EditText user_name_view;
    EditText phone_num_view;
    EditText email_id_view;

    String error_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);

        this.password_view = (EditText) findViewById(R.id.reg_password);
        this.confirm_pwd_view = (EditText) findViewById(R.id.reg_password_confirm);
        this.name_view = (EditText) findViewById(R.id.reg_name);
        this.user_name_view = (EditText) findViewById(R.id.reg_username);
        this.phone_num_view = (EditText) findViewById(R.id.reg_mobile_num);
        this.email_id_view = (EditText) findViewById(R.id.reg_email_id);
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
            User u = new User(this.user_name_view.getText().toString(), this.password_view.getText().toString(),
                              this.name_view.getText().toString(), this.phone_num_view.getText().toString(),
                              this.email_id_view.getText().toString());
            Intent home_page = new Intent(this, HomePageActivity.class);
            home_page.putExtra("userId", u.getId());
            startActivity(home_page);
        } else {
            Toast.makeText(this, "Registration Unsuccessful. " + this.error_message, Toast.LENGTH_LONG).show();
        }
    }

    private Boolean is_success() {
        if(!(this.password_view.getText().toString().equals(this.confirm_pwd_view.getText().toString()))) {
            this.error_message = "Password Mismatch";
            return false;
        } else if (this.user_name_view.getText().toString().contains(" ")) {
            this.error_message = "Username cannot contain white space.";
            return false;
        } else if (!(Utilities.isValidEmail(this.email_id_view.getText().toString()))) {
            this.error_message = "Email ID not valid";
            return false;
        } else {
            this.error_message = null;
            return true;
        }
    }
}
