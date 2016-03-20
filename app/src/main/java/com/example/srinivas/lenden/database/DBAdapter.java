package com.example.srinivas.lenden.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public UserDBHelper userHelper;

    public DBAdapter(Context context)
    {
        helper=new DbHelper(context);
        this.userHelper = new UserDBHelper(this, context);
        this.userHelper.initUsersDataBase();
    }

    public long insertData(String name, String password)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.USER_DATA.NAME, name);
        contentValues.put(DbHelper.USER_DATA.PASSWORD, password);
        long id= db.insert(DbHelper.USER_DATA.TABLE_NAME,null,contentValues);
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


    private ArrayList<Long> stringToLongArray(String s) {
        if(s.equals("")){
            return new ArrayList<Long>();
        }
        String[] strArray = s.split(",");
        ArrayList<Long> list = new ArrayList<Long>();
        for(String str: strArray) {
            list.add(Long.parseLong(str));
        }
        return list;
    }

    public void insertUser(User u) {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.USER_DATA.ID, u.getId());
        contentValues.put(DbHelper.USER_DATA.NAME, u.getName());
        contentValues.put(DbHelper.USER_DATA.PASSWORD, u.getPassword());
        contentValues.put(DbHelper.USER_DATA.USER_NAME, u.get_user_name());
        contentValues.put(DbHelper.USER_DATA.EMAIL_ID, u.getEmail());
        contentValues.put(DbHelper.USER_DATA.PHONE_NUMBER, u.getPhone_number());
        contentValues.put(DbHelper.USER_DATA.FB_PROFILE_ID, u.getFb_profile_id());
        contentValues.put(DbHelper.USER_DATA.CONTACTS, this.createCommaSeparatedString(u.getContacts()));
        contentValues.put(DbHelper.USER_DATA.GROUPS, this.createCommaSeparatedString(u.getGroups()));
        Long id= db.insert(DbHelper.USER_DATA.TABLE_NAME, null, contentValues);
        return;
    }

    private User getUserFromCursor(Cursor cursor) {
        int index1=cursor.getColumnIndex(DbHelper.USER_DATA.ID);
        Long id=cursor.getLong(index1);
        String name = cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.NAME));
        String password = cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.PASSWORD));
        String user_name = cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.USER_NAME));
        String email_id = cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.EMAIL_ID));
        String fb_profile_id = cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.FB_PROFILE_ID));
        String phone_number = cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.PHONE_NUMBER));
        ArrayList<Long> contacts = this.stringToLongArray(cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.CONTACTS)));
        ArrayList<Long> groups = this.stringToLongArray(cursor.getString(cursor.getColumnIndex(DbHelper.USER_DATA.GROUPS)));
        return new User(id, user_name, password, name, email_id, phone_number, fb_profile_id, contacts, groups);
    }


    public ArrayList<User> getAllUsers() {
        ArrayList<User> returnList = new ArrayList<User>();
        SQLiteDatabase db= helper.getReadableDatabase();

        String[] columns= {DbHelper.USER_DATA.ID, DbHelper.USER_DATA.NAME, DbHelper.USER_DATA.PASSWORD};
        Cursor cursor=db.query(DbHelper.USER_DATA.TABLE_NAME, null, null, null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        while(cursor.moveToNext())
        {
            returnList.add(this.getUserFromCursor(cursor));
        }
        return returnList;
    }

    public ArrayList<User> getAllGroups() {
        ArrayList<User> returnList = new ArrayList<User>();
        SQLiteDatabase db= helper.getReadableDatabase();

        String[] columns= {DbHelper.USER_DATA.ID, DbHelper.USER_DATA.NAME, DbHelper.USER_DATA.PASSWORD};
        Cursor cursor=db.query(DbHelper.USER_DATA.TABLE_NAME, null, null, null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        while(cursor.moveToNext())
        {
            returnList.add(this.getUserFromCursor(cursor));
        }
        return returnList;
    }

    public ArrayList<User> getAllTransactions() {
        ArrayList<User> returnList = new ArrayList<User>();
        SQLiteDatabase db= helper.getReadableDatabase();

        String[] columns= {DbHelper.USER_DATA.ID, DbHelper.USER_DATA.NAME, DbHelper.USER_DATA.PASSWORD};
        Cursor cursor=db.query(DbHelper.USER_DATA.TABLE_NAME, null, null, null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        while(cursor.moveToNext())
        {
            returnList.add(this.getUserFromCursor(cursor));
        }
        return returnList;
    }

    public ArrayList<User> getUsers(HashMap<String, String> map) {
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


        Cursor cursor=db.query(DbHelper.USER_DATA.TABLE_NAME, null, whereClause, arr, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        ArrayList<User> usersList = new ArrayList<User>();
        while(cursor.moveToNext())
        {
            usersList.add(this.getUserFromCursor(cursor));
        }
        return usersList;
    }

    public void insertUsers(ArrayList<User> users) {
        for (int i=0; i < users.size(); i++) {
            this.insertUser(users.get(i));
        }
    }

    public String getData(String name)
    {
        SQLiteDatabase db= helper.getWritableDatabase();

        String[] columns= {DbHelper.USER_DATA.NAME, DbHelper.USER_DATA.PASSWORD};
        Cursor cursor=db.query(DbHelper.USER_DATA.TABLE_NAME, columns, DbHelper.USER_DATA.NAME+" ='"+name+"'", null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {
            int index2=cursor.getColumnIndex(DbHelper.USER_DATA.NAME);
            String personName = cursor.getString(index2);
            int index3=cursor.getColumnIndex(DbHelper.USER_DATA.PASSWORD);
            String password = cursor.getString(index3);
            buffer.append(personName+" "+password+"\n");
        }
        return buffer.toString();
    }

    public int updateName(String oldName, String newName)
    {
        SQLiteDatabase db= helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.USER_DATA.NAME, newName);
        String whereClause= DbHelper.USER_DATA.NAME+"=?";
        String[] whereArgs={oldName};
        int count = db.update(DbHelper.USER_DATA.TABLE_NAME,contentValues, whereClause, whereArgs);
        return count;
    }

    public int deleteRow(String nameToDel)
    {
        SQLiteDatabase db= helper.getWritableDatabase();
        String whereClause= DbHelper.USER_DATA.NAME+"=?";
        String[] whereArgs={nameToDel};
        int count = db.delete(DbHelper.USER_DATA.TABLE_NAME,whereClause, whereArgs);
        return count;
    }

    static class DbHelper extends SQLiteOpenHelper { //static because of concept of least privilege, nonstatic also fine (static inner class can only access the static fields of outer class.)

        private static final String DATABASE_NAME = "lenden";
        private static final int DATABASE_VERSION = 14;
        private static final String VARC = " VARCHAR(255), ";

        static class USER_DATA {
            private static final String TABLE_NAME = "USERS";
            private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

            private static final String ID = "id";
            private static final String NAME = "name";
            private static final String PASSWORD = "password";
            private static final String USER_NAME = "user_name";
            private static final String CONTACTS = "contacts";
            private static final String GROUPS = "groups";
            private static final String FB_PROFILE_ID = "fb_profile_id";
            private static final String EMAIL_ID = "email_id";
            private static final String PHONE_NUMBER = "phone_number";

            private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " LONG PRIMARY KEY, "
                    + NAME + " VARCHAR(255), " + PASSWORD + VARC + USER_NAME + VARC + CONTACTS + VARC + GROUPS + VARC + FB_PROFILE_ID + VARC + EMAIL_ID + VARC + PHONE_NUMBER + " VARCHAR(20));";
        }

        static class GROUP_DATA {
            private static final String TABLE_NAME = "GROUPS";
            private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

            private static final String ID = "id";
            private static final String NAME = "name";
            private static final String USERS = "users";
            private static final String BILLS = "bills";

            private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " LONG PRIMARY KEY, "
                    + NAME + VARC + BILLS + VARC + USERS + " VARCHAR(255));";
        }

        static class TRANSACTION_DATA {
            private static final String TABLE_NAME = "TRANSACTIONS";
            private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

            private static final String ID = "id";
            private static final String STATUS = "status";
            private static final String DESCRIPTION = "description";
            private static final String SOURCE_ID = "source_id";
            private static final String DEST_ID = "dest_id";
            private static final String AMOUNT = "amount";

            private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " LONG PRIMARY KEY, "
                    + DESCRIPTION + VARC + STATUS + VARC + SOURCE_ID + " LONG, " + DEST_ID + " LONG, " + AMOUNT + " VARCHAR(255));";
        }

        private Context context;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATE TABLE TABLE1 (-id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255));
            try {
                db.execSQL(USER_DATA.CREATE_TABLE);
                db.execSQL(GROUP_DATA.CREATE_TABLE);
                db.execSQL(TRANSACTION_DATA.CREATE_TABLE);
                System.out.println("Tables created");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                System.out.println("Upgrade of table called. Deleting data and creating copy.");
                db.execSQL(USER_DATA.DROP_TABLE); //or add cols, or back it up to secondary db or cloud..
                db.execSQL(GROUP_DATA.DROP_TABLE);
                db.execSQL(TRANSACTION_DATA.DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}