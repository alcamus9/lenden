package com.example.srinivas.lenden;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
    ListView contacts_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        this.contactsUsers = (ArrayList<User>) getIntent().getSerializableExtra("contacts");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.contacts_list_view = (ListView) findViewById(R.id.contacts_view);
        ArrayAdapter adapter = new ContactArrayAdapter(this.contactsUsers);
        contacts_list_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
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
