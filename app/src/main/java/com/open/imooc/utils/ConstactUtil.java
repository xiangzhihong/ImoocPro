package com.open.imooc.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Groups;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

import com.open.imooc.R;
import com.open.imooc.bean.ContactModel;

public class ConstactUtil {
    /**获取库Phon表字段**/
    private static  String[] PHONES_INFO = new String[] {
            Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };
    /**联系人显示名称**/
    private static  int PHONES_DISPLAY_NAME_INDEX = 0;
    /**电话号码**/
    private static  int PHONES_NUMBER_INDEX = 1;
    /**头像ID**/
    private static  int PHONES_PHOTO_ID_INDEX = 2;
    /**联系人的ID**/
    private static  int PHONES_CONTACT_ID_INDEX = 3;
    /**联系人信息**/
    private static ArrayList<ContactModel> contactInfo = new ArrayList<ContactModel>();

    public static Map<String, String> getAllCallRecords(Context context) {
        String number = null;
        Map<String, String> temp = new HashMap<String, String>();
        Cursor c = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
                        + " COLLATE LOCALIZED ASC");
        if (c.moveToFirst()) {
            do {
                String contactId = c.getString(c
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String name = c
                        .getString(c
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int phoneCount = c
                        .getInt(c
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    Cursor phones = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    if (phones.moveToFirst()) {
                        number = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                }
                temp.put(name, number);
            } while (c.moveToNext());
        }
        c.close();
        return temp;
    }

    /**
     * 获取手机联系人信息
     * @param mContext
     */
    public static List<ContactModel> getPhoneContacts(Context mContext) {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        //sim卡数据
        Cursor cursor = resolver.query(Phone.CONTENT_URI,PHONES_INFO, null, null, null);
        if (cursor != null) {
            contactInfo.clear();
            while (cursor.moveToNext()) {
                String contactNumber = cursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(contactNumber))
                    continue;
                String contactName = cursor.getString(PHONES_DISPLAY_NAME_INDEX);
                Long contactid = cursor.getLong(PHONES_CONTACT_ID_INDEX);
                Long photoid = cursor.getLong(PHONES_PHOTO_ID_INDEX);
                Bitmap contactPhoto = null;
                if(photoid > 0 ) {
                    Uri uriId = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uriId);
                    contactPhoto = BitmapFactory.decodeStream(input);
                }else {
                    contactPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.personal_default_user_icon);
                }
                ContactModel cm=new ContactModel();
                cm.contactId=contactid;
                cm.contactIcon=contactPhoto;
                cm.contactName=contactName;
                cm.contactNum=contactNumber;
                contactInfo.add(cm);
            }
            cursor.close();
        }
        return contactInfo;
    }
}
