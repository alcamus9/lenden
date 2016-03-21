package com.example.srinivas.lenden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.srinivas.lenden.objects.User;
import com.example.srinivas.testlogin.R;

import java.util.ArrayList;

/**
 * Created by srinivas on 3/3/2016.
 */
public class ContactsActivity extends AppCompatActivity {
    ArrayList<User> contactsUsers;
    ArrayList<User> otherUsers;
    ListView contacts_list_view;
    private static final int ADD_CONTACT_CODE = 1;
    ArrayAdapter contact_list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        this.contactsUsers = (ArrayList<User>) getIntent().getSerializableExtra("contacts");
        this.otherUsers = (ArrayList<User>) getIntent().getSerializableExtra("otherUsers");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.contacts_list_view = (ListView) findViewById(R.id.contacts_view);
        this.contact_list_adapter = new ContactArrayAdapter(this.contactsUsers);
        contacts_list_view.setAdapter(this.contact_list_adapter);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("contacts", this.contactsUsers);
        resultIntent.putExtra("otherUsers", this.otherUsers);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void addContact(MenuItem item) {
        Intent addContactIntent = new Intent(this, AddContactsActivity.class);
        // this.otherUsers is not the right thing to use. It has to be edited.
        addContactIntent.putExtra("otherUsers", this.otherUsers);
        startActivityForResult(addContactIntent, ADD_CONTACT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_CONTACT_CODE) {
            if(resultCode == RESULT_OK) {
                User u = (User) data.getSerializableExtra("user");
                this.contactsUsers.add(u);
                this.otherUsers.remove(u);
                this.contact_list_adapter.notifyDataSetChanged();
            }
        }
    }

    private class ContactArrayAdapter extends  ArrayAdapter {
        public ContactArrayAdapter() {
            super(ContactsActivity.this, R.layout.layout_contact, contactsUsers);
        }

        public ContactArrayAdapter(ArrayList users) {
            super(ContactsActivity.this, R.layout.layout_contact, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout_contact = convertView;

            //make sure we have a view to work with.
            if (convertView == null) {
                layout_contact = getLayoutInflater().inflate(R.layout.layout_contact, parent, false);
            }

            User current_contact = contactsUsers.get(position);
            ImageView img_view = (ImageView) layout_contact.findViewById(R.id.contact_image_view);
            int id = getResources().getIdentifier(current_contact.getImage_path(), "drawable", getPackageName());
            img_view.setImageResource(id);

            TextView text_view = (TextView) layout_contact.findViewById(R.id.contact_name_view);
            text_view.setText(current_contact.get_user_name());

            return layout_contact;
        }
    }
}
