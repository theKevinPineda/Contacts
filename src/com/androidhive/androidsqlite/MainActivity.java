package com.androidhive.androidsqlite;

import java.util.ArrayList;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity{
	static DatabaseHandler db;
	ListView list;
    LazyAdapter adapter;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = new DatabaseHandler(this);
        populateContact();
        //Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        ArrayList<Contact> contacts = db.getAllContacts();      
		list=(ListView)findViewById(R.id.list);
		adapter=new LazyAdapter(this, contacts);        
        list.setAdapter(adapter);

        
    }
	
	private Cursor getContacts() {
	    Uri uri = ContactsContract.Contacts.CONTENT_URI;
	    String[] projection = new String[] {ContactsContract.Contacts._ID, 
	    									ContactsContract.Contacts.DISPLAY_NAME, };
	    return	 getContentResolver().query(uri, projection, null, null, null);
	}
	
	void populateContact(){
		
		Cursor cursor = getContacts();
		while(cursor.moveToNext()){
        	String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
        	String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Data._ID));
        	String number = getContentData(id);
        	db.addContact(new Contact(Integer.parseInt(id), name, number));
        }
	}
	
	private String getContentData(String contactID) {
	    Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	    String[] projection = null;
	    String where = ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?";
	    String[] selectionArgs = new String[] { contactID };
	    String sortOrder = null;
	    Cursor result = getContentResolver().query(uri, projection, where, selectionArgs, sortOrder);
	    if (result.moveToFirst()) {
	        String phone = result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	        if (phone != null) {
	            result.close();
	            return phone;
	        }
	    }
	    result.close();
	    return null;
	}
}
