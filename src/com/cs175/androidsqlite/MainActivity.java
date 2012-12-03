package com.cs175.androidsqlite;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.cs175.quickaction.ActionItem;
import com.cs175.quickaction.QuickAction;

public class MainActivity extends Activity{
	private static DatabaseHandler db;
	private ListView list;
    private LazyAdapter adapter;
	private int EDIT = 1, DELETE =2;
	private Contact clickedContact;
	private final int REQUEST_CODE=1;
    private QuickAction mQuickAction;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        db = new DatabaseHandler(this);
        populateContact();
        
        
        listPopulate();
        setQuickAction();
        setListeners();
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
	private void listPopulate(){
		ArrayList<Contact> contacts = db.getAllContacts();      
		list=(ListView)findViewById(R.id.list);
		adapter=new LazyAdapter(this, contacts);        
        list.setAdapter(adapter);
		
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
	
	private void setQuickAction(){
		ActionItem editItem      = new ActionItem(EDIT, "Edit", getResources().getDrawable(R.drawable.edit));
        ActionItem deletetItem   = new ActionItem(DELETE, "Delete", getResources().getDrawable(R.drawable.cancel));
        mQuickAction  = new QuickAction(this);
        mQuickAction.addActionItem(editItem);
        mQuickAction.addActionItem(deletetItem);		
	}
	
	private void editAction(){
		Bundle b = new Bundle();
		Intent i = new Intent(this, EditActivity.class);
		b.putSerializable("contact", clickedContact);
		i.putExtras(b);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	private void deleteAction(){
		db.deleteContact(clickedContact);
		listPopulate();
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent i) {
		
		super.onActivityResult(request, result, i);
		
		if(result == 1){
			listPopulate();
			toastThis(1);
		}
		else if(result == 2){
			toastThis(2);
		}
	}
	
	private void toastThis(int i){
		switch(i){
			
		case(1):Toast.makeText(getBaseContext(), "Successfully Edited Contact", Toast.LENGTH_SHORT).show();
		break;
		case(2):Toast.makeText(getBaseContext(), "Nothing Changed", Toast.LENGTH_SHORT).show();
		break;
		
		}
		
	}
	

	private void setListeners(){
		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            
            public void onItemClick(QuickAction quickAction, int pos, int actionId) {
 
                if (actionId == EDIT) {
                	editAction();
                	
                } else {
                	deleteAction();
                }
                
                
            }
        });
        
        list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				clickedContact = (Contact) view.getTag();
				mQuickAction.show(view);
				
			}
		});
		
	}
}
