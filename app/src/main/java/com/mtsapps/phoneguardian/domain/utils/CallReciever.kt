package com.mtsapps.phoneguardian.domain.utils

import android.content.Context
import android.telecom.Call
import android.telecom.CallScreeningService
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import com.mtsapps.phoneguardian.data.repository.DataStoreRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class CallReciever : CallScreeningService() {

    @Inject
    lateinit var contactRepositoryImpl: ContactRepositoryImpl
    @Inject
    lateinit var dataStoreRepositoryImpl: DataStoreRepositoryImpl
    @Inject
    lateinit var context : Context

    override fun onScreenCall(callDetails: Call.Details) {
        val phoneNumber = callDetails.handle.schemeSpecificPart
        val dateFormatter = DateTimeFormatter.ofPattern("HH:mm")
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepositoryImpl.getBlockStatus(key = booleanPreferencesKey("isBlock")).collect{isBlock->
            contactRepositoryImpl.getActiveCategories().collect{
                val numberList = mutableListOf<String>()
                    val inTimeCategories = it.filter { category ->
                        LocalTime.now().isAfter(LocalTime.parse(category.blockedStartTime,dateFormatter)) && LocalTime.now().isBefore(LocalTime.parse(category.blockedEndTime,dateFormatter)) && category.isAlarm
                    }
                    inTimeCategories.forEach { category ->
                        contactRepositoryImpl.getPhoneNumbersByCategory(category.categoryID).forEach { number->
                            numberList.add(number)
                        }
                    }
                    val notInTimeCategories = it.filter { category ->
                        !category.isAlarm
                    }
                    notInTimeCategories.forEach { category ->
                        contactRepositoryImpl.getPhoneNumbersByCategory(category.categoryID).forEach { number->
                            numberList.add(number)
                        }
                    }

                if (numberList.contains(phoneNumber) && isBlock){

                    val respondWithCallResponse = CallResponse.Builder()
                    respondToCall(callDetails,respondWithCallResponse.setDisallowCall(true).setRejectCall(true).build())
                  /*  val channelId = "my_channel_id"
                    val channelName = "My Channel"
                    val importance = NotificationManager.IMPORTANCE_HIGH
                    val channel = NotificationChannel(channelId, channelName, importance)
                    val notificationManager = getSystemService(NotificationManager::class.java)
                    notificationManager.createNotificationChannel(channel)
                    val notificationId = System.currentTimeMillis().toInt()

                    val builder = NotificationCompat.Builder(context,channelId)
                        .setSmallIcon(R.drawable.person)
                        .setContentTitle("Engellendi")
                        .setContentText(phoneNumber)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)

                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        notificationManager.notify(notificationId, builder.build())
                    }
*/
                }
            }
            }

        }

    }

}