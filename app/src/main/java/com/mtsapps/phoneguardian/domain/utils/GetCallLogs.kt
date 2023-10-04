package com.mtsapps.phoneguardian.domain.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import com.mtsapps.phoneguardian.domain.models.CallContact

class GetCallLogs {
    companion object{
        fun getContactInfo(contentResolver: ContentResolver, phoneNumber: String): Pair<String?, String?> {
            val projection =
                arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.PHOTO_URI)
            val uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber)
            )
            val cursor = contentResolver.query(uri, projection, null, null, null)
            var contactName: String? = null
            var contactPhoto: String? = null

            cursor?.use {
                if (it.moveToFirst()) {
                    contactName =
                        it.getString(it.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME))
                    contactPhoto =
                        it.getString(it.getColumnIndexOrThrow(ContactsContract.PhoneLookup.PHOTO_URI))
                }
            }


            cursor?.close()

            return Pair(contactName, contactPhoto)
        }

        fun readIncomingCalls(contentResolver: ContentResolver): List<CallContact> {
            val callList = mutableSetOf<CallContact>()
            val projection = arrayOf(
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE,
            )


            val sortOrder = "${CallLog.Calls.DATE} DESC"
            val cursor: Cursor? = contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            cursor?.use {
                var queryCount =0
                while (it.moveToNext() && it.position < 50) {
                    val phoneNumber = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))

                    val contactInfo = getContactInfo(contentResolver, phoneNumber)
                    val contactName = contactInfo.first
                    val contactPhoto = contactInfo.second
                    val callId = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls._ID))
                    val callDate = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))
                    val callDuration = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                    val callType = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                    if (callType == CallLog.Calls.INCOMING_TYPE || callType == CallLog.Calls.REJECTED_TYPE) {
                        callList.add(
                            CallContact(
                                name = contactName,
                                number = phoneNumber.toString(),
                                photoUri = contactPhoto
                            )
                        )
                    }
                    queryCount++
                }
            }
            return callList.toList()
        }
        fun readMissedCalls(contentResolver: ContentResolver): List<CallContact> {
            val callList = mutableSetOf<CallContact>()
            val projection = arrayOf(
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE,
            )


            val sortOrder = "${CallLog.Calls.DATE} DESC"
            val cursor: Cursor? = contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            cursor?.use {
                var queryCount =0
                while (it.moveToNext() && it.position < 50) {
                    val phoneNumber = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))

                    val contactInfo = getContactInfo(contentResolver, phoneNumber)
                    val contactName = contactInfo.first
                    val contactPhoto = contactInfo.second
                    val callId = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls._ID))
                    val callDate = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))
                    val callDuration = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                    val callType = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                    if (callType == CallLog.Calls.MISSED_TYPE) {
                        callList.add(
                            CallContact(
                                name = contactName,
                                number = phoneNumber.toString(),
                                photoUri = contactPhoto
                            )
                        )
                    }
                    queryCount++
                }
            }
            return callList.toList()
        }
        fun readOutgoingCalls(contentResolver: ContentResolver): List<CallContact> {
            val callList = mutableSetOf<CallContact>()
            val projection = arrayOf(
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE,
            )


            val sortOrder = "${CallLog.Calls.DATE} DESC"
            val cursor: Cursor? = contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            cursor?.use {
                var queryCount =0
                while (it.moveToNext() && it.position < 50) {
                    val phoneNumber = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))

                    val contactInfo = getContactInfo(contentResolver, phoneNumber)
                    val contactName = contactInfo.first
                    val contactPhoto = contactInfo.second
                    val callId = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls._ID))
                    val callDate = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))
                    val callDuration = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                    val callType = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                    if (callType == CallLog.Calls.OUTGOING_TYPE) {
                        callList.add(
                            CallContact(
                                name = contactName,
                                number = phoneNumber.toString(),
                                photoUri = contactPhoto
                            )
                        )
                    }
                    queryCount++
                }
            }
            return callList.toList()
        }
    }
}