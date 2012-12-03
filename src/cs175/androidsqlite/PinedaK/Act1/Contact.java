package cs175.androidsqlite.PinedaK.Act1;

import java.io.Serializable;

public class Contact implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7965329939862684521L;
	//private variables
	int _id;
	String _Aid;
	String _name;
	String _phone_number;
	String image;
	// Empty constructor
	public Contact(){
		
	}
public Contact(int id){
		this._id=id;
	}
	// constructor
	public Contact(String id, String name, String _phone_number){
		this._Aid = id;
		this._name = name;
		this._phone_number = _phone_number;
	}
	public Contact(int id, String name, String _phone_number){
		this._id = id;
		this._name = name;
		this._phone_number = _phone_number;
	}
	public Contact(int id, String name, String _phone_number,String photo){
		this._id = id;
		this._name = name;
		this._phone_number = _phone_number;
		this.image = photo;
	}
	public Contact(String id, String name, String _phone_number,String photo){
		this._Aid = id;
		this._name = name;
		this._phone_number = _phone_number;
		this.image = photo;
	}
	// constructor
	public Contact(String name, String _phone_number){
		this._name = name;
		this._phone_number = _phone_number;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	// getting Application ID
	public String getAID(){
		return this._Aid;
	}
		
	// setting id
	public void setID(String id){
		this._Aid = id;
	}
		
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting phone number
	public String getPhoneNumber(){
		return this._phone_number;
	}
	
	// setting phone number
	public void setPhoneNumber(String phone_number){
		this._phone_number = phone_number;
	}
}
