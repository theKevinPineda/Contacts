package com.cs175.androidsqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {
private Contact recievedContact;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        
        Bundle b = getIntent().getExtras();
        recievedContact = (Contact) b.getSerializable("contact");
        
        final EditText editName = (EditText) findViewById(R.id.newName);
        final EditText editNumber = (EditText) findViewById(R.id.newNumber);
        final Button edit = (Button) findViewById(R.id.editButton);
        final Button cancel = (Button) findViewById(R.id.cancelButton);
        
        
        editName.setHint(recievedContact.getName());
        editNumber.setHint(recievedContact.getPhoneNumber());
        
        edit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				pressedEdit(editName, editNumber);
			}
		});

        cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				pressedCancel();
			}
		});
        
        
	}
	
	public void pressedEdit(EditText name, EditText number){
		String tempName = name.getText().toString()
			  ,tempNumber =number.getText().toString();
		if(tempName.isEmpty()!=true)
		recievedContact.setName(tempName);
		if(tempNumber.isEmpty()!=true)
		recievedContact.setPhoneNumber(number.getText().toString());
		
		DatabaseHandler db = new DatabaseHandler(this);
		db.updateContact(recievedContact);
		finish();
		
	}
	public void pressedCancel(){
		finish();
		
	}
}
