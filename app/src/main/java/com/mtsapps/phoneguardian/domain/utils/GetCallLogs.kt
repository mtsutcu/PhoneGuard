package com.mtsapps.phoneguardian.domain.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import androidx.core.content.getSystemService
import com.mtsapps.phoneguardian.data.entities.CallContact
import java.util.Locale


class GetCallLogs {
    companion object{
        private  fun getContactInfo(contentResolver: ContentResolver, phoneNumber: String): Pair<String?, String?>{
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

        fun readIncomingCalls(context: Context): List<CallContact> {
            val telephonyManager = context.getSystemService() as TelephonyManager?
            val countryIso = telephonyManager?.networkCountryIso?.uppercase(Locale.getDefault())
            val contentResolver: ContentResolver = context.contentResolver

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
                    val callType = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                    if (callType == CallLog.Calls.INCOMING_TYPE || callType == CallLog.Calls.REJECTED_TYPE) {
                        callList.add(
                            CallContact(
                                name = contactName,
                                number = PhoneNumberUtils.formatNumber(phoneNumber,countryIso),
                                photoUri = contactPhoto, type = "incoming"
                            )
                        )
                    }
                    queryCount++
                }
            }
            return callList.toList()
        }
        fun readMissedCalls(context: Context): List<CallContact> {
            val telephonyManager = context.getSystemService() as TelephonyManager?
            val countryIso = telephonyManager?.networkCountryIso?.uppercase(Locale.getDefault())
            val contentResolver: ContentResolver = context.contentResolver
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
                    val callType = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                    if (callType == CallLog.Calls.MISSED_TYPE) {
                        callList.add(
                            CallContact(
                                name = contactName,
                                number = PhoneNumberUtils.formatNumber(phoneNumber,countryIso),
                                photoUri = contactPhoto,
                                type = "missed"
                            )
                        )
                    }
                    queryCount++
                }
            }
            return callList.toList()
        }
        fun readOutgoingCalls(context: Context): List<CallContact> {
            val telephonyManager = context.getSystemService() as TelephonyManager?
            val countryIso = telephonyManager?.networkCountryIso?.uppercase(Locale.getDefault())
            val contentResolver: ContentResolver = context.contentResolver
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
                    val callType = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE))
                    if (callType == CallLog.Calls.OUTGOING_TYPE) {
                        callList.add(
                            CallContact(
                                name = contactName,
                                number = PhoneNumberUtils.formatNumber(phoneNumber,countryIso),
                                photoUri = contactPhoto,
                                type = "outgoing"
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