package com.example.srinivas.lenden.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.srinivas.lenden.objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sushantc on 3/5/16.
 */



public class DBAdapter {
    DbHelper helper;

    public DBAdapter(Context context)
    {
        helper=new DbHelper(context);
    }

    public long insertData(String name, String password)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, name);
        contentValues.put(DbHelper.PASSWORD, password);
        long id= db.insert(DbHelper.USERS_TABLE_NAME,null,contentValues);
        return id;
    }

    private String createCommaSeparatedString(ArrayList Elements) {
        String value = "";
        if(Elements == null || Elements.size() == 0)
            return value;
        for(int i=0; i<Elements.size() - 1; i++) {
            value += Elements.get(i).toString();
            value += ",";
        }
        value += Elements.get(Elements.size() - 1).toString();
        return value;
    }

    public void insertUser(User u) {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, u.getName());
        contentValues.put(DbHelper.PASSWORD, u.getPassword());
        contentValues.put(DbHelper.USER_NAME, u.get_user_name());
        contentValues.put(DbHelper.PASSWORD, u.getEmail());
        contentValues.put(DbHelper.NAME, u.getPhone_number());
        contentValues.put(DbHelper.PASSWORD, u.getFb_profile_id());
        contentValues.put(DbHelper.NAME, this.createCommaSeparatedString(u.getContacts()));
        contentValues.put(DbHelper.NAME, this.createCommaSeparatedString(u.getGroups()));
        Long id= db.insert(DbHelper.USERS_TABLE_NAME,null,contentValues);
        return;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> returnList = new ArrayList<User>();
        SQLiteDatabase db= helper.getReadableDatabase();

        String[] columns= {DbHelper.ID, DbHelper.NAME, DbHelper.PASSWORD};
        Cursor cursor=db.query(DbHelper.USERS_TABLE_NAME, null, null, null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {
            int index1=cursor.getColumnIndex(DbHelper.ID);
            Long id=cursor.getLong(index1);
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.NAME));
            String password = cursor.getString(cursor.getColumnIndex(DbHelper.PASSWORD));
            String user_name = cursor.getString(cursor.getColumnIndex(DbHelper.USER_NAME));
            String email_id = cursor.getString(cursor.getColumnIndex(DbHelper.EMAIL_ID));
            String fb_profile_id = cursor.getString(cursor.getColumnIndex(DbHelper.FB_PROFILE_ID));
            String phone_number = cursor.getString(cursor.getColumnIndex(DbHelper.PHONE_NUMBER));
            String ContactsString = cursor.getString(cursor.getColumnIndex(DbHelper.CONTACTS));
            String GroupsString = cursor.getString(cursor.getColumnIndex(DbHelper.GROUPS));
            User u = new User(id, user_name, password, name, email_id, phone_number, fb_profile_id, null, null);
            returnList.add(u);
        }
        return returnList;
    }

    public void getUsers(HashMap<String, String> map) {
        SQLiteDatabase db= helper.getReadableDatabase();
        String whereClause = " ";
        ArrayList<String> whereArgs = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<String, String> el = it.next();
            whereClause+= el.getKey()+ "=? ";
            if(it.hasNext()) {
                whereClause += "AND ";
            }
            whereArgs.add(el.getValue());
        }
        String[] arr = new String[whereArgs.size()];
        whereArgs.toArray(arr);


        Cursor cursor=db.query(DbHelper.USERS_TABLE_NAME, null, whereClause, arr, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {
            int index2=cursor.getColumnIndex(DbHelper.NAME);
            String personName = cursor.getString(index2);
            int index3 = cursor.getColumnIndex(DbHelper.PASSWORD);
            String password = cursor.getString(index3);
            buffer.append(personName+" "+password+"\n");
        }

    }

    public void insertUsers(ArrayList<User> users) {
        for (int i=0; i < users.size(); i++) {
            this.insertUser(users.get(i));
        }
    }

    public String getData(String name)
    {
        // select name, password from MYTABLE where name = 'sri'
        SQLiteDatabase db= helper.getWritableDatabase();

        String[] columns= {DbHelper.NAME, DbHelper.PASSWORD};
        Cursor cursor=db.query(DbHelper.USERS_TABLE_NAME, columns, DbHelper.NAME+" ='"+name+"'", null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {
            int index2=cursor.getColumnIndex(DbHelper.NAME);
            String personName = cursor.getString(index2);
            int index3=cursor.getColumnIndex(DbHelper.PASSWORD);
            String password = cursor.getString(index3);
            buffer.append(personName+" "+password+"\n");
        }
        return buffer.toString();
    }

    public int updateName(String oldName, String newName)
    {
        SQLiteDatabase db= helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, newName);
        String whereClause=DbHelper.NAME+"=?";
        String[] whereArgs={oldName};
        int count = db.update(DbHelper.USERS_TABLE_NAME,contentValues, whereClause, whereArgs);
        return count;
    }

    public int deleteRow(String nameToDel)
    {
        SQLiteDatabase db= helper.getWritableDatabase();
        String whereClause=DbHelper.NAME+"=?";
        String[] whereArgs={nameToDel};
        int count = db.delete(DbHelper.USERS_TABLE_NAME,whereClause, whereArgs);
        return count;
    }

    static class DbHelper extends SQLiteOpenHelper { //static because of concept of least privilege, nonstatic also fine (static inner class can only access the static fields of outer class.)

        private static final String DATABASE_NAME = "lenden";
        private static final String USERS_TABLE_NAME = "USERS";
        private static final int DATABASE_VERSION = 8;
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String PASSWORD = "password";
        private static final String USER_NAME = "user_name";
        private static final String CONTACTS = "contacts";
        private static final String GROUPS = "groups";
        private static final String FB_PROFILE_ID = "fb_profile_id";
        private static final String EMAIL_ID = "email_id";
        private static final String PHONE_NUMBER = "phone_number";
        private static final String VARC = " VARCHAR(255), ";
        private static final String CREATE_TABLE = "CREATE TABLE " + USERS_TABLE_NAME + " (" + ID + " LONG PRIMARY KEY, "
                + NAME + " VARCHAR(255), " + PASSWORD + VARC + USER_NAME + VARC + CONTACTS + VARC + GROUPS + VARC + FB_PROFILE_ID + VARC + EMAIL_ID + VARC + PHONE_NUMBER + " VARCHAR(20));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + USERS_TABLE_NAME;

        private Context context;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Toast.makeText(context,  "constructor called", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATE TABLE TABLE1 (-id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255));

            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context,  "onCreate called", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context,  " " + e, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context,  "onUpgrade called", Toast.LENGTH_SHORT).show();
                db.execSQL(DROP_TABLE); //or add cols, or back it up to secondary db or cloud..
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context,  " " + e, Toast.LENGTH_SHORT).show();
            }
        }

    }
}