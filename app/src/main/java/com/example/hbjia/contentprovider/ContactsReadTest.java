package com.example.hbjia.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hbjia on 2014/12/18.
 */
public class ContactsReadTest extends AndroidTestCase {

    private static final String TAG = "ContactsReadTest";

    //[content://com.android.contacts/contacts]
    private static final Uri CONTACTS_URI = ContactsContract.Contacts.CONTENT_URI;
    //[content://com.android.contacts/data/phones]
    private static final Uri PHONES_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static final Uri EMAIL_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

    private static final String _ID = ContactsContract.Contacts._ID;
    private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private static final String CONTACT_ID = ContactsContract.Data.CONTACT_ID;

    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
    private static final String EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA;
    private static final String EMAIL_TYPE = ContactsContract.CommonDataKinds.Email.TYPE;

    public void testReadContacts() {
        ContentResolver resolver = this.getContext().getContentResolver();
        Cursor c = resolver.query(CONTACTS_URI, null, null, null, null);
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex(_ID));
            String displayName = c.getString(c.getColumnIndex(DISPLAY_NAME));
            Log.i(TAG, displayName);

            ArrayList<String> phones = new ArrayList<String>();
            ArrayList<String> emails = new ArrayList<String>();

            String selection = CONTACT_ID + "=" + _id;
            int hasPhoneNumber = c.getInt(c.getColumnIndex(HAS_PHONE_NUMBER));
            if(hasPhoneNumber > 0) {
                Cursor phc = resolver.query(PHONES_URI, null, selection, null, null);
                while (phc.moveToNext()) {
                    String phoneNumber = phc.getString(phc.getColumnIndex(PHONE_NUMBER));
                    int phoneType = phc.getInt(phc.getColumnIndex(PHONE_TYPE));
                    phones.add(getPhoneTypeNameById(phoneType) + " : " + phoneNumber);
                }
                phc.close();
            }
            Log.i(TAG, "phones: " + phones);

            Cursor emc = resolver.query(EMAIL_URI, null, selection, null, null);
            while (emc.moveToNext()) {
                String email = emc.getString(emc.getColumnIndex(EMAIL_DATA));
                int emailType = emc.getInt(emc.getColumnIndex(EMAIL_TYPE));
                emails.add(getEmailTypeById(emailType) + " : " + email);
            }
            Log.i(TAG, "emails: " + emails);
        }
    }

    private String getEmailTypeById(int emailType) {
        switch (emailType) {
            case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                return "home";
            case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                return "work";
            case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                return "other";
            default:
                return "none";
        }
    }

    private String getPhoneTypeNameById(int phoneType) {
        switch (phoneType) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                return "home";
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                return "work";
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                return "mobile";
            default:
                return "none";
        }
    }
    
}
