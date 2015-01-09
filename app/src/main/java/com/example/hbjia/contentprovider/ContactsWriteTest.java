package com.example.hbjia.contentprovider;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hbjia on 2014/12/18.
 */
public class ContactsWriteTest extends AndroidTestCase {
    private static final String TAG = "ContactsWriteTest";

    private static final Uri RAW_CONTACTS_URI = ContactsContract.RawContacts.CONTENT_URI;
    private static final Uri DATA_URI = ContactsContract.Data.CONTENT_URI;

    private static final String ACCOUNT_TYPE = ContactsContract.RawContacts.ACCOUNT_TYPE;
    private static final String ACCOUNT_NAME = ContactsContract.RawContacts.ACCOUNT_NAME;

    private static final String RAW_CONTACT_ID = ContactsContract.Data.RAW_CONTACT_ID;
    private static final String MIMETYPE = ContactsContract.Data.MIMETYPE;

    private static final String NAME_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE;
    private static final String DISPLAY_NAME = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME;

    private static final String PHONE_ITEM_TYPE = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
    private static final int PHONE_TYPE_HOME = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
    private static final int PHONE_TYPE_MOBILE = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

    private static final String EMAIL_ITEM_TYPE = ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE;
    private static final String EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA;
    private static final String EMAIL_TYPE = ContactsContract.CommonDataKinds.Email.TYPE;
    private static final int EMAIL_TYPE_HOME = ContactsContract.CommonDataKinds.Email.TYPE_HOME;
    private static final int EMAIL_TYPE_WORK = ContactsContract.CommonDataKinds.Email.TYPE_WORK;

    private static final String AUTHORITY = ContactsContract.AUTHORITY;

    public void testWriteContacts() throws RemoteException, OperationApplicationException {
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

        ContentProviderOperation operation = ContentProviderOperation.newInsert(RAW_CONTACTS_URI)
                .withValue(ACCOUNT_TYPE, null)
                .withValue(ACCOUNT_NAME, null)
                .build();
        operations.add(operation);

        operation = ContentProviderOperation.newInsert(DATA_URI)
                .withValueBackReference(RAW_CONTACT_ID, 0)
                .withValue(MIMETYPE, NAME_ITEM_TYPE)
                .withValue(DISPLAY_NAME, "TeleNav")
                .build();
        operations.add(operation);

        operation = ContentProviderOperation.newInsert(DATA_URI)
                .withValueBackReference(RAW_CONTACT_ID, 0)
                .withValue(MIMETYPE, PHONE_ITEM_TYPE)
                .withValue(PHONE_TYPE, PHONE_TYPE_HOME)
                .withValue(PHONE_NUMBER, "0123456789")
                .build();
        operations.add(operation);

        operation = ContentProviderOperation.newInsert(DATA_URI)
                .withValueBackReference(RAW_CONTACT_ID, 0)
                .withValue(MIMETYPE, PHONE_ITEM_TYPE)
                .withValue(PHONE_TYPE, PHONE_TYPE_MOBILE)
                .withValue(PHONE_NUMBER, "1324568740")
                .build();
        operations.add(operation);

        operation = ContentProviderOperation.newInsert(DATA_URI)
                .withValueBackReference(RAW_CONTACT_ID, 0)
                .withValue(MIMETYPE, EMAIL_ITEM_TYPE)
                .withValue(EMAIL_TYPE, EMAIL_TYPE_HOME)
                .withValue(EMAIL_DATA, "hbjia@telenavsoftware.com")
                .build();
        operations.add(operation);

        ContentResolver resolver = getContext().getContentResolver();
        ContentProviderResult [] results = resolver.applyBatch(AUTHORITY, operations);
        for(ContentProviderResult result : results) {
            Log.i(TAG, result.uri.toString());
        }
    }

}
