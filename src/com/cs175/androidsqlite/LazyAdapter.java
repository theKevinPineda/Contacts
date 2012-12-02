package com.cs175.androidsqlite;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<Contact> data;
    private static LayoutInflater inflater=null;
    public LazyAdapter(Activity a, ArrayList<Contact> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView name = (TextView)vi.findViewById(R.id.title); // title
        TextView number = (TextView)vi.findViewById(R.id.number); // artist name
        
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);
        
        Contact contact = new Contact();
        contact = data.get(position);
        
        // Setting all values in listview
        name.setText(contact.getName());
        number.setText(contact.getPhoneNumber());
        
        Uri contactURI = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contact.getID()));
        InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(activity.getContentResolver(),contactURI);            
        BufferedInputStream buf =new BufferedInputStream(photo_stream);
        Bitmap my_btmp = BitmapFactory.decodeStream(buf);
        thumb_image.setImageBitmap(my_btmp);
        
        vi.setTag(contact);
        
        return vi;
    }
}