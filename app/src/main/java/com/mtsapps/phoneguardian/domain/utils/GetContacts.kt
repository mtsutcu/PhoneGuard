package com.mtsapps.phoneguardian.domain.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.mtsapps.phoneguardian.domain.models.CallContact

class GetContacts {
    companion object {
        fun readContactsWithPhotos(context: Context): List<CallContact> {
            val contacts = mutableListOf<CallContact>()

            val contentResolver: ContentResolver = context.contentResolver
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            )

            val cursor: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                null
            )

            cursor?.use {
                while (it.moveToNext()) {
                    val name =
                        it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val phoneNumber =
                        it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val photoUriString =
                        it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))

                    val contact = CallContact(name, phoneNumber.filterNot { it == '(' || it == ')' || it == '-' || it == '+' || it == ' '}.trim(), photoUriString)
                    contacts.add(contact)
                }
            }

            cursor?.close()
            return contacts
        }
    }
}