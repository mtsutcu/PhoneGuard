package com.mtsapps.phoneguardian.domain.utils

import android.content.Context
import android.telecom.Call
import android.telecom.CallScreeningService
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import androidx.core.content.getSystemService
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import com.mtsapps.phoneguardian.data.repository.DataStoreRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class CallScreenService : CallScreeningService() {

    @Inject
    lateinit var contactRepositoryImpl: ContactRepositoryImpl

    @Inject
    lateinit var dataStoreRepositoryImpl: DataStoreRepositoryImpl

    @Inject
    lateinit var context: Context

    override fun onScreenCall(callDetails: Call.Details) {
        val telephonyManager = context.getSystemService() as TelephonyManager?
        val countryIso = telephonyManager?.networkCountryIso?.uppercase(Locale.getDefault())
        val phoneNumber =
            PhoneNumberUtils.formatNumber(callDetails.handle.schemeSpecificPart, countryIso)

        val dateFormatter = DateTimeFormatter.ofPattern("HH:mm")
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepositoryImpl.getBooleanValue(key = booleanPreferencesKey("isBlock"))
                .collect { isBlock ->
                    contactRepositoryImpl.getActiveCategories().collect {
                        val numberList = mutableListOf<String>()
                        val inTimeCategories = it.filter { category ->
                            LocalTime.now().isAfter(
                                LocalTime.parse(
                                    category.blockedStartTime,
                                    dateFormatter
                                )
                            ) && LocalTime.now().isBefore(
                                LocalTime.parse(
                                    category.blockedEndTime,
                                    dateFormatter
                                )
                            ) && category.isAlarm
                        }
                        inTimeCategories.forEach { category ->
                            contactRepositoryImpl.getPhoneNumbersByCategory(category.categoryID)
                                .forEach { number ->
                                    numberList.add(number)
                                }
                        }
                        val notInTimeCategories = it.filter { category ->
                            !category.isAlarm
                        }
                        notInTimeCategories.forEach { category ->
                            contactRepositoryImpl.getPhoneNumbersByCategory(category.categoryID)
                                .forEach { number ->
                                    numberList.add(number)
                                }
                        }
                        val filteredNumber =
                            numberList.filter { PhoneNumberUtils.compare(it, phoneNumber) }
                        if (filteredNumber.isNotEmpty() && isBlock) {

                            val respondWithCallResponse = CallResponse.Builder()
                            respondToCall(
                                callDetails,
                                respondWithCallResponse.setDisallowCall(true).setRejectCall(true)
                                    .build()
                            )

                        }
                    }
                }

        }

    }

}