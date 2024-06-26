package Draz.afinal;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContactUtils { // Method to fetch all contacts and their phone numbers
    public static List<String> getContactNumbers(Context context) {
        List<String> numbers = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = null;

        try {
            // Query to get contacts
            cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
//                    // Get contact name
//                    String contactName = cursor.getString(cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    // Get contact phone number
//                    String phoneNumber = cursor.getString(cursor.getColumnIndex(
//                            ContactsContract.CommonDataKinds.Phone.NUMBER));

                    // Add to list
//                    numbers.add(contactName + ": " + phoneNumber);
                } while (cursor.moveToNext());
            }
        } catch (SecurityException e) {
            Log.e("ContactsPermission", "Permission denied to read contacts");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return numbers;
    }

}
