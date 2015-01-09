package com.example.hbjia.contentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by hbjia on 2014/12/18.
 */
public class SMSTest extends AndroidTestCase {
    private static final String TAG = "SMSTest";

    private static final String CONVERSATIONS = "content://sms/conversations/";
    private static final String CONTACTS_LOOKUP = "content://com.android.contacts/phone_lookup/";
    private static final String SMS_ALL = "content://sms/";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void testReadConversation() {
        ContentResolver resolver = getContext().getContentResolver();
        Uri uri = Uri.parse(CONVERSATIONS);
        String[] projection = new String[]{
                "groups.group_thread_id AS group_id",
                "groups.msg_count AS msg_count",
                "groups.group_date AS last_date",
                "sms.body AS last_msg",
                "sms.address AS contact"
        };
        Cursor thinc = resolver.query(uri, projection, null, null, "groups.group_date DESC");
        Cursor richc = new CursorWrapper(thinc){
            @Override
            public String getString(int columnIndex) {
                if(super.getColumnIndex("contact") == columnIndex) {
                    String contact = super.getString(columnIndex);
                    Uri uri = Uri.parse(CONTACTS_LOOKUP + contact);
                    Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
                    if(cursor.moveToFirst()) {
                        String contactName = cursor.getString(cursor.getColumnIndex("display_name"));
                        return contactName;
                    }
                    return contact;
                }
                return super.getString(columnIndex);
            }
        };

        while (richc.moveToNext()) {
            String groupId = "groupId: " + richc.getInt(richc.getColumnIndex("group_id"));
            String msgCount = "msgCount: " + richc.getLong(richc.getColumnIndex("msg_count"));
            String lastMsg = "lastMsg: " + richc.getString(richc.getColumnIndex("last_msg"));
            String contact = "contact: " + richc.getString(richc.getColumnIndex("contact"));
            String lastDate = "lastDate: " + dateFormat.format(richc.getLong(richc.getColumnIndex("last_date")));

            printLog(groupId, msgCount, lastMsg, contact, lastDate, "----------END----------");
        }
        richc.close();
    }

    private void printLog(String...strings) {
        for(String s : strings) {
            Log.i(TAG, s == null ? "NULL" : s);
        }
    }
}
