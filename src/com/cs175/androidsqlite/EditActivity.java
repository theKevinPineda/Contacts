package com.cs175.androidsqlite;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class EditActivity extends Activity {
private Contact recievedContact;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        
        Bundle b = getIntent().getExtras();
        recievedContact = (Contact) b.getSerializable("contact");
        
        EditText editName = (EditText) findViewById(R.id.newName);
        EditText editNumber = (EditText) findViewById(R.id.newNumber);
        editName.setHint(recievedContact.getName());
        editNumber.setHint(recievedContact.getPhoneNumber());
        
	}
	
}
