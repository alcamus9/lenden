package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srinivas.lenden.requests.ReqPayRequest;
import com.example.srinivas.testlogin.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String, String> password_ref = new HashMap<String, String>();
    EditText username_edittext, password_edittext;
    private CallbackManager mCallBackManager;

    private FacebookCallback<LoginResult> mCallBack=new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken successToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            if(profile!=null) {
                Toast.makeText(getApplicationContext(), "Welcome " + profile.getName(), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    private void initialize() {
        password_ref.put("sus", "shirl");
        password_ref.put("srin", "weim");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        FacebookSdk.sdkInitialize(getApplicationContext());

        mCallBackManager= CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username_edittext = (EditText) findViewById(R.id.username_text);
        password_edittext = (EditText) findViewById(R.id.password_text);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(mCallBackManager, mCallBack);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode,resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events. fb analytics like user demographics
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event. track the time people spend in your app
        AppEventsLogger.deactivateApp(this);
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
//        Intent reg_activity = new Intent(this, RegistrationActivity.class);
//        startActivity(reg_activity);
        ReqPayRequest req = new ReqPayRequest(getApplicationContext());
        req.read_json();
    }

}
