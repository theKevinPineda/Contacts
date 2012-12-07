package cs175.androidsqlite.PinedaK.QuerubinJ.Act1;

import cs175.androidsqlite.PinedaK.Act1.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity {
private Contact recievedContact;
private EditText editName, editNumber;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        // Sets Variables 
        setValues();
        // Sets Button Listeners
        setButtonListeners();
        
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
		Intent i =getIntent();
		Bundle b = new Bundle();
		recievedContact.setName("WTF it worked");
		b.putSerializable("con",recievedContact);
		i.putExtras(b);
		setResult((tempName.isEmpty()==true&&tempNumber.isEmpty()==true)?2:1,
				i);
		finish();
		
	}
	
	public void pressedCancel(){
		setResult(2, getIntent());
		finish();
		
	}
	
	private void setValues(){
		recievedContact = (Contact) getIntent().getExtras().getSerializable("contact");
		
		editName = (EditText) findViewById(R.id.newName);
        editNumber = (EditText) findViewById(R.id.newNumber);
        editName.setHint(recievedContact.getName());
        editNumber.setHint(recievedContact.getPhoneNumber());
       
	}
	
	private void setButtonListeners(){
		Button edit = (Button) findViewById(R.id.editButton);
        Button cancel = (Button) findViewById(R.id.cancelButton);
       
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
}
