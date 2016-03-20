package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srinivas.lenden.dbrequests.AuthDBRequest;
import com.example.srinivas.lenden.dbrequests.ContactDetailsRequest;
import com.example.srinivas.lenden.dbrequests.GroupDetailRequest;
import com.example.srinivas.lenden.dbrequests.TransactionsRequest;
import com.example.srinivas.lenden.dbrequests.UserDetailsRequest;
import com.example.srinivas.lenden.objects.Group;
import com.example.srinivas.lenden.objects.Transaction;
import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.lenden.requests.AsyncRequestListener;
import com.example.srinivas.lenden.requests.ReqPayRequest;
import com.example.srinivas.testlogin.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AsyncRequestListener {

    HashMap<String, String> password_ref = new HashMap<String, String>();
    EditText username_edittext, password_edittext;
    private CallbackManager mCallBackManager;
    private Long authUserId;
    private User user;
    private ArrayList<User> contacts;
    private ArrayList<Transaction> transactions;
    private ArrayList<Group> groups;

    private FacebookCallback<LoginResult> mCallBack=new FacebookCallback<LoginResult>() {

        private MainActivity getOuter() {
            return MainActivity.this;
        }

        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken successToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            if(profile!=null) {
                // Toast.makeText(getApplicationContext(), "Welcome " + profile.getName(), Toast.LENGTH_LONG).show();
                AuthDBRequest auth_request = new AuthDBRequest(getOuter(), getApplicationContext());
                HashMap<String, Object> payLoad = new HashMap<String, Object>();
                payLoad.put("fb_profile_id", profile.getId());
                auth_request.sendRequest(payLoad);
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
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
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

        AuthDBRequest auth_request = new AuthDBRequest(this, getApplicationContext());
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("user_name", username);
        payLoad.put("password", password);
        auth_request.sendRequest(payLoad);
    }

    public void register_clicked(View view) {
        // start registration activity
//        Intent reg_activity = new Intent(this, RegistrationActivity.class);
//        startActivity(reg_activity);
        ReqPayRequest req = new ReqPayRequest(getApplicationContext());
        req.read_json();
    }

    @Override
    public void onResponseReceived(String name, HashMap response) {
        if(name.equals("AuthDBRequest")) {
            this.authResponseReceived(response);
        } else if(name.equals("UserDetailsRequest")) {
            this.userDetailsResponseReceived(response);
        } else if(name.equals("ContactsDetailsRequest")) {
            this.contactDetailsReceived(response);
        } else if(name.equals("TransactionsRequest")) {
            this.transactionDetailsReceived(response);
        } else if(name.equals("GroupsRequest")) {
            this.groupsReceived(response);
        }
    }

    private void getUserDetails() {
        UserDetailsRequest user_req = new UserDetailsRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user_id", this.authUserId);
        user_req.sendRequest(payload);
    }

    private void getContacts() {
        ContactDetailsRequest contacts_req = new ContactDetailsRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("contact_ids", this.user.getContacts());
        contacts_req.sendRequest(payload);
    }

    private void getGroups() {
        GroupDetailRequest group_req = new GroupDetailRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user_id", this.user.getId());
        group_req.sendRequest(payload);
    }

    private void getTransactions() {
        TransactionsRequest trans_req = new TransactionsRequest(this, getApplicationContext());
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("user_id", this.user.getId());
        trans_req.sendRequest(payload);
    }

    private void authResponseReceived(HashMap response) {
        if (response.get("user_id") == null) {
            Toast.makeText(getApplicationContext(), "User authentication failed", Toast.LENGTH_LONG).show();
        }
        else {
            this.authUserId = (Long) response.get("user_id");
            Toast.makeText(getApplicationContext(), "User authenticated." + this.authUserId.toString(), Toast.LENGTH_LONG).show();
            this.getUserDetails();
        }
    }

    private void userDetailsResponseReceived(HashMap response) {
        this.user = (User) response.get("user");
        Toast.makeText(getApplicationContext(), "Welcome " + this.user.getName(), Toast.LENGTH_LONG).show();
        this.getContacts();
        this.getTransactions();
        this.getGroups();
    }

    private void contactDetailsReceived(HashMap response) {
        this.contacts = (ArrayList<User>) response.get("contacts");
        Toast.makeText(getApplicationContext(), "User " + this.user.getName() + " has " +
                ((Integer) this.contacts.size()).toString() + " contacts", Toast.LENGTH_LONG).show();
    }

    private void transactionDetailsReceived(HashMap response) {
        this.transactions = (ArrayList<Transaction>) response.get("transactions");
        Toast.makeText(getApplicationContext(), "User " + this.user.getName() + " has " +
                ((Integer) this.transactions.size()).toString() + " transactions", Toast.LENGTH_LONG).show();
    }

    private void groupsReceived(HashMap response) {
        this.groups = (ArrayList<Group>) response.get("groups");
        Toast.makeText(getApplicationContext(), "User " + this.user.getName() + " has " +
                ((Integer) this.groups.size()).toString() + " groups", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        LoginManager.getInstance().logOut();
        super.onDestroy();
    }

    public void gotorcview(View view){
        Intent gotorc = new Intent(this, RecyclerActivity.class);
        startActivity(gotorc);
    }
}
