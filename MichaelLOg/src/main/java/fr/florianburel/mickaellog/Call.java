package fr.florianburel.mickaellog;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.Date;

/**
 * Created by fl0 on 22/11/2013.
 */
public class Call
{
    private String phoneNumber;
    private String contactName;
    private Date callDate;

    public Call(String name, String number, Date date) {
        this.phoneNumber = number;
        this.callDate = date;
        this.contactName = name;


    }


    @Override
    public String toString() {
        return contactName;
    }

    /* CONSTRUCTOR */
    public Call(String phoneNumber, Context context)
    {
        this.phoneNumber = phoneNumber;
        this.callDate = new Date();

        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor c = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if(c != null)
        {
            c.moveToFirst();
            if(!c.isAfterLast())
                this.contactName = c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            c.close();
        }

    }

    /* ACCESSORS */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }
}
